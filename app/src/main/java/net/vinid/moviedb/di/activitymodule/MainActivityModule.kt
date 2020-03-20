package net.vinid.moviedb.di.activitymodule

import androidx.appcompat.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import net.vinid.moviedb.MainActivity

@Module
interface MainActivityModule {

    @Binds
    fun bindMainActivity(activity: MainActivity): AppCompatActivity

}