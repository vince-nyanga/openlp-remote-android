package me.vincenyanga.openlpremote.ui.live

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.live_view_fragment.*

import me.vincenyanga.openlpremote.R
import me.vincenyanga.openlpremote.di.OpenLPViewModelFactory
import me.vincenyanga.openlpremote.domain.Result
import me.vincenyanga.openlpremote.model.RequestResult
import javax.inject.Inject

class LiveViewFragment : DaggerFragment() {

    companion object {
        fun newInstance() = LiveViewFragment()
    }

    private lateinit var viewModel: LiveViewViewModel
    @Inject lateinit var openLPViewModelFactory: OpenLPViewModelFactory
    private val liveViewAdapter = LiveViewAdapter {
        selectSlide(it.selectionId!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.live_view_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, openLPViewModelFactory).get(LiveViewViewModel::class.java)
        refreshBtn.setOnClickListener { viewModel.refresh() }
        slides.layoutManager = LinearLayoutManager(context)
        slides.adapter = liveViewAdapter
    }

    private fun selectSlide(selectionId: Int) {
        viewModel.selectSlide(selectionId).observe(this@LiveViewFragment, Observer<Result<RequestResult>> {
            when(it){
                is Result.Success -> {
                    viewModel.refresh()
                }
                is Result.Error -> showError(it.exception.message)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.liveViewData.observe(this@LiveViewFragment, Observer {
            when(it){
                is Result.Error -> showError(it.exception.message)
                is Result.Success -> {
                    liveViewAdapter.submitList(it.data.slides)
                }
            }
        })
        viewModel.refresh()
    }

    private fun showError(message: String?) {
        Toast.makeText(context, "Error: $message", Toast.LENGTH_SHORT).show()
    }

    private fun showLiveViewTab(){
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val host = sharedPreferences.getString(getString(R.string.pref_host_key),"")
        val port = sharedPreferences.getString(getString(R.string.pref_port_key),"")
        val address = "$host:$port/main"
        val customTabsIntent = CustomTabsIntent.Builder()
            .addDefaultShareMenuItem()
            .setToolbarColor(resources.getColor(R.color.colorPrimary))
            .setShowTitle(true)
            .setCloseButtonIcon(BitmapFactory.decodeResource(resources,R.drawable.ic_arrow_back_black_24dp))
            .build()
        customTabsIntent.launchUrl(context, Uri.parse(address))


    }

}
