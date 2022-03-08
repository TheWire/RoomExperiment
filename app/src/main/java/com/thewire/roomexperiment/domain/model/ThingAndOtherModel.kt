package com.thewire.roomexperiment.domain.model

data class ThingAndOtherModel(
    val id: Int,
    val description: String,
    val tf: Boolean,
    val embed: Embed,
    val other: OtherThing
)
