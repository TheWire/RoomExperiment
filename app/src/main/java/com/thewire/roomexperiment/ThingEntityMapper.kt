package com.thewire.roomexperiment

import android.net.Uri
import com.thewire.roomexperiment.database.model.OtherAndAnother
import com.thewire.roomexperiment.database.model.OtherThingEntity
import com.thewire.roomexperiment.database.model.ThingAndOther
import com.thewire.roomexperiment.database.model.ThingEntity
import com.thewire.roomexperiment.domain.model.OtherThing
import com.thewire.roomexperiment.domain.model.Thing
import com.thewire.roomexperiment.domain.model.ThingAndOtherModel

class ThingEntityMapper() {

    fun mapEntityToDomain(thingEntity: ThingEntity): Thing {
        return Thing(
            id = thingEntity.id!!,
            description = thingEntity.description,
            tf = thingEntity.tf,
            embed = thingEntity.embedTest,
            n = thingEntity.n
        )
    }

    fun mapDomainToEntity(thing: Thing): ThingEntity {
        return ThingEntity(
            description = thing.description,
            tf = thing.tf,
            embedTest = thing.embed,
            n = thing.n,
        )
    }
}

class OtherAndAnotherThingEntityMapper() {
    fun mapEntityToDomain(otherAndAnother: OtherAndAnother) : OtherThing {
        return OtherThing(
            b = otherAndAnother.otherThingEntity.b,
            i = otherAndAnother.otherThingEntity.i,
            uri = Uri.parse(otherAndAnother.otherThingEntity.uri),
            anotherThing = otherAndAnother.anotherThing?.let{ AnotherThingEntityMapper().mapEntityToDomain(it) }
        )
    }

    fun mapDomainToEntity(otherThing: OtherThing): OtherAndAnother {
        return OtherAndAnother(
            otherThingEntity = OtherThingEntity(
                b = otherThing.b,
                uri = otherThing.uri.toString(),
                i = otherThing.i,
            ),
            anotherThing = AnotherThingEntityMapper().mapDomainToEntity(otherThing.anotherThing)
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
            n = thingAndOther.thing.n,
            other = thingAndOther.otherThingEntity?.let { OtherAndAnotherThingEntityMapper().mapEntityToDomain(it) },
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
                    n = thingAndOtherModel.n
                )
            ),
            otherThingEntity = thingAndOtherModel.other?.let { OtherAndAnotherThingEntityMapper().mapDomainToEntity(it) },
        )
    }
}