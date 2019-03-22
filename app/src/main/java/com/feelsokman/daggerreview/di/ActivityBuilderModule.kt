package com.feelsokman.daggerreview.di

import com.feelsokman.daggerreview.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    // this is how you inject into activites/fragments/android specific stuff
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity
}
