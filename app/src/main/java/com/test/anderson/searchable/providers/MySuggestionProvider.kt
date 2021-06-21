package com.test.anderson.searchable.providers

import android.content.SearchRecentSuggestionsProvider

class MySuggestionProvider : SearchRecentSuggestionsProvider() {

    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.test.anderson.searchable.providers.MySuggestionProvider"
        const val MODE: Int = DATABASE_MODE_QUERIES
    }
}