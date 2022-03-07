package com.thewire.roomexperiment.interactors

import com.thewire.roomexperiment.ThingDao
import com.thewire.roomexperiment.ThingEntityMapper
import com.thewire.roomexperiment.domain.DataState
import com.thewire.roomexperiment.domain.model.Thing
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GetByEmbed(
    private val thingDao: ThingDao,
    private val thingMapper: ThingEntityMapper,
) {
    fun execute(
        embedInt: Int,
    ) : Flow<DataState<List<Thing>>> = flow {
        try {
            emit(DataState.loading())
            delay(1000)
            var things: List<Thing> = getByEmbed(embedInt)
            if(things.isNotEmpty()) {
                emit(DataState.success(things))
            } else {
                emit(DataState.error("cannot not find thing"))
            }
        } catch(e: Exception) {
            emit(DataState.error(e.message?: "Unknown error"))
        }
    }

    private suspend fun getByEmbed(embedInt: Int): List<Thing> {
        return thingDao.getEmbedByInt(embedInt).map{
            ThingEntityMapper().mapEntityToDomain(it)
        }
    }
}