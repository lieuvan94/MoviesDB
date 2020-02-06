package net.vinid.moviedb.data.local.dao

import android.util.Log
import io.realm.Realm
import io.realm.RealmResults
import net.vinid.moviedb.data.local.entity.MovieEntity

class MovieDAO(realm: Realm) {
    private val _realm: Realm by lazy { realm }
    private val TAG = "MovieDAO"

    fun saveMovie(movie: MovieEntity, realm: Realm){
        realm.executeTransactionAsync(object : Realm.Transaction {
            override fun execute(realm: Realm) {
                realm.copyToRealmOrUpdate(movie)
            }
        })
    }

    fun loadAllMovie(): RealmResults<MovieEntity>{
        return _realm.where(MovieEntity::class.java).findAll()
    }

    fun getMoviesByPage(page: Int): RealmResults<MovieEntity>{
        return _realm.where(MovieEntity::class.java)
            .equalTo("page",page)
            .findAll()
    }

    fun saveMovieBackgroundThread(movie: MovieEntity){
        _realm.executeTransactionAsync(Realm.Transaction { realm -> realm.copyToRealmOrUpdate(movie) })
    }

    fun deleteAllMovie(realm: Realm){
        realm.executeTransactionAsync(object : Realm.Transaction {
            override fun execute(realm: Realm) {
               val query = realm.where(MovieEntity::class.java)
                Log.d(TAG,"deleteAllFromRealm - query find all")
                val realmResult = query.findAllAsync()
                Log.d(TAG,"realmResult: "+realmResult.size)
                if (realmResult != null && realmResult.size > 0){
                    Log.d(TAG,"deleteAllFromRealm")
                }
            }
        })
    }

    fun clearDB(){
        _realm.deleteAll()
    }
}