package com.thewire.roomexperiment

import android.net.Uri
import com.thewire.roomexperiment.database.model.AnotherThingEntity
import com.thewire.roomexperiment.database.model.OtherThingEntity
import com.thewire.roomexperiment.domain.model.AnotherThing
import com.thewire.roomexperiment.domain.model.OtherThing

class AnotherThingEntityMapper {
    fun mapEntityToDomain(anotherThingEntity: AnotherThingEntity): AnotherThing {
        return AnotherThing(
           text = anotherThingEntity.text
        )
    }

    fun mapDomainToEntity(anotherThing: AnotherThing): AnotherThingEntity {
        return AnotherThingEntity(
          text = anotherThing.text
        )
    }
}