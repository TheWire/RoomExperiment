package com.thewire.roomexperiment

import androidx.room.*
import com.thewire.roomexperiment.database.model.*

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

   @Insert
   suspend fun insertAnotherThing(anotherThing: AnotherThingEntity): Long

   @Transaction
   @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insertOtherAndAnother(otherAndAnother: OtherAndAnother): Long {
      val id = insertAnotherThing(otherAndAnother.anotherThing)
      return insertOtherThing(otherAndAnother.otherThingEntity.copy(anotherThing = id.toInt()))
   }

   @Transaction
   @Query("SELECT * FROM things WHERE ID =:id")
   suspend fun getThingWithOther(id: Int): ThingAndOther

   @Transaction
   @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insertThingAndOther(thingAndOther: ThingAndOther): Long {
      val id = thingAndOther.otherThingEntity?.let { otherAndAnother ->
         insertOtherAndAnother(otherAndAnother)
      }
      return insertThing(thingAndOther.thing.copy(otherThingId = id?.toInt()))
   }

   @Transaction
   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertThingsAndOthers(things: List<ThingAndOther>): LongArray {
      val ret = arrayListOf<Long>()
      things.forEach{
         ret.add(insertThingAndOther(it))
      }
      return ret.toLongArray()
   }

   @Query("DELETE FROM things WHERE ID=:id")
   suspend fun deleteThing(id: Int): Int

   @Query("DELETE FROM things")
   suspend fun deleteAllThings(): Int

}