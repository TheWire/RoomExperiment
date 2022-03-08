package com.thewire.roomexperiment

import android.net.Uri
import com.thewire.roomexperiment.database.model.OtherThingEntity
import com.thewire.roomexperiment.domain.model.OtherThing

class OtherThingEntityMapper {
    fun mapEntityToDomain(otherThingEntity: OtherThingEntity): OtherThing {
        return OtherThing(
            b = otherThingEntity.b,
            i = otherThingEntity.i,
            uri = Uri.parse(otherThingEntity.uri),
        )
    }

    fun mapDomainToEntity(otherThing: OtherThing): OtherThingEntity {
        return OtherThingEntity(
            b = otherThing.b,
            i = otherThing.i,
            uri = otherThing.uri.toString(),
        )
    }
}