package net.vinid.moviedb.data.local.dao

import android.util.Log
import io.realm.Realm
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.util.AppUtils

class MovieDAOImpl : MovieDAO {
    private val TAG = "MovieDAOImpl"
    override fun getMoviesByPageAndCategory(page: Int, category: String): List<MovieEntity>{
        val listMovie = ArrayList<MovieEntity>()
        val realmResult = Realm.getInstance(AppUtils.initRealmConfig())
            .where(MovieEntity::class.java)
            .equalTo(AppUtils.COLUMN_PAGE, page)
            .equalTo(AppUtils.COLUMN_MOVIE_CATEGORY, category)
            .findAll()
        if (!realmResult.isNullOrEmpty()) {
            listMovie.addAll(realmResult)
        }
        return listMovie
    }

    override fun deleteMovieByPageAndCategory(page: Int, category: String) {
        Realm.getInstance(AppUtils.initRealmConfig()).executeTransactionAsync { realm ->
            val result = realm.where(MovieEntity::class.java)
                .equalTo(AppUtils.COLUMN_PAGE, page)
                .equalTo(AppUtils.COLUMN_MOVIE_CATEGORY, category)
                .findAll()
            if (!result.isNullOrEmpty()){
                result.deleteAllFromRealm()
            }
        }
    }

    override fun saveListMovie(listMovie: List<MovieEntity>) {
        Realm.getInstance(AppUtils.initRealmConfig())
            .executeTransactionAsync { realm ->
                for (movie in listMovie){
                    // save movie to db
                    val currentIdNumber = realm.where(MovieEntity::class.java).max("id")
                    var nextId: Int?
                    nextId = currentIdNumber?.toInt()?.inc() ?: 0
                    movie.id = nextId
                    realm.copyToRealm(movie)
                    Log.d(TAG,"Save to db sc: "+movie.title+" , "+movie.category)
                }
            }
    }
}
