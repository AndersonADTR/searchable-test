package com.test.anderson.searchable.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.anderson.searchable.providers.MySuggestionProvider
import com.test.anderson.searchable.R
import com.test.anderson.searchable.base.BaseActivity
import com.test.anderson.searchable.base.Resource
import com.test.anderson.searchable.base.Status
import com.test.anderson.searchable.databinding.ActivityMainBinding
import com.test.anderson.searchable.models.AcronymResultResponseItem
import com.test.anderson.searchable.models.LfsItem
import com.test.anderson.searchable.ui.fragments.AnotherResultsBottomSheetFragment
import com.test.anderson.searchable.ui.views.ResultItem
import com.test.anderson.searchable.viewmodels.SearchViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import org.koin.java.KoinJavaComponent

class MainActivity : BaseActivity() {

    private val viewModel by KoinJavaComponent.inject(SearchViewModel::class.java)

    private val recyclerAdapter by lazy { GroupAdapter<GroupieViewHolder>() }

    private lateinit var binding: ActivityMainBinding
    private lateinit var onItemClickListener: OnItemClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        handleIntent(intent)
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_search -> true
            R.id.action_clear -> {
                showClearHistoryAlert()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    override fun showLoader(show: Boolean) {
        binding.textViewLabel.isVisible = false
        binding.progressCircular.isVisible = show
    }

    private fun initView() {
        initListeners()
        subscribeToObservable()
        setupRecycler()
    }

    private fun initListeners() {
        onItemClickListener =
            OnItemClickListener { item, _ ->
                if (item is ResultItem) {
                    val lfsItem = item.getItem()
                    if (lfsItem is LfsItem) {
                        onClickAction(lfsItem)
                    }
                }
            }
    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                viewModel.getAcronymResult(query)
                SearchRecentSuggestions(
                    this,
                    MySuggestionProvider.AUTHORITY,
                    MySuggestionProvider.MODE
                )
                    .saveRecentQuery(query, null)
            }
        }
    }

    private fun handleResponse(resource: Resource<List<AcronymResultResponseItem>>) {
        when (resource.status) {
            Status.LOADING -> showLoader(true)
            Status.SUCCESS -> updateAdapter(resource.data)
            Status.ERROR -> showError(resource.message.orEmpty())
        }
    }

    private fun subscribeToObservable() {
        viewModel.result.observe(this, {
            handleResponse(it)
        })
    }

    private fun setupRecycler() {
        val manager = LinearLayoutManager(applicationContext)

        recyclerAdapter.clear()
        recyclerAdapter.setOnItemClickListener(onItemClickListener)

        binding.rvAcronym.apply {
            layoutManager = manager
            adapter = recyclerAdapter
        }
    }

    private fun updateAdapter(data: List<AcronymResultResponseItem>?) {
        showLoader(false)
        if (data.orEmpty().isNotEmpty()) {
            recyclerAdapter.clear()
            data?.forEach { item ->
                item.lfs?.forEach {
                    recyclerAdapter.add(ResultItem(it))
                }
            }
        }
    }

    private fun onClickAction(item: LfsItem) {
        AnotherResultsBottomSheetFragment.newInstance(item)
            .show(supportFragmentManager, AnotherResultsBottomSheetFragment.TAG)
    }

    private fun showClearHistoryAlert() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.action_clear))
            .setMessage(getString(R.string.are_you_sure_clear))
            .setPositiveButton(R.string.yes) { dialog, _ ->
                dialog.dismiss()
                clearHistory()
            }
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun clearHistory() {
        SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
            .clearHistory()
    }
}