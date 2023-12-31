package com.andrii_a.muze.domain.models

data class Artist(
    val id: Int,
    val name: String,
    val bornDateString: String?,
    val diedDateString: String?,
    val portraitImage: Image,
    val bio: String
)
