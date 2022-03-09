package com.thewire.roomexperiment.domain.model

import android.net.Uri

data class OtherThing(
    val b: Boolean,
    val i: Int,
    val uri: Uri,
    val anotherThing: AnotherThing,
)
