package net.vinid.moviedb.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Nguyen Van Lieu on 1/31/2020.
 */
open class BaseViewModel : ViewModel(){

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * TODO: handle the Rx subscription
     */
    protected fun addToDisposable(disposable: Disposable) {
        compositeDisposable.remove(disposable)
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}