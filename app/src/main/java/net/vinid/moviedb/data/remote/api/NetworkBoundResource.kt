package net.vinid.moviedb.data.remote.api

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.annotation.WorkerThread
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


abstract class NetworkBoundResource<ResultType, RequestType> {
    private val TAG = "NetworkBoundResource"

    // result to observe to Repos
    var result: Observable<Resource<ResultType>>

    init {
        if (this.shouldFetch()){
            result = this.loadFromDb().toObservable().map{ t -> Resource.success(t) }.concatWith(
                this.createCall()
                    .doOnNext {
                        // save remote data to db
                        saveCallResult(processResponse(it))
                    }
                    .distinct()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { convertRequestTypeToResultType(it) }
            )
        }
        else {
            result = this.loadFromDb().toObservable()?.map{ t -> Resource.success(t) }!!
        }
    }

    protected abstract fun convertRequestTypeToResultType(requestType: Resource<RequestType>): Resource<ResultType>

    @WorkerThread
    protected open fun processResponse(response: Resource<RequestType>): RequestType {
        return response.data!!
    }

    @WorkerThread
    protected abstract fun saveCallResult(@NonNull item: RequestType)

    protected open fun onFetchFailed() {
        // Todo: Show dialog "No internet"
        Log.d(TAG,"Fetch failed")
    }

    @MainThread
    protected abstract fun shouldFetch(): Boolean

    @NonNull
    @MainThread
    protected abstract fun loadFromDb(): Flowable<ResultType>

    @NonNull
    @MainThread
    protected abstract fun createCall(): Observable<Resource<RequestType>>



}