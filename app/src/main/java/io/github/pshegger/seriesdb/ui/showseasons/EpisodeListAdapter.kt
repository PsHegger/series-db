package io.github.pshegger.seriesdb.ui.showseasons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.pshegger.seriesdb.R
import io.github.pshegger.seriesdb.databinding.ItemEpisodeBinding
import io.github.pshegger.seriesdb.model.Episode
import io.github.pshegger.seriesdb.utils.loadUrl
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class EpisodeListAdapter(private val episodes: List<Episode>) : RecyclerView.Adapter<EpisodeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemEpisodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = episodes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(episodes[position])

    class ViewHolder(private val binding: ItemEpisodeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(episode: Episode) {
            binding.episodeImage.loadUrl(episode.image?.medium)
            binding.episodeName.text = episode.name
            binding.episodeAiredDate.text = if (episode.airstamp != null) {
                episode.airstamp.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
            } else {
                binding.root.context.getString(R.string.unavailable)
            }
        }
    }
}
