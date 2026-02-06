package com.example.inazumaelevenexpress2

import android.app.Application
import com.example.inazumaelevenexpress2.data.AppContainer
import com.example.inazumaelevenexpress2.data.DefaultAppContainer

class InazumaElevenExpressApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}