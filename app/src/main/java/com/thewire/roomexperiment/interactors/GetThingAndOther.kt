package com.thewire.roomexperiment.interactors

import com.thewire.roomexperiment.ThingDao
import com.thewire.roomexperiment.ThingEntityMapper
import com.thewire.roomexperiment.ThingOtherEntityMapper
import com.thewire.roomexperiment.domain.DataState
import com.thewire.roomexperiment.domain.model.Thing
import com.thewire.roomexperiment.domain.model.ThingAndOtherModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GetThingAndOther(
    private val thingDao: ThingDao,
    private val thingMapper: ThingOtherEntityMapper,
) {
    fun execute(
        thingId: Int,
    ) : Flow<DataState<ThingAndOtherModel>> = flow {
        try {
            emit(DataState.loading())
            delay(1000)
            var thing = getThing(thingId)
            if(thing != null) {
                emit(DataState.success(thing))
            } else {
                emit(DataState.error("cannot not find thing"))
            }
        } catch(e: Exception) {
            emit(DataState.error(e.message?: "Unknown error"))
        }
    }

    private suspend fun getThing(thingId: Int): ThingAndOtherModel? {
        return thingDao.getThingWithOther(thingId)?.let { thingEntity ->
            thingMapper.mapEntityToDomain(thingEntity)
        }
    }
}