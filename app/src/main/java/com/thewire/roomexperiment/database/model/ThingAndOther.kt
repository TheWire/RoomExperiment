package com.thewire.roomexperiment.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.thewire.roomexperiment.domain.model.OtherThing

data class ThingAndOther(
    @Embedded
    val thing: ThingEntity,

    @Relation(
        entity = OtherThingEntity::class,
        parentColumn = "otherThingId",
        entityColumn = "id"
    )
    val otherThingEntity: OtherAndAnother
)
