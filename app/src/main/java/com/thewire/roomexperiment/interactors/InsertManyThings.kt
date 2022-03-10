package com.thewire.roomexperiment.interactors

import com.thewire.roomexperiment.ThingDao
import com.thewire.roomexperiment.ThingOtherEntityMapper
import com.thewire.roomexperiment.domain.DataState
import com.thewire.roomexperiment.domain.model.ThingAndOtherModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InsertManyThings(
    private val thingDao: ThingDao,
    private val thingOtherMapper: ThingOtherEntityMapper,
) {
    fun execute(thingsAndOthers: List<ThingAndOtherModel>): Flow<DataState<LongArray>> = flow {
        try {
            emit(DataState.loading())
            delay(1000)
            val ret = insertThings(thingsAndOthers)
            emit(DataState.success(ret))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown error"))
        }

    }

    private suspend fun insertThings(thingsAndOthers: List<ThingAndOtherModel>): LongArray {
        return thingDao.insertThingsAndOthers(thingsAndOthers.map{thingOtherMapper.mapDomainToEntity(it)} )
    }
}