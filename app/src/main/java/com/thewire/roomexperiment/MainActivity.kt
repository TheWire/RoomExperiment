package com.thewire.roomexperiment

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.room.Room
import com.thewire.roomexperiment.database.AppDatabase
import com.thewire.roomexperiment.domain.model.Thing
import com.thewire.roomexperiment.interactors.GetThing
import com.thewire.roomexperiment.interactors.InsertThing
import com.thewire.roomexperiment.ui.theme.RoomExperimentTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

const val TAG = "room_debug"

class MainActivity : ComponentActivity() {
    lateinit var getThing: GetThing
    lateinit var insertThing: InsertThing

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
        getThing = GetThing(dao, mapper)
        insertThing = InsertThing(dao, mapper)

        setContent {
            RoomExperimentTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(getThing = getThing, insertThing = insertThing)
                }
            }
        }
    }
}

@Composable
fun Greeting(getThing: GetThing, insertThing:InsertThing) {
    Column() {
        val description = remember{ mutableStateOf("insert description")}
        val tf = remember{ mutableStateOf(false)}
        TextField(
            value = description.value,
            onValueChange = { description.value = it }
        )
        Checkbox(
            checked = tf.value,
            onCheckedChange = { tf.value = it }
        )
        Button(
            onClick = {
                if(description.value != "") {
                    goInsertThing(Thing(0, description.value, tf.value), insertThing, MainScope())
                    description.value = ""
                }
            }
        ) {
            Text("Insert")
        }
        ThingGetter(
            getThing = getThing
        )
    }

}

@Composable
fun ThingGetter(getThing: GetThing) {

    Column() {
        val list: MutableList<Thing> = remember {
            mutableStateListOf()
        }
        val idString = remember { mutableStateOf("id of thing to get goes here")}
        TextField(value = idString.value, onValueChange = { idString.value = it })
        Button(
            onClick = {
                    goGetThing(idString.value.toInt(), getThing, list, MainScope())
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

fun goInsertThing(thing: Thing, insertThing: InsertThing, scope: CoroutineScope) {
    scope.launch {
        insertThing.execute(thing).collect {
            it.error?.let { error ->
              Log.e(TAG, error)
            }
        }
    }
}


