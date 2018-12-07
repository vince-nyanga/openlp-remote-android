package me.vincenyanga.openlpremote.ui.settings

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import me.vincenyanga.openlpremote.BuildConfig
import me.vincenyanga.openlpremote.R

class SettingsFragment: PreferenceFragmentCompat() {

    private val versionSummaryProvider = Preference.SummaryProvider<Preference> {
        BuildConfig.VERSION_NAME
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_settings, rootKey)
        findPreference(getString(R.string.pref_version_key)).summaryProvider = versionSummaryProvider
    }
}