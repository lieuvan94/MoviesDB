package net.vinid.moviedb.data.local.dao

import io.realm.Realm
import io.realm.RealmList
import net.vinid.moviedb.data.local.entity.GenreEntity
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

    override fun getMoviesByGenre(page: Int, genre: Int): List<MovieEntity> {
        val listMovie = ArrayList<MovieEntity>()
        val realmListGenre = RealmList<Int>()

        val realmResult = Realm.getInstance(AppUtils.initRealmConfig())
            .where(MovieEntity::class.java)
            .equalTo(AppUtils.COLUMN_PAGE,page)
            .findAll()
        if (!realmResult.isNullOrEmpty()) {
            for (item in realmResult){
                val listGenres = item.genreIds?.toList()
                if (listGenres?.contains(genre)!!){
                    listMovie.add(item)
                }
            }
        }
        return listMovie
    }

    override fun getListGenres(): List<GenreEntity> {
        val listGenres = ArrayList<GenreEntity>()
        val realmResult = Realm.getInstance(AppUtils.initRealmConfig())
            .where(GenreEntity::class.java)
            .findAll()
        if (!realmResult.isNullOrEmpty()) {
            listGenres.addAll(realmResult)
        }
        return listGenres
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

    override fun saveListGenres(listGenres: List<GenreEntity>) {
        Realm.getInstance(AppUtils.initRealmConfig())
            .executeTransactionAsync { realm ->
                for (genre in listGenres){
                    // save movie to db
                    realm.copyToRealmOrUpdate(listGenres)
                }
            }
    }
}
