package com.thewire.roomexperiment.database.model

import android.net.Uri
import androidx.room.TypeConverter

class ThingTypeConverter {

    @TypeConverter
    fun toUri(uri: String): Uri {
        return Uri.parse(uri)
    }

    @TypeConverter
    fun fromUri(uri: Uri): String {
        return uri.toString()
    }
}