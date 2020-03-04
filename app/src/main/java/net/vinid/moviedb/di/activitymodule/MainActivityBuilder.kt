package net.vinid.moviedb.di.activitymodule

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.vinid.moviedb.MainActivity

@Module(includes = [FragmentModule::class])
interface MainActivityBuilder {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])

    fun contributeMainActivity(): MainActivity
}