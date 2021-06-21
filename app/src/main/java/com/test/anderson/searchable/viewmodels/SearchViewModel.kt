package com.test.anderson.searchable.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.anderson.searchable.base.Resource
import com.test.anderson.searchable.models.AcronymResultResponseItem
import com.test.anderson.searchable.repositories.SearchRepository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: SearchRepository
) : ViewModel() {

    private val _result by lazy { MutableLiveData<Resource<List<AcronymResultResponseItem>>>() }
    val result: LiveData<Resource<List<AcronymResultResponseItem>>> = _result

    private fun buildQueryParams(query: String): Map<String, String> {
        return mapOf(
            SF to query
        )
    }

    fun getAcronymResult(query: String) {
        _result.postValue(Resource.loading(null))
        viewModelScope.launch {
            _result.postValue(
                repository.getAcronymResult(buildQueryParams(query))
            )
        }
    }

    companion object {
        internal const val SF = "sf"
    }
}