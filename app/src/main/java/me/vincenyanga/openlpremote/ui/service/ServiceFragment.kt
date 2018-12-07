package me.vincenyanga.openlpremote.ui.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.service_fragment.*
import me.vincenyanga.openlpremote.R
import me.vincenyanga.openlpremote.di.OpenLPViewModelFactory
import me.vincenyanga.openlpremote.domain.Result
import me.vincenyanga.openlpremote.model.RequestResult
import me.vincenyanga.openlpremote.model.ServiceData
import me.vincenyanga.openlpremote.model.ServiceItem
import timber.log.Timber
import javax.inject.Inject

class ServiceFragment : DaggerFragment() {

    companion object {
        fun newInstance() = ServiceFragment()
    }

    @Inject lateinit var viewModelFactory:OpenLPViewModelFactory
    private lateinit var viewModel: ServiceViewModel

    lateinit var serviceItemsAdapter: ServiceItemsAdapter

    private val serviceItemCallbacks = object :ServiceItemCallbacks {
        override fun onItemSelected(item: ServiceItem) {
            selectItem(item.selectionId!!)
        }

        override fun onNextSlideClicked() {
            viewModel.goToNextSlide()
        }

        override fun onPreviousSlideClicked() {
            viewModel.goToPrevSlide()
        }
    }

    private fun selectItem(selectionId: Int) {
        viewModel.selectItem(selectionId).observe(this@ServiceFragment, Observer<Result<RequestResult>> { result ->
            when (result){
                is Result.Success -> viewModel.refresh()
                is Result.Error -> showError(result.exception.message)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.service_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ServiceViewModel::class.java)
        serviceItemsAdapter = ServiceItemsAdapter(serviceItemCallbacks)

        serviceItems.layoutManager = LinearLayoutManager(context)
        serviceItems.adapter = serviceItemsAdapter
    }

    private fun showError(message: String?) {
        Toast.makeText(context, "Error: $message", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        subscribeUi()

    }

    private fun subscribeUi() {
        viewModel.serviceData.observe(this@ServiceFragment,
            Observer<Result<ServiceData>> { t ->
                when (t){
                    is Result.Error -> {
                        Timber.e(t.exception)
                        Toast.makeText(context, "${t.exception.message}", Toast.LENGTH_LONG).show()

                    }
                    is Result.Success -> {
                       serviceItemsAdapter.submitList(t.data.items)
                    }
                    is Result.Loading -> {

                    }
                }
            })
        viewModel.refresh()
    }

}
