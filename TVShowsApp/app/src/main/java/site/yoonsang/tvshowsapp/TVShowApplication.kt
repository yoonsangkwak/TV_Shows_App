package site.yoonsang.tvshowsapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TVShowApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}