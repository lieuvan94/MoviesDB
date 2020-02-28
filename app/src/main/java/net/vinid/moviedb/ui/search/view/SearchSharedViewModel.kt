package net.vinid.moviedb.ui.search.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Nguyen Van Lieu on 2/14/2020.
 */
class SearchSharedViewModel :ViewModel(){
    val keyword =  MutableLiveData<String>()

}