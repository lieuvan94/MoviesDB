package net.vinid.moviedb

import android.app.Application
import io.realm.Realm
import net.vinid.moviedb.data.local.dao.MovieDAO
import net.vinid.moviedb.data.local.dao.MovieDAOImpl
import net.vinid.moviedb.data.remote.api.APIClient
import net.vinid.moviedb.data.remote.api.APIService
import net.vinid.moviedb.data.remote.api.APIServiceImpl
import net.vinid.moviedb.data.repository.MovieRepository
import net.vinid.moviedb.data.repository.MovieRepositoryImpl
import net.vinid.moviedb.ui.ViewModelFactory
import net.vinid.moviedb.ui.home.MoviesViewModel
import net.vinid.moviedb.util.AppUtils
import net.vinid.moviedb.util.NetworkManager
import retrofit2.Retrofit

class MovieApplication: Application(){

    companion object{
        private lateinit var retrofit: Retrofit
        private lateinit var apiService: APIService
        private lateinit var movieDAO: MovieDAO
        private lateinit var moviesViewModel: MoviesViewModel
        private lateinit var movieRepository: MovieRepository
        private lateinit var networkManager: NetworkManager
        private lateinit var viewModelFactory: ViewModelFactory

        fun injectViewModelFactory() = viewModelFactory
    }

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        Realm.setDefaultConfiguration(AppUtils.initRealmConfig())

        retrofit = APIClient.getClient(this)

        apiService = APIServiceImpl(retrofit.create(APIService::class.java))

        movieDAO = MovieDAOImpl()

        networkManager = NetworkManager(applicationContext)

        movieRepository = MovieRepositoryImpl(movieDAO, apiService)

        viewModelFactory = ViewModelFactory(movieRepository)
    }
}