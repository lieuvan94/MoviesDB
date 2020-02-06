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
    var result: Observable<Resource<ResultType>>
    val TAG = "NetworkBoundResource"

    init {
        var source: Observable<Resource<ResultType>>
        // request API
        if (this.shouldFetch()) {
            source = this.createCall().subscribeOn(Schedulers.io())
                .doOnNext(
                    object : Consumer<Resource<RequestType>> {
                        override fun accept(apiRespone: Resource<RequestType>) {
                            // save db
                            saveCallResult(processResponse(apiRespone))
                            Log.d(TAG,"Save db success")
                        }
                    })
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(object :
                    Function<Resource<RequestType>, Observable<Resource<ResultType>>> {
                    override fun apply(t: Resource<RequestType>): Observable<Resource<ResultType>> {
                        return loadFromDb()?.toObservable()
                            ?.map(object : Function<ResultType, Resource<ResultType>> {
                                override fun apply(t: ResultType): Resource<ResultType> {
                                    return Resource.success(t)
                                }
                            })!!
                    }
                })
                .doOnError(object : Consumer<Throwable> {
                    override fun accept(t: Throwable?) {
                        onFetchFailed()
                    }
                })
        } else {
            // call api error -> load data from local
            Log.d(TAG,"Call api err -> Load from local")
            source = this.loadFromDb()?.toObservable()
                ?.map(object : Function<ResultType, Resource<ResultType>> {
                    override fun apply(t: ResultType): Resource<ResultType> {
                        return Resource.success(t)
                    }
                })!!
        }
        // merge remote source and local source
        Log.d(TAG,"Load from db")
        result = Observable.merge(source,
            this.loadFromDb()?.toObservable()
            ?.map(object : Function<ResultType, Resource<ResultType>>{
                override fun apply(t: ResultType): Resource<ResultType> {
                    return Resource.loading(t)
                }
            }))
            .distinct()
        Log.d(TAG,"Load from db and merge and distine with remote")
    }

    @WorkerThread
    protected open fun processResponse(response: Resource<RequestType>): RequestType {
        return response.data!!
    }

    @WorkerThread
    protected abstract fun saveCallResult(@NonNull item: RequestType)

    protected open fun onFetchFailed() {
        Log.d(TAG,"Fetch failed")
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