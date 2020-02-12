package net.vinid.moviedb.ui.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import net.vinid.moviedb.R
import net.vinid.moviedb.databinding.FragmentSearchByMovieNameBinding
import net.vinid.moviedb.ui.base.BaseFragment

/**
 * Created by Nguyen Van Lieu on 2/1/2020.
 */
class SearchByMoviesNameFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentSearchByMovieNameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search_by_movie_name, container, false)
        dataBinding.lifecycleOwner = this

        return dataBinding.root
    }

}