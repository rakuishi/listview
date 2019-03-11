package com.rakuishi.listview.sample

import android.widget.TextView
import com.rakuishi.listview.ListItem

class TwoLineListItem(private var body: String, private var caption: String) : ListItem(R.layout.list_item_two_line) {

    var onItemClick: (() -> Unit)? = null

    override fun render() {
        findViewById<TextView>(R.id.body_text_view).text = body
        findViewById<TextView>(R.id.caption_text_view).text = caption
        itemView.setOnClickListener { onItemClick?.invoke() }
    }
}