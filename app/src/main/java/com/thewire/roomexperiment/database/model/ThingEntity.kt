package com.thewire.roomexperiment.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "things")
data class ThingEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "tf")
    var tf: Boolean
    )
