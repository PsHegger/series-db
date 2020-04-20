package io.github.pshegger.seriesdb.ui.showlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.pshegger.seriesdb.R
import io.github.pshegger.seriesdb.databinding.ItemShowBinding
import io.github.pshegger.seriesdb.model.Show
import io.github.pshegger.seriesdb.utils.DataClassDiffCallback
import io.github.pshegger.seriesdb.utils.loadUrl
import io.github.pshegger.seriesdb.utils.localizedName

typealias ShowActionListener = (Show) -> Unit

class ShowListAdapter : RecyclerView.Adapter<ShowListAdapter.ViewHolder>() {

    private val shows = mutableListOf<Show>()
    private val favoriteIds = mutableListOf<Int>()

    private val mergedItems = mutableListOf<ShowFavoriteInfo>()

    private var selectedListener: ShowActionListener? = null
    private var toggleFavoriteListener: ShowActionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemShowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            { selectedListener?.invoke(it) },
            { toggleFavoriteListener?.invoke(it) }
        )

    override fun getItemCount(): Int = mergedItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mergedItems[position])
    }

    private fun updateList() {
        val newMerged = shows.map { show ->
            ShowFavoriteInfo(show, favoriteIds.contains(show.id))
        }
        val callback = DataClassDiffCallback(mergedItems, newMerged) { it.show.id }
        val result = DiffUtil.calculateDiff(callback)
        result.dispatchUpdatesTo(this)
        mergedItems.clear()
        mergedItems.addAll(newMerged)
    }

    fun updateFavoriteIds(newFavorites: List<Int>) {
        favoriteIds.clear()
        favoriteIds.addAll(newFavorites)
        updateList()
    }

    fun updateShows(newShows: List<Show>) {
        shows.clear()
        shows.addAll(newShows)
        updateList()
    }

    fun setShowSelectedListener(listener: ShowActionListener?) {
        selectedListener = listener
    }

    fun setToggleFavoriteListener(listener: ShowActionListener?) {
        toggleFavoriteListener = listener
    }

    class ViewHolder(
        private val binding: ItemShowBinding,
        private val showSelectedListener: ShowActionListener,
        private val toggleFavoriteListener: ShowActionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(mergedInfo: ShowFavoriteInfo) {
            val show = mergedInfo.show
            binding.root.setOnClickListener { showSelectedListener(show) }
            binding.showIsFavorite.setOnClickListener { toggleFavoriteListener(show) }

            binding.showTitle.text = show.name
            binding.showReleaseYear.text = if (show.premiered != null) {
                show.premiered.year.toString()
            } else {
                binding.root.context.getText(R.string.unavailable)
            }
            binding.showStatus.text = show.status.localizedName(binding.root.context, "show_status")
            binding.showIsFavorite.setImageResource(
                if (mergedInfo.isFavorite) R.drawable.ic_favorite else R.drawable.ic_not_favorite
            )

            binding.showImage.loadUrl(show.image?.medium)
            binding.showImage.transitionName = "showImage${show.id}"
        }
    }

    data class ShowFavoriteInfo(val show: Show, val isFavorite: Boolean)
}
