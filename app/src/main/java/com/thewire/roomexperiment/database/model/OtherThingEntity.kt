package com.thewire.roomexperiment.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "otherThings")
data class OtherThingEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "b")
    var b: Boolean,
    @ColumnInfo(name = "uri")
    var uri: String,
    @ColumnInfo(name = "i")
    var i: Int,
    @ColumnInfo(name = "another_thing_id")
    var anotherThing: Int = 0
)
