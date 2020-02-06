package net.vinid.moviedb.data

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.annotation.WorkerThread
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers


abstract class NetworkBoundResource<ResultType, RequestType> {
    // result to observe to Repos
    var result: Observable<Resource<ResultType>>

    init {
        // local data
        val localSource: Observable<Resource<ResultType>>? = this.loadFromDb()?.toObservable()
                ?.map(object : Function<ResultType, Resource<ResultType>> {
                    override fun apply(t: ResultType): Resource<ResultType> {
                        return Resource.success(t)
                    }
                })!!

        // remote data
        var remoteSource : Observable<Resource<ResultType>>? = null

        if (this.shouldFetch()) {
            // request API
            remoteSource = this.createCall().subscribeOn(Schedulers.io())
                .doOnNext(
                    object : Consumer<Resource<RequestType>> {
                        override fun accept(apiRespone: Resource<RequestType>) {
                            // save db
                            saveCallResult(processResponse(apiRespone))
                        }
                    })
                .map(object : Function<Resource<RequestType>, Resource<ResultType>>{
                    override fun apply(t: Resource<RequestType>): Resource<ResultType>{
                        return convertRequestTypeToResultType(t)
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(object : Consumer<Throwable> {
                    override fun accept(t: Throwable?) {
                        onFetchFailed()
                    }
                })
        }

        // Internet connection available
        if (remoteSource != null) {
            // merge and distinct data from local and remote
            result = Observable.merge(localSource, remoteSource)
                .distinct()
        } else {
            // cannot connect internet
            if (localSource != null){
                result = localSource
            } else {
                result = Observable.empty()
            }
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
    }

    @MainThread
    protected abstract fun shouldFetch(): Boolean

    @NonNull
    @MainThread
    protected abstract fun loadFromDb(): Flowable<ResultType>?

    @NonNull
    @MainThread
    protected abstract fun createCall(): Observable<Resource<RequestType>>



}