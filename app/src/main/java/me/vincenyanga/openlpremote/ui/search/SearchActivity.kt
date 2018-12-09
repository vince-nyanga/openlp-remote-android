package me.vincenyanga.openlpremote.ui.search

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.layout_search.*
import me.vincenyanga.openlpremote.R
import me.vincenyanga.openlpremote.di.OpenLPViewModelFactory
import me.vincenyanga.openlpremote.domain.Result
import me.vincenyanga.openlpremote.model.SearchResult
import me.vincenyanga.openlpremote.setIsVisible
import javax.inject.Inject

class SearchActivity : DaggerAppCompatActivity() {

    private lateinit var viewModel: SearchViewModel
    @Inject
    lateinit var openLPViewModelFactory: OpenLPViewModelFactory
    private val adapter by lazy { PluginsAdapter(this@SearchActivity) }

    private val searchResultsAdapter by lazy {
        SearchResultsAdapter { view, result ->
            showOptions(view, result)
        }
    }


    var selectedPlugin = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this@SearchActivity, openLPViewModelFactory).get(SearchViewModel::class.java)

        setUpUi()

    }

    private fun setUpUi() {
        pluginSpinner.adapter = adapter
        pluginSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val plugin = adapter.getItem(position)!!
                selectedPlugin = plugin.key
            }

        }

        searchButton.setOnClickListener {
            searchPlugins()
        }
        val divider = DividerItemDecoration(this@SearchActivity, DividerItemDecoration.VERTICAL)
        searchResults.addItemDecoration(divider)
        searchResults.layoutManager = LinearLayoutManager(this@SearchActivity)
        searchResults.adapter = searchResultsAdapter

    }

    private fun searchPlugins() {
        if (selectedPlugin.isBlank()) {
            showMessage(getString(R.string.plugin_not_selected_error))
            return
        }

        if (searchText.text.toString().isBlank()) {
            showMessage(getString(R.string.select_search_text))
            return
        }

        viewModel.search(selectedPlugin, searchText.text.toString()).observe(this@SearchActivity, Observer { results ->
            when (results) {
                is Result.Success ->showSuccessState(results.data)
                is Result.Error -> showErrorState(results.exception)
                is Result.Loading -> showLoadingState()
            }
        })
    }

    private fun showMessage(message: String) {
        Toast.makeText(this@SearchActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        getPlugins()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getPlugins() {
        viewModel.getPlugins().observe(this@SearchActivity, Observer { result ->
            when (result) {
                is Result.Error -> showErrorState(result.exception)
                is Result.Success -> adapter.setData(result.data)
            }
        })
    }



    private fun showOptions(view: View, searchResult: SearchResult) {

        val menu = PopupMenu(this@SearchActivity, view)
        menu.inflate(R.menu.search_result_options)
        menu.setOnMenuItemClickListener { item ->
            when (item?.itemId) {
                R.id.addToLive -> addToLive(searchResult)
                R.id.addToService -> addToService(searchResult)

            }
            true
        }

        menu.show()

    }

    private fun addToLive(result: SearchResult){
        viewModel.addToLive(selectedPlugin, result.id).observe(this@SearchActivity, Observer { searchResult ->
            when(searchResult){
                is Result.Error -> showMessage("Error: ${searchResult.exception.message}")
                is Result.Success -> showMessage(getString(R.string.added_to_live))
            }
        })
    }

    private fun addToService(result:SearchResult){
        viewModel.addToService(selectedPlugin, result.id).observe(this@SearchActivity, Observer { searchResult ->
            when(searchResult){
                is Result.Error ->showError(searchResult.exception.message)
                is Result.Success -> showMessage(getString(R.string.added_to_service))
            }
        })
    }

    private fun showErrorState(exception: Exception) {
        emptyMessage.setIsVisible(false)
        searchResults.setIsVisible(false)
        loadingPb.setIsVisible(false)
        showError(exception.message)
    }

    private fun showLoadingState() {
        emptyMessage.setIsVisible(false)
        searchResults.setIsVisible(false)
        loadingPb.setIsVisible(true)
    }

    private fun showSuccessState(data: List<SearchResult>) {

        searchResults.postDelayed({
            loadingPb.visibility = View.GONE
        }, 150)

        searchResults.postDelayed({
            emptyMessage.setIsVisible(data.isEmpty())
            searchResults.setIsVisible(!data.isEmpty())
            searchResultsAdapter.submitList(data)
        }, 150)
    }

    private fun showError(message: String?){
        Toast.makeText(this@SearchActivity, "Error: $message", Toast.LENGTH_SHORT).show()
    }
}
