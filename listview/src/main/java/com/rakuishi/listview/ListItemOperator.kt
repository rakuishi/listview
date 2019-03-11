package com.rakuishi.listview

internal interface ListItemOperator {

    fun add(listItem: ListItem)
    fun addAll(listItems: Collection<ListItem>)
    fun update(listItem: ListItem)
    fun remove(listItem: ListItem)
    fun clear()
}