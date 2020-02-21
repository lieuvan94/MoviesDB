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
        val listMovieByGenre = ArrayList<MovieEntity>()
        val listMovie = Realm.getInstance(AppUtils.initRealmConfig())
            .where(GenreEntity::class.java)
            .equalTo(AppUtils.COLUMN_GENRE_ID,genre)
            .findFirst()?.listMovie

        for (item in listMovie!!){
            if (item.page?.equals(page)!!){
                listMovieByGenre.add(item)
            }
        }

        return listMovieByGenre
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

    override fun saveListMovieByGenres(genreId: Int, page: Int, listMovie: RealmList<MovieEntity>) {
        Realm.getInstance(AppUtils.initRealmConfig())
            .executeTransactionAsync{ realm ->
                val realmResult = realm
                    .where(GenreEntity::class.java)
                    .equalTo(AppUtils.COLUMN_GENRE_ID, genreId)
                    .findFirst()

                if (!realmResult?.listMovie.isNullOrEmpty()){
                    for (item in realmResult?.listMovie!!){
                        if (item.page?.equals(page)!!){
                            realmResult.listMovie.remove(item)
                        }
                    }
                }

                realmResult?.listMovie?.addAll(listMovie)
//                var list = realmResult?.listMovie
//                Log.d("TEST", "MovieDAOImp: -saveListMovieByGenres - BEfore add - getList: " + realmResult?.listMovie?.size!!)
//                for (item in list!!) {
//                    Log.d(
//                        "TEST",
//                        "MovieDAOImp - saveListMovieByGenres - getList: " + item.id + ", " + item.title + ", " + item.page
//                    )
//                }
//                realmResult.listMovie.addAll(listMovie)
//                list = realmResult.listMovie
//                Log.d("TEST", "MovieDAOImp: -saveListMovieByGenres - After add - getList: " + list.size)
//                for (item in list) {
//                    Log.d(
//                        "TEST",
//                        "MovieDAOImp - saveListMovieByGenres - getList: " + item.id + ", " + item.title + ", " + item.page
//                    )
//                }
            }
    }
}
