package com.thewire.roomexperiment.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class ThingAndOther(
    @Embedded
    val thing: ThingEntity,

    @Relation(
        parentColumn = "otherThingId",
        entityColumn = "id"
    )
    val otherThingEntity: OtherThingEntity
)
