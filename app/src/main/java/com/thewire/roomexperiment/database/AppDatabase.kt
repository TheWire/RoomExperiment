package com.thewire.roomexperiment.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thewire.roomexperiment.ThingDao
import com.thewire.roomexperiment.database.model.ThingEntity

@Database(entities = [ThingEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun thingDao(): ThingDao

    companion object {
        val DATABASE_NAME = "thing_db"
    }
}