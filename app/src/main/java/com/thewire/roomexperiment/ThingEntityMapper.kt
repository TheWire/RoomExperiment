package com.thewire.roomexperiment

import com.thewire.roomexperiment.database.model.ThingEntity
import com.thewire.roomexperiment.domain.model.Thing

class ThingEntityMapper() {

    fun mapEntityToDomain(thingEntity: ThingEntity): Thing {
        return Thing(
            id = thingEntity.id!!,
            description = thingEntity.description,
            tf = thingEntity.tf,
        )
    }

    fun mapDomainToEntity(thing: Thing): ThingEntity {
        return ThingEntity(
            description = thing.description,
            tf = thing.tf,
        )
    }
}