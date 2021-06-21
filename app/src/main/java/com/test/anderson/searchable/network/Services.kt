package com.test.anderson.searchable.network

import com.test.anderson.searchable.models.AcronymResultResponseItem
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface Services {

    @GET("acromine/dictionary.py/")
    suspend fun getAcronymResult(@QueryMap queryMap: Map<String, String>): List<AcronymResultResponseItem>

    companion object {
        const val BASE_URL = "http://www.nactem.ac.uk/software/"
    }
}