package com.rakuishi.listview.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.rakuishi.listview.ListView
import com.rakuishi.listview.LoadMoreListItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadMoreContentsActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, LoadMoreContentsActivity::class.java))
        }
    }

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        supportActionBar?.let { actionBar ->
            actionBar.title = "Load more contents"
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        listView = findViewById(R.id.list_view)
        listView.swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))
        listView.loadingListItem = LoadMoreListItem()
        listView.onRefreshListener = { fetch() }
        listView.onLoadMoreListener = { fetch() }
        fetch()
    }

    private fun fetch() {
        listView.startRefresh()

        GlobalScope.launch {
            delay(1000)
            listView.clearIfFirstPage()

            val digit = listView.page * 10
            for (i in (digit + 1)..(digit + 10)) {
                SingleLineListItem("Content $i").let { listItem ->
                    val context = this@LoadMoreContentsActivity
                    listItem.onItemClick = { Toast.makeText(context, "Content $i", Toast.LENGTH_SHORT).show() }
                    listView.add(listItem)
                }
            }

            listView.stopRefresh()
            listView.updateLoadMore(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
