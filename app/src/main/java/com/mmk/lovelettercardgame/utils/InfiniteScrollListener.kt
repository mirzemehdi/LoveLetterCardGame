package com.mmk.lovelettercardgame.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmk.lovelettercardgame.utils.callbacks.ILoadMore

class InfiniteScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val loadMore: ILoadMore
) : RecyclerView.OnScrollListener() {
    private var isLoading = true
    private var totalItemCount = 0
    private var lastVisibleItem = 0
    private var visibleThreshold = 5
    private var previousTotalCount = 0
    fun reset() {
        isLoading = true
        totalItemCount = 0
        lastVisibleItem = 0
        visibleThreshold = 5
        previousTotalCount = 0
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) {
            totalItemCount = layoutManager.itemCount
            lastVisibleItem = layoutManager.findLastVisibleItemPosition()
            if (isLoading && totalItemCount > previousTotalCount) {
                previousTotalCount = totalItemCount + 1 //+1 for progress item
                isLoading = false
            }
            if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                loadMore.onLoad()
                isLoading = true
            }
        }
    }

}