package com.thewire.roomexperiment

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thewire.roomexperiment.database.model.ThingEntity

@Dao
interface ThingDao {
   @Insert
   suspend fun insertThing(thing: ThingEntity): Long

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insertThings(things: List<ThingEntity>): LongArray

   @Query("SELECT * FROM things WHERE id =:id ")
   suspend fun getThingById(id: Int): ThingEntity?


}