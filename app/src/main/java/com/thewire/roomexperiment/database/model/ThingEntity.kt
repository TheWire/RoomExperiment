package com.thewire.roomexperiment.database.model

import androidx.room.*
import com.thewire.roomexperiment.domain.model.Embed
import com.thewire.roomexperiment.domain.model.OtherThing

@Entity(tableName = "things")
data class ThingEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "tf")
    var tf: Boolean,

    @Embedded(prefix = "embedTest_")
    var embedTest: Embed,

    @ColumnInfo(name = "otherThingId")
    var otherThingId: Int = 0,

    @ColumnInfo(name = "nh")
    var n: String?,
)