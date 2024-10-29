package com.andrii_a.muze.data.util

import com.andrii_a.muze.BuildConfig

const val BASE_URL = BuildConfig.BASE_URL
const val ARTISTS_URL = "$BASE_URL/artists"
const val ARTISTS_SEARCH_URL = "$ARTISTS_URL/search"
const val ARTWORKS_URL = "$BASE_URL/artworks"
const val ARTWORKS_BY_ARTIST_URL = "$BASE_URL/artworks/by-artist"
const val ARTWORKS_SEARCH_URL = "$ARTWORKS_URL/search"

const val INITIAL_PAGE_INDEX = 1
const val PAGE_SIZE = 20