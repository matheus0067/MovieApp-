package com.matheus.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieVideosResponseDto(
    @SerializedName("results") val videos: List<MovieVideoDto> = emptyList()
)

