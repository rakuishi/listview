package com.rakuishi.listview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LoadMoreScrollListener(
    private var linearLayoutManager: LinearLayoutManager,
    private var onLoadMore: (() -> Unit)
) : RecyclerView.OnScrollListener() {

    private var previousTotalCount: Int = 0
    private var isLoading: Boolean = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = recyclerView.childCount
        val totalItemCount = linearLayoutManager.itemCount
        val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()

        if (isLoading && totalItemCount > previousTotalCount) {
            isLoading = false
            previousTotalCount = totalItemCount
        }

        if (!isLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItemPosition + visibleItemCount)) {
            onLoadMore()
            isLoading = true
        }
    }

    fun resetState() {
        previousTotalCount = 0
        isLoading = true
    }
}