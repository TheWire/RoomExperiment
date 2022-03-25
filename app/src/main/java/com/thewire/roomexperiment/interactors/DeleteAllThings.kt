package com.thewire.roomexperiment.interactors

import com.thewire.roomexperiment.ThingDao
import com.thewire.roomexperiment.domain.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class DeleteAllThings(
    private val thingDao: ThingDao
) {
    fun execute(
    ) : Flow<DataState<Int>> = flow {
        try {
            emit(DataState.loading())
            delay(1000)
            val ret = deleteAllThings()
            emit(DataState.success(ret))
        } catch(e: Exception) {
            emit(DataState.error(e.message?: "Unknown error"))
        }
    }

    private suspend fun deleteAllThings(): Int {
        return thingDao.deleteAllThings()
    }
}