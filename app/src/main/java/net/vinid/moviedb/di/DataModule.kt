package net.vinid.moviedb.di

import android.content.Context
import dagger.Module
import dagger.Provides
import io.realm.Realm
import net.vinid.moviedb.data.local.dao.MovieDAO
import net.vinid.moviedb.data.local.dao.MovieDAOImpl
import net.vinid.moviedb.data.remote.api.APIService
import net.vinid.moviedb.data.remote.api.ConnectivityInterceptor
import net.vinid.moviedb.data.remote.api.RequestInterceptor
import net.vinid.moviedb.data.repository.MovieRepository
import net.vinid.moviedb.data.repository.MovieRepositoryImpl
import net.vinid.moviedb.utils.ConfigUtils
import net.vinid.moviedb.utils.ConstStrings
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideMovieLocalDataSource(movieDAOImpl: MovieDAOImpl): MovieDAO{
        return movieDAOImpl
    }

    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(retrofit: Retrofit): APIService{
        return retrofit.create(APIService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(ConstStrings.REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(ConstStrings.REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(ConstStrings.REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(RequestInterceptor())
            .addInterceptor(ConnectivityInterceptor(context))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ConstStrings.BASE_MOVIE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRealm(context: Context): Realm {
        Realm.init(context)
        val realmConfig = ConfigUtils.initRealmConfig()

        return Realm.getInstance(realmConfig)
    }

    @Provides
    @Singleton
    fun provideRepository(repositoryImpl: MovieRepositoryImpl): MovieRepository {
        return repositoryImpl
    }

}