package com.thewire.roomexperiment.interactors

import com.thewire.roomexperiment.ThingDao
import com.thewire.roomexperiment.ThingEntityMapper
import com.thewire.roomexperiment.domain.DataState
import com.thewire.roomexperiment.domain.model.Thing
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class InsertThing(
    private val thingDao: ThingDao,
    private val thingMapper: ThingEntityMapper,
) {
    fun execute(thing: Thing): Flow<DataState<Long>> = flow {
        try {
            emit(DataState.loading<Long>())
            delay(1000)
            val ret = insertThing(thing)
            emit(DataState.success(ret))
        } catch(e: Exception) {
            emit(DataState.error<Long>(e.message?: "Unknown error"))
        }

    }

    private suspend fun insertThing(thing: Thing): Long {
        return thingDao.insertThing(thingMapper.mapDomainToEntity(thing))
    }
}