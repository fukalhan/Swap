package cz.cvut.fukalhan.swap.system

import android.app.Application
import cz.cvut.fukalhan.swap.di.setupKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }
}