package com.thewire.roomexperiment.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thewire.roomexperiment.ThingDao
import com.thewire.roomexperiment.database.model.OtherThingEntity
import com.thewire.roomexperiment.database.model.ThingEntity
import com.thewire.roomexperiment.database.model.ThingTypeConverter

@Database(entities = [ThingEntity::class, OtherThingEntity::class], version = 5)
@TypeConverters(ThingTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun thingDao(): ThingDao
    companion object {
        val DATABASE_NAME = "thing_db"
    }
}