package net.vinid.moviedb.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import net.vinid.moviedb.R
import net.vinid.moviedb.base.BaseFragment
import net.vinid.moviedb.databinding.FragmentFavoritesBinding

/**
 * Created by Nguyen Van Lieu on 2/1/2020.
 */
class FavoritesFragment : BaseFragment() {

    //region MARK: - private filed
    private lateinit var dataBinding: FragmentFavoritesBinding

    //endregion

    //region MARK: - Fragment LifeCycle Method
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

    //endregion

}