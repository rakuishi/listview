package com.rakuishi.listview.sample

import android.widget.TextView
import com.rakuishi.listview.ListItem

class SingleLineListItem(private var text: String) : ListItem(R.layout.list_item_single_line) {

    var onItemClick: (() -> Unit)? = null

    override fun render() {
        findViewById<TextView>(R.id.text_view).text = text
        itemView.setOnClickListener { onItemClick?.invoke() }
    }
}