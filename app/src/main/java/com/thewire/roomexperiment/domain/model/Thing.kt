package com.thewire.roomexperiment.domain.model

data class Thing(
    val id: Int,
    val description: String,
    val tf: Boolean,
    val embed: Embed,
    val n: String?
)
