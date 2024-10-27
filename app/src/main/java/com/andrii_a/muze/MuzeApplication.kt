package com.andrii_a.muze

import android.app.Application
import com.andrii_a.muze.di.artistsModule
import com.andrii_a.muze.di.artworksModule
import com.andrii_a.muze.di.baseNetworkModule
import com.andrii_a.muze.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MuzeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MuzeApplication)
            modules(baseNetworkModule, artistsModule, artworksModule, searchModule)
        }
    }
}