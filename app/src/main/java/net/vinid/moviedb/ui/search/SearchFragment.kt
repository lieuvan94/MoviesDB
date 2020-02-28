package net.vinid.moviedb.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import net.vinid.moviedb.R
import net.vinid.moviedb.databinding.FragmentSearchBinding
import net.vinid.moviedb.ui.base.BaseFragment
import net.vinid.moviedb.ui.home.MoviesViewModel
import javax.inject.Inject

/**
 * Created by Nguyen Van Lieu on 2/1/2020.
 */
class SearchFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentSearchBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val moviesViewModel by viewModels<MoviesViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        dataBinding.lifecycleOwner = this

        return dataBinding.root
    }
}