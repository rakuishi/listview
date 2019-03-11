package com.rakuishi.listview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class ListView : RelativeLayout, ListItemOperator {

    var recyclerView: RecyclerView
    var swipeRefreshLayout: SwipeRefreshLayout

    var onRefreshListener: (() -> Unit)? = null
        set(value) {
            swipeRefreshLayout.isEnabled = value != null
            field = value
        }

    var onLoadMoreListener: (() -> Unit)? = null
        set(value) {
            recyclerView.addOnScrollListener(loadMoreScrollListener)
            field = value
        }

    var hasMore: Boolean = false
    var page = 0
    var loadingListItem: ListItem? = null

    private var listViewAdapter: ListViewAdapter
    private var loadMoreScrollListener: LoadMoreScrollListener

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.lv_list_view, this)
        recyclerView = findViewById(R.id.lv_recycler_view)
        swipeRefreshLayout = findViewById(R.id.lv_swipe_refresh_layout)

        val linearLayoutManager = LinearLayoutManager(context)
        listViewAdapter = ListViewAdapter()
        loadMoreScrollListener = LoadMoreScrollListener(linearLayoutManager) {
            if (hasMore) {
                onLoadMoreListener?.invoke()
            }
        }

        recyclerView.let { recyclerView ->
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = listViewAdapter
        }

        swipeRefreshLayout.let { swipeRefreshLayout ->
            swipeRefreshLayout.isEnabled = false
            swipeRefreshLayout.setOnRefreshListener {
                resetLoadMore()
                onRefreshListener?.invoke()
            }
        }
    }

    fun isFirstPage(): Boolean {
        return page == 0
    }

    fun updateLoadMore(hasMore: Boolean) {
        this.hasMore = hasMore
        if (hasMore) {
            page++
            loadingListItem?.let { add(it) }
        }
    }

    fun resetLoadMore() {
        page = 0
        hasMore = false
        loadMoreScrollListener.resetState()
    }

    fun startRefresh() {
        if (isFirstPage() || loadingListItem == null) {
            swipeRefreshLayout.isRefreshing = true
        }
    }

    fun stopRefresh() {
        swipeRefreshLayout.isRefreshing = false
        loadingListItem?.let { remove(it) }
    }

    fun clearIfFirstPage() {
        if (isFirstPage()) {
            clear()
        }
    }

    // region ListItemOperator

    override fun add(listItem: ListItem) {
        listViewAdapter.add(listItem)
    }

    override fun addAll(listItems: Collection<ListItem>) {
        listViewAdapter.addAll(listItems)
    }

    override fun update(listItem: ListItem) {
        listViewAdapter.update(listItem)
    }

    override fun remove(listItem: ListItem) {
        listViewAdapter.remove(listItem)
    }

    override fun clear() {
        listViewAdapter.clear()
    }

    // endregion
}