package com.test.anderson.searchable.application

import android.app.Application
import com.test.anderson.searchable.di.appModule
import com.test.anderson.searchable.di.retrofitModule
import com.test.anderson.searchable.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SearchableApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@SearchableApp)
            modules(
                listOf(
                    appModule,
                    retrofitModule,
                    searchModule
                )
            )
        }
    }
}