package com.thewire.roomexperiment.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
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

    @Embedded
    var otherThing: OtherThing
)
