package net.vinid.moviedb.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import net.vinid.moviedb.R
import net.vinid.moviedb.databinding.FragmentSearchBinding

/**
 * Created by Nguyen Van Lieu on 2/1/2020.
 */
class SearchFragment : Fragment() {

    private lateinit var dataBinding: FragmentSearchBinding

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