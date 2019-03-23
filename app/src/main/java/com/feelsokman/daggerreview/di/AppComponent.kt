package com.feelsokman.daggerreview.di

import android.app.Application
import com.feelsokman.daggerreview.ReviewApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * The AppComponent is like a basket for your modules.
 *  Put all your modules here!
 *  Just throwing most of the stuff in AppModule for now to get things working
 */
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBuilderModule::class

    ]
)
// Dagger-Android specific thingy, pure copy pasta! Whatever dude
interface AppComponent : AndroidInjector<ReviewApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
