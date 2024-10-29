package com.andrii_a.muze.data.dto

import com.andrii_a.muze.domain.models.Image
import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(
    val width: Int,
    val height: Int,
    val url: String
) {
    fun toImage(): Image = Image(width, height, url)
}