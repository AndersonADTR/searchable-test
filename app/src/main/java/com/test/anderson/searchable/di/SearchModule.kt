package com.test.anderson.searchable.di

import com.test.anderson.searchable.network.Services
import com.test.anderson.searchable.base.ResponseHandler
import com.test.anderson.searchable.repositories.SearchRepository
import com.test.anderson.searchable.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val searchModule = module {

    fun provideViewModel(repository: SearchRepository): SearchViewModel {
        return SearchViewModel(repository)
    }

    fun provideRepository(
        responseHandler: ResponseHandler,
        services: Services
    ): SearchRepository {
        return SearchRepository(responseHandler, services)
    }

    fun provideService(retrofit: Retrofit): Services {
        return retrofit.create(Services::class.java)
    }

    viewModel { provideViewModel(get()) }
    single { provideRepository(get(), get()) }
    single { provideService(get()) }
}