package me.vincenyanga.openlpremote.ui.service

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.service_fragment.*
import me.vincenyanga.openlpremote.R
import me.vincenyanga.openlpremote.di.OpenLPViewModelFactory
import me.vincenyanga.openlpremote.domain.Result
import me.vincenyanga.openlpremote.model.RequestResult
import me.vincenyanga.openlpremote.model.ServiceData
import me.vincenyanga.openlpremote.model.ServiceItem
import me.vincenyanga.openlpremote.setIsVisible
import timber.log.Timber
import javax.inject.Inject

class ServiceFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: OpenLPViewModelFactory
    private lateinit var viewModel: ServiceViewModel
    private val serviceItemCallbacks = object : ServiceItemCallbacks {
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

    private val serviceItemsAdapter = ServiceItemsAdapter(serviceItemCallbacks)


    private fun selectItem(selectionId: Int) {
        viewModel.selectItem(selectionId).observe(this@ServiceFragment, Observer<Result<RequestResult>> { result ->
            when (result) {
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
        serviceItemsAdapter.setHasStableIds(true)
        serviceItems.layoutManager = LinearLayoutManager(context)
        serviceItems.adapter = serviceItemsAdapter
        refreshBtn.setOnClickListener {
            viewModel.refresh()
        }
        subscribeUi()
    }

    private fun showError(message: String?) {
        Toast.makeText(context, "Error: $message", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    private fun subscribeUi() {
        viewModel.serviceData.observe(this@ServiceFragment,
            Observer<Result<ServiceData>> { result ->
                when (result) {
                    is Result.Error -> {
                        Timber.e(result.exception)
                        showErrorState(result.exception)
                    }
                    is Result.Success -> showSuccessState(result.data.items)

                    is Result.Loading -> showLoadingState()
                }
            })
    }

    private fun showErrorState(exception: Exception) {
        emptyMessage.setIsVisible(false)
        serviceItems.setIsVisible(false)
        loadingPb.setIsVisible(false)
        showError(exception.message)
    }

    private fun showSuccessState(data: List<ServiceItem>) {

        serviceItemsAdapter.submitList(data)
        serviceItems.postDelayed({
            loadingPb.visibility = View.GONE
        }, 150)

        serviceItems.postDelayed({
            emptyMessage.setIsVisible(data.isEmpty())
            serviceItems.setIsVisible(!data.isEmpty())
            serviceItemsAdapter.submitList(data)
        }, 150)


    }

    private fun showLoadingState() {
        emptyMessage.setIsVisible(false)
        serviceItems.setIsVisible(false)
        loadingPb.setIsVisible(true)
    }


}
