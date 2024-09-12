package com.ngg.cloudefirestoreexemplos.themes

import android.app.Application
import com.google.android.material.color.DynamicColors

class ThemasAplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}