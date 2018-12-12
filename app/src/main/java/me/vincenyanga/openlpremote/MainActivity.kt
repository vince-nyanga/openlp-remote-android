package me.vincenyanga.openlpremote

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.vincenyanga.openlpremote.di.OpenLPViewModelFactory
import me.vincenyanga.openlpremote.domain.Result
import me.vincenyanga.openlpremote.model.Alert
import me.vincenyanga.openlpremote.ui.alert.AlertViewModel
import me.vincenyanga.openlpremote.ui.search.SearchActivity
import me.vincenyanga.openlpremote.ui.settings.SettingsActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {


    lateinit var navController: NavController
    @Inject lateinit var openLPViewModelFactory: OpenLPViewModelFactory
    private lateinit var viewModel: AlertViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.OpenLpTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(appBar)
        navController = findNavController(R.id.hostFragment)
        viewModel = ViewModelProviders.of(this@MainActivity, openLPViewModelFactory).get(AlertViewModel::class.java)
        searchButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, SearchActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            processMenuSelection(it.itemId)

        }
        return super.onOptionsItemSelected(item)
    }

    private fun processMenuSelection(itemId: Int) {
        when (itemId){
            R.id.service -> navController.navigate(R.id.serviceFragment)
            R.id.live ->  navController.navigate(R.id.liveViewFragment)
            R.id.settings -> startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
            R.id.alert -> displayShowAlertDialog()
        }
    }

    @SuppressLint("InflateParams")
    private fun displayShowAlertDialog(){
        val view = LayoutInflater.from(this@MainActivity).inflate(R.layout.layout_alert,null,false)
        val alertTextView = view.findViewById<TextInputEditText>(R.id.alertText)
        AlertDialog.Builder(this@MainActivity)
            .setTitle(R.string.display_alert)
            .setView(view)
            .setPositiveButton(R.string.display_alert_positive) { _, _ ->
                sendDisplayAlertCommand(alertTextView.text.toString())
            }
            .setNegativeButton(R.string.display_alert_negative, null)
            .show()

    }

    private fun sendDisplayAlertCommand(text: String) {
        if (text.isEmpty()){
            return
        }
        viewModel.displayAlert(Alert(text)).observe(this@MainActivity, Observer{
            when(it) {
                is Result.Error -> showErrorMessage(it.exception.message)
                is Result.Success -> showSuccessMessage(getString(R.string.alert_displayed))
            }
        })
    }

    private fun showSuccessMessage(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun showErrorMessage(message: String?) {
        Toast.makeText(this@MainActivity, getString(R.string.error) +message, Toast.LENGTH_SHORT).show()
    }

}
