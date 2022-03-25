package com.thewire.roomexperiment.interactors

import com.thewire.roomexperiment.ThingDao
import com.thewire.roomexperiment.ThingEntityMapper
import com.thewire.roomexperiment.domain.DataState
import com.thewire.roomexperiment.domain.model.Thing
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GetThing(
    private val thingDao: ThingDao,
    private val thingMapper: ThingEntityMapper,
) {
    fun execute(
        thingId: Int,
    ) : Flow<DataState<Thing>> = flow {
        try {
            emit(DataState.loading<Thing>())
            delay(1000)
            var thing = getThing(thingId)
            if(thing != null) {
                emit(DataState.success(thing))
            } else {
                emit(DataState.error<Thing>("cannot find thing"))
            }
        } catch(e: Exception) {
            emit(DataState.error<Thing>(e.message?: "Unknown error"))
        }
    }

    private suspend fun getThing(thingId: Int): Thing? {
        return thingDao.getThingById(thingId)?.let { thingEntity ->
            thingMapper.mapEntityToDomain(thingEntity)
        }
    }
}