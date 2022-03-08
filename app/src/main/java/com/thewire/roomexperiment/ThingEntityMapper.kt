package com.thewire.roomexperiment

import com.thewire.roomexperiment.database.model.ThingAndOther
import com.thewire.roomexperiment.database.model.ThingEntity
import com.thewire.roomexperiment.domain.model.Thing
import com.thewire.roomexperiment.domain.model.ThingAndOtherModel

class ThingEntityMapper() {

    fun mapEntityToDomain(thingEntity: ThingEntity): Thing {
        return Thing(
            id = thingEntity.id!!,
            description = thingEntity.description,
            tf = thingEntity.tf,
            embed = thingEntity.embedTest,
        )
    }

    fun mapDomainToEntity(thing: Thing): ThingEntity {
        return ThingEntity(
            description = thing.description,
            tf = thing.tf,
            embedTest = thing.embed,
        )
    }
}

class ThingOtherEntityMapper() {

    fun mapEntityToDomain(thingAndOther: ThingAndOther): ThingAndOtherModel {
        return ThingAndOtherModel(
            id = thingAndOther.thing.id!!,
            description = thingAndOther.thing.description,
            tf = thingAndOther.thing.tf,
            embed = thingAndOther.thing.embedTest,
            other = OtherThingEntityMapper().mapEntityToDomain(thingAndOther.otherThingEntity),
        )
    }

    fun mapDomainToEntity(thingAndOtherModel: ThingAndOtherModel): ThingAndOther {
        return ThingAndOther(
            thing = ThingEntityMapper().mapDomainToEntity(
                Thing(
                    id = thingAndOtherModel.id,
                    description = thingAndOtherModel.description,
                    tf = thingAndOtherModel.tf,
                    embed = thingAndOtherModel.embed,
                )
            ),
            otherThingEntity = OtherThingEntityMapper().mapDomainToEntity(thingAndOtherModel.other),
        )
    }
}