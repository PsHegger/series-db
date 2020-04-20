package io.github.pshegger.seriesdb.utils

import androidx.recyclerview.widget.DiffUtil

class DataClassDiffCallback<T, R>(
    private val oldItems: List<T>,
    private val newItems: List<T>,
    private val idExtractor: (T) -> R
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        idExtractor(oldItems[oldItemPosition]) == idExtractor(newItems[newItemPosition])

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItems[oldItemPosition] == newItems[newItemPosition]
}
