package com.rakuishi.listview.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rakuishi.listview.ListView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        findViewById<ListView>(R.id.list_view).let { listView ->
            SingleLineListItem("Load more contents").let { listItem ->
                listItem.onItemClick = { LoadMoreContentsActivity.start(this) }
                listView.add(listItem)
            }
        }
    }
}
