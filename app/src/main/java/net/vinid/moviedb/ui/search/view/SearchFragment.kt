package net.vinid.moviedb.ui.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import kotlinx.coroutines.*
import net.vinid.moviedb.R
import net.vinid.moviedb.databinding.FragmentSearchBinding
import net.vinid.moviedb.ui.base.BaseFragment
import net.vinid.moviedb.ui.common.ViewPagerAdapter
import net.vinid.moviedb.util.AppUtils

/**
 * Created by Nguyen Van Lieu on 2/1/2020.
 */
class SearchFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentSearchBinding

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    private var searchJob: Job? = null

    private val sharedViewModel: SearchSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        dataBinding.lifecycleOwner = this

        initViewPager()
        initView()

        return dataBinding.root
    }

    private fun initView(){
        dataBinding.svSearchMovies.apply {
            isActivated = true
            queryHint = context.resources.getString(R.string.search_title_hint)
            onActionViewExpanded()
            isIconified = false
            clearFocus()
        }

        dataBinding.svSearchMovies.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    sharedViewModel.keyword.value = query
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()
                searchJob = coroutineScope.launch {
                    newText?.let {
                        delay(AppUtils.REQUEST_DELAY)
                        if (it.isEmpty()) {
                            sharedViewModel.keyword.value = newText
                        } else {
                            sharedViewModel.keyword.value = newText
                        }
                    }
                }
                return false
            }

        })
    }

    private fun initViewPager(){
        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)

        viewPagerAdapter.apply {
            addFragment(
                SearchByMoviesNameFragment(),getString(R.string.movies_name_title)
            )
            addFragment(
                SearchMoviesByCategoryFragment(),getString(R.string.category_title)
            )
            addFragment(
                SearchMoviesByGenresFragment(),getString(R.string.genres_label)
            )
        }
        dataBinding.moviesResultViewPager.apply {
            adapter = viewPagerAdapter
            offscreenPageLimit = OFF_SCREEN_PAGE_LIMIT
            currentItem = CURRENT_ITEM
        }
        dataBinding.searchMoviesTabLayout.setupWithViewPager(dataBinding.moviesResultViewPager)
    }

    companion object{
        const val OFF_SCREEN_PAGE_LIMIT = 3
        const val CURRENT_ITEM = 0
    }
}