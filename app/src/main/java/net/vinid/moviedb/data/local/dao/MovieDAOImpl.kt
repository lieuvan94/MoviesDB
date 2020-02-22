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
            }
    }

    override fun updateMovieStatus(movieEntity: MovieEntity, isLike: Boolean) {
        Realm.getInstance(AppUtils.initRealmConfig())
            .executeTransaction { realm ->
                val movie: MovieEntity = movieEntity
                movie.isLike = isLike
                realm.copyToRealmOrUpdate(movie)
            }
    }

    override fun getMoviesLiked(): List<MovieEntity> {
        val listMovies = ArrayList<MovieEntity>()
        val realmResult = Realm.getInstance(AppUtils.initRealmConfig())
            .where(MovieEntity::class.java)
            .equalTo(AppUtils.COLUMN_MOVIE_IS_LIKE, true)
            .findAll()
        if (!realmResult.isNullOrEmpty()) {
            listMovies.addAll(realmResult)
        }
        return listMovies
    }
}
