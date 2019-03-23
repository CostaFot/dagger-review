package com.feelsokman.daggerreview.di

import com.feelsokman.daggerreview.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    /**
     * This is how you inject into activities/fragments/android specific stuff
     * You gotta write "AndroidInjection.inject(this)"
     * right before your onCreate in the activity to make this work!
     */
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity
}
