package com.andrii_a.muze.di

import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify

@OptIn(KoinExperimentalAPI::class)
class CheckModulesTest : KoinTest {

    @Test
    fun checkBaseNetworkModule() {
        baseNetworkModule.verify()
    }

    @Test
    fun checkArtistsModule() {
        artistsModule.verify()
    }

    @Test
    fun checkArtworksModule() {
        artworksModule.verify()
    }

    @Test
    fun checkSearchModule() {
        searchModule.verify()
    }

}