package com.test.anderson.searchable.repositories

import com.test.anderson.searchable.network.Services
import com.test.anderson.searchable.base.Resource
import com.test.anderson.searchable.base.ResponseHandler
import com.test.anderson.searchable.models.AcronymResultResponseItem

class SearchRepository(
    private val responseHandler: ResponseHandler,
    private val services: Services
) {

    suspend fun getAcronymResult(queryMap: Map<String, String>): Resource<List<AcronymResultResponseItem>> {
        return try {
            val response = services.getAcronymResult(queryMap)
            responseHandler.handleSuccess(response)
        } catch (exception: Exception) {
            responseHandler.handleException(exception)
        }
    }
}