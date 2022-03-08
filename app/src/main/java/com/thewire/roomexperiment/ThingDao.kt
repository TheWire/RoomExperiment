package com.thewire.roomexperiment

import androidx.room.*
import com.thewire.roomexperiment.database.model.OtherThingEntity
import com.thewire.roomexperiment.database.model.ThingAndOther
import com.thewire.roomexperiment.database.model.ThingEntity

@Dao
interface ThingDao {
   @Insert
   suspend fun insertThing(thing: ThingEntity): Long

   @Insert
   suspend fun insertOtherThing(otherThing: OtherThingEntity): Long

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insertThings(things: List<ThingEntity>): LongArray

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insertOtherThings(otherThings: List<OtherThingEntity>): LongArray

   @Query("SELECT * FROM things WHERE id =:id ")
   suspend fun getThingById(id: Int): ThingEntity?

   @Query("SELECT * FROM things WHERE embedTest_exampleInt=:exInt")
   suspend fun getEmbedByInt(exInt: Int): List<ThingEntity>

   @Transaction
   @Query("SELECT * FROM things WHERE ID =:id")
   suspend fun getThingWithOther(id: Int): ThingAndOther

   @Transaction
   @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insertThingAndOther(thingAndOther: ThingAndOther): Long {
      val id = insertOtherThing(thingAndOther.otherThingEntity)

      return insertThing(thingAndOther.thing.copy(otherThingId = id.toInt()))
   }

}