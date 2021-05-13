package site.yoonsang.tvshowsapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import site.yoonsang.tvshowsapp.util.ThemeProvider

@HiltAndroidApp
class TVShowApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        val theme = ThemeProvider(this).getThemeFromPreferences()
        AppCompatDelegate.setDefaultNightMode(theme)
    }
}