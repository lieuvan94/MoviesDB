package net.vinid.moviedb.data.local.dao

import io.realm.Realm
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.util.AppUtils

class MovieDAOImpl : MovieDAO {
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

    override fun saveListMovie(listMovie: List<MovieEntity>, category: String, page: Int) {
        Realm.getInstance(AppUtils.initRealmConfig())
            .executeTransactionAsync { realm ->
                for (movie in listMovie){
                    // save movie to db
                    realm.copyToRealmOrUpdate(movie)
                }
            }
    }
}
