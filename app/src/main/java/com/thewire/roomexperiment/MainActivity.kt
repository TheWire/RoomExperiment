package com.thewire.roomexperiment

import android.app.Application
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.thewire.roomexperiment.database.AppDatabase
import com.thewire.roomexperiment.domain.model.Embed
import com.thewire.roomexperiment.domain.model.OtherThing
import com.thewire.roomexperiment.domain.model.Thing
import com.thewire.roomexperiment.domain.model.ThingAndOtherModel
import com.thewire.roomexperiment.interactors.GetByEmbed
import com.thewire.roomexperiment.interactors.GetThing
import com.thewire.roomexperiment.interactors.GetThingAndOther
import com.thewire.roomexperiment.interactors.InsertThing
import com.thewire.roomexperiment.ui.theme.RoomExperimentTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

const val TAG = "room_debug"

class MainActivity : ComponentActivity() {
    lateinit var getThing: GetThing
    lateinit var getByEmbed: GetByEmbed
    lateinit var insertThing: InsertThing
    lateinit var getThingAndOther: GetThingAndOther

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()

        val dao = db.thingDao()
        val mapper = ThingEntityMapper()
        val otherMapper = ThingOtherEntityMapper()
        getThing = GetThing(dao, mapper)
        getByEmbed = GetByEmbed(dao, mapper)
        insertThing = InsertThing(dao, otherMapper)
        getThingAndOther = GetThingAndOther(dao, otherMapper)

        setContent {
            RoomExperimentTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    DatabaseScreen(getThingAndOther = getThingAndOther, getByEmbed = getByEmbed, insertThing = insertThing)
                }
            }
        }
    }
}

@Composable
fun DatabaseScreen(getThingAndOther: GetThingAndOther, getByEmbed: GetByEmbed, insertThing:InsertThing) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        val description = remember{ mutableStateOf("")}
        val tf = remember{ mutableStateOf(false)}
        TextField(
            value = description.value,
            onValueChange = { description.value = it },
            label = { Text("insert description") }
        )
        Checkbox(
            checked = tf.value,
            onCheckedChange = { tf.value = it }
        )
        val embedString = remember{ mutableStateOf("")}
        TextField(
            value = embedString.value,
            onValueChange = { embedString.value = it },
            label = { Text("insert embed text") }
        )
        val embedInt = remember{ mutableStateOf("")}
        TextField(
            value = embedInt.value,
            onValueChange = { embedInt.value = it },
            label = { Text("insert embed int") }
        )
        val othertf = remember{ mutableStateOf(false)}
        Checkbox(
            checked = othertf.value,
            onCheckedChange = { othertf.value = it }
        )
        val otherUri = remember{ mutableStateOf("")}
        TextField(
            value = otherUri.value,
            onValueChange = { otherUri.value = it },
            label = { Text("other thing uri") }
        )
        val otherInt = remember{ mutableStateOf("")}
        TextField(
            value = otherInt.value,
            onValueChange = { otherInt.value = it },
            label = { Text("other thing int") }
        )
        Button(
            onClick = {
                if(description.value != "") {
                    goInsertThing(
                        ThingAndOtherModel(
                            0,
                            description.value,
                            tf.value,
                            Embed(
                                embedString.value,
                                Integer.parseInt(embedInt.value)
                            ),
                            OtherThing(
                                othertf.value,
                                Integer.parseInt(otherInt.value),
                                uri = Uri.parse(otherUri.value)
                            )
                        ), insertThing, MainScope())
                    description.value = ""
                }
            }
        ) {
            Text("Insert")
        }
        ThingGetter(
            getThingAndOther = getThingAndOther
        )
        EmbedGetter(
           getByEmbed = getByEmbed
        )
    }

}

@Composable
fun ThingGetter(getThingAndOther: GetThingAndOther) {

    Column() {
        val list: MutableList<ThingAndOtherModel> = remember {
            mutableStateListOf()
        }
        val idString = remember { mutableStateOf("")}
        TextField(
            value = idString.value,
            onValueChange = { idString.value = it },
            label = { Text("id of thing to get goes here") }
        )
        Button(
            onClick = {
                    goGetThingAndOther(idString.value.toInt(), getThingAndOther, list, MainScope())
                }
        ) {
            Text("Get")
        }
        list.forEach {
            Thing(
                Thing(
                    id = it.id,
                    description = it.description,
                    tf = it.tf,
                    embed = it.embed
                )
            )
            OtherThing(it.other)
        }
    }
}

@Composable
fun EmbedGetter(getByEmbed: GetByEmbed) {

    Column() {
        val list: MutableList<Thing> = remember {
            mutableStateListOf()
        }
        val idString = remember { mutableStateOf("")}
        TextField(
            value = idString.value,
            onValueChange = { idString.value = it },
            label = { Text("embed int") }
        )
        Button(
            onClick = {
                goGetEmbed(idString.value.toInt(), getByEmbed, list, MainScope())
            }
        ) {
            Text("Get")
        }
        list.forEach {
            Thing(it)
        }
    }
}

@Composable
fun Thing(thing: Thing) {
    Text(thing.id.toString())
    Text(thing.description)
    Text(thing.tf.toString())
    Text(thing.embed.exampleString)
    Text(thing.embed.exampleInt.toString())

}

@Composable
fun OtherThing(other: OtherThing) {
    Text(other.b.toString())
    Text(other.uri.toString())
    Text(other.i.toString())
}

fun goGetThing(
    thingId: Int,
    getThing: GetThing,
    list: MutableList<Thing>,
    scope: CoroutineScope
) {
        scope.launch {
            getThing.execute(thingId).collect {
                it.data?.let { thing ->
                    list.add(thing)
                }

                it.error?.let { error ->
                    Log.e(TAG, error)
                }
            }
        }
}

fun goGetThingAndOther(
    thingId: Int,
    getThingAndOther: GetThingAndOther,
    list: MutableList<ThingAndOtherModel>,
    scope: CoroutineScope
) {
    scope.launch {
        getThingAndOther.execute(thingId).collect {
            it.data?.let { thing ->
                list.add(thing)
            }

            it.error?.let { error ->
                Log.e(TAG, error)
            }
        }
    }
}

fun goGetEmbed(
    thingId: Int,
    getByEmbed: GetByEmbed,
    list: MutableList<Thing>,
    scope: CoroutineScope
) {
    scope.launch {
        getByEmbed.execute(thingId).collect {
            it.data?.let { things ->
                things.forEach { thing ->
                    list.add(thing)
                }
            }

            it.error?.let { error ->
                Log.e(TAG, error)
            }
        }
    }
}

fun goInsertThing(thing: ThingAndOtherModel, insertThing: InsertThing, scope: CoroutineScope) {
    scope.launch {
        insertThing.execute(thing).collect {
            it.error?.let { error ->
              Log.e(TAG, error)
            }
        }
    }
}


