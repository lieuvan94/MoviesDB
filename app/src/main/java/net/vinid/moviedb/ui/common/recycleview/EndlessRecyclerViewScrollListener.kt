package net.vinid.moviedb.ui.common.recycleview

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class EndlessRecyclerViewScrollListener(private val layoutManager: RecyclerView.LayoutManager) :
    RecyclerView.OnScrollListener() {
    private var visibleThreshold = 5
    private var currentPage = 1
    private var previousTotalItemCount = 0
    private var loading = true

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = layoutManager.itemCount

        if (layoutManager is GridLayoutManager) {
            lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        } else if (layoutManager is LinearLayoutManager) {
            lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, totalItemCount, view)
            setLastPosition(view)
            loading = true
        }
    }

    abstract fun setLastPosition(view: RecyclerView)
    abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?)
}