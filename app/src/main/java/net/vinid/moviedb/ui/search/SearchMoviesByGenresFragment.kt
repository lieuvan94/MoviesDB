package net.vinid.moviedb.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import net.vinid.moviedb.R
import net.vinid.moviedb.databinding.FragmentSearchByGenresBinding
import net.vinid.moviedb.ui.base.BaseFragment

/**
 * Created by Nguyen Van Lieu on 2/12/2020.
 */
class SearchMoviesByGenresFragment: BaseFragment() {

    private lateinit var dataBinding: FragmentSearchByGenresBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_by_genres
            , container, false)
        dataBinding.lifecycleOwner = this

        return dataBinding.root
    }
}