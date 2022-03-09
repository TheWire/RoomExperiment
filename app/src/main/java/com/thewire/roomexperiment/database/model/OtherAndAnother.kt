package com.thewire.roomexperiment.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class OtherAndAnother (
    @Embedded
    val otherThingEntity: OtherThingEntity,
    @Relation(
        parentColumn = "another_thing_id",
        entityColumn = "id"
    )
    val anotherThing: AnotherThingEntity
)