package net.vinid.moviedb.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import net.vinid.moviedb.R
import net.vinid.moviedb.databinding.FragmentFavoritesBinding
import net.vinid.moviedb.ui.base.BaseFragment

/**
 * Created by Nguyen Van Lieu on 2/1/2020.
 */
class FavoritesFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        dataBinding.lifecycleOwner = this

        return dataBinding.root
    }
}