package com.test.anderson.searchable.di

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import com.test.anderson.searchable.application.SearchableApp
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single { application }
    single { provideSettingsPreferences(androidApplication()) }
    single { provideContentResolver(get()) }
}

private val application = SearchableApp()

private fun provideSettingsPreferences(app: Application): SharedPreferences =
    app.getSharedPreferences("SHARED_PREFERENCES_NAME", Context.MODE_PRIVATE)

private fun provideContentResolver(context: Context): ContentResolver = context.contentResolver