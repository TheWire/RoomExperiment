package com.thewire.roomexperiment.interactors

import com.thewire.roomexperiment.ThingDao
import com.thewire.roomexperiment.domain.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class DeleteThing(
    private val thingDao: ThingDao
) {
    fun execute(
        thingId: Int
    ) : Flow<DataState<Int>> = flow {
        try {
            emit(DataState.loading())
            delay(1000)
            val ret = deleteById(thingId)
            emit(DataState.success(ret))
        } catch(e: Exception) {
            emit(DataState.error(e.message?: "Unknown error"))
        }
    }

    private suspend fun deleteById(thingId: Int): Int {
        return thingDao.deleteThing(thingId)
    }
}