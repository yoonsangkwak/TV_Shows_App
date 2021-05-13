package site.yoonsang.tvshowsapp.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import site.yoonsang.tvshowsapp.R
import site.yoonsang.tvshowsapp.util.ThemeProvider

class SettingsFragment : PreferenceFragmentCompat() {

    private val themeProvider by lazy { ThemeProvider(requireContext()) }
    private val themePreference by lazy { findPreference<ListPreference>(getString(R.string.theme_preferences_key)) }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preference, rootKey)
        setThemePreference()
    }

    private fun setThemePreference() {
        themePreference?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                if (newValue is String) {
                    val theme = themeProvider.getTheme(newValue)
                    AppCompatDelegate.setDefaultNightMode(theme)
                }
                true
            }
        themePreference?.summaryProvider = getThemeSummaryProvider()
    }

    private fun getThemeSummaryProvider() =
        Preference.SummaryProvider<ListPreference> { preference ->
            themeProvider.getThemeDescriptionForPreference(preference.value)
        }
}