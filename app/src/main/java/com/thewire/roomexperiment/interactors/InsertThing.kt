package com.thewire.roomexperiment.interactors

import com.thewire.roomexperiment.ThingDao
import com.thewire.roomexperiment.ThingOtherEntityMapper
import com.thewire.roomexperiment.domain.DataState
import com.thewire.roomexperiment.domain.model.ThingAndOtherModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InsertThing(
    private val thingDao: ThingDao,
    private val thingOtherMapper: ThingOtherEntityMapper,
) {
    fun execute(thingAndOther: ThingAndOtherModel): Flow<DataState<Long>> = flow {
        try {
            emit(DataState.loading())
            delay(1000)
            val ret = insertThing((thingAndOther))
            emit(DataState.success(ret))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown error"))
        }

    }

    private suspend fun insertThing(thingAndOther: ThingAndOtherModel): Long {
        return thingDao.insertThingAndOther(thingOtherMapper.mapDomainToEntity(thingAndOther))
    }
}