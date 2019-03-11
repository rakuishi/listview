package com.rakuishi.listview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class ListItem(@LayoutRes var layoutId: Int) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    lateinit var itemView: View

    fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    fun render(viewHolder: RecyclerView.ViewHolder) {
        itemView = viewHolder.itemView
        render()
    }

    fun <T : View> findViewById(@IdRes id: Int): T {
        return itemView.findViewById(id)
    }

    abstract fun render()
}
