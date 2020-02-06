package net.vinid.moviedb.data.local.dao

import io.realm.Realm
import io.realm.RealmResults
import net.vinid.moviedb.data.local.entity.MovieEntity

class MovieDAO(realm: Realm) {
    private val _realm: Realm by lazy { realm }

    fun saveMovie(movie: MovieEntity, realm: Realm){
        realm.executeTransactionAsync { realm -> realm.copyToRealmOrUpdate(movie) }
    }

    fun getMoviesByPage(page: Int, category: Int): RealmResults<MovieEntity>{
        return _realm.where(MovieEntity::class.java)
            .equalTo("page",page)
            .equalTo("category",category)
            .findAll()
    }

    fun deleteMovies(realm: Realm){
        realm.executeTransactionAsync { realm -> realm.deleteAll() }
    }
}