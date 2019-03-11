package com.rakuishi.listview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

internal class ListViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ListItemOperator {

    private var listItems: MutableList<ListItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return listItems.first { listItem -> listItem.layoutId == viewType }.createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        listItems[position].render(holder)
    }

    override fun getItemViewType(position: Int): Int {
        return listItems[position].layoutId
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    // region ListItemOperator

    override fun add(listItem: ListItem) {
        listItems.add(listItem)
        notifyItemInserted(listItems.lastIndex)
    }

    override fun addAll(listItems: Collection<ListItem>) {
        val positionStart = this.listItems.size
        this.listItems.addAll(listItems)
        notifyItemRangeInserted(positionStart, listItems.size)
    }

    override fun update(listItem: ListItem) {
        val index = listItems.indexOf(listItem)
        if (index != -1) {
            listItems[index] = listItem
            notifyItemChanged(index)
        }
    }

    override fun remove(listItem: ListItem) {
        val index = listItems.indexOf(listItem)
        if (index != -1) {
            listItems.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    override fun clear() {
        val size = listItems.size
        listItems.removeAll { true }
        notifyItemRangeRemoved(0, size)
    }

    // endregion
}