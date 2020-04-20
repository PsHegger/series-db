package io.github.pshegger.seriesdb.ui.showdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import io.github.pshegger.seriesdb.R
import io.github.pshegger.seriesdb.databinding.FragmentShowDetailBinding
import io.github.pshegger.seriesdb.ui.base.BaseFragment
import io.github.pshegger.seriesdb.utils.loadUrl
import io.github.pshegger.seriesdb.utils.localizedName
import io.github.pshegger.seriesdb.utils.navigateTo
import io.github.pshegger.seriesdb.utils.stripTags
import org.koin.android.ext.android.inject

class ShowDetailFragment : BaseFragment() {

    override val viewModel by inject<ShowDetailViewModel>()

    private var _binding: FragmentShowDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ShowDetailFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentShowDetailBinding.inflate(inflater, container, false).let { layoutBinding ->
            _binding = layoutBinding

            binding.showSeasonsContainer.setOnClickListener {
                navigateTo(
                    ShowDetailFragmentDirections.actionToShowSeasonsFragment(args.showId)
                )
            }

            binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.output.show
            .subscribe { show ->
                binding.showName.text = show.name
                binding.showLanguage.text = show.language ?: getString(R.string.unavailable)
                binding.showRuntime.text = if (show.runtime != null) {
                    getString(R.string.runtime, show.runtime)
                } else {
                    getString(R.string.unavailable)
                }
                binding.showDescription.text = show.summary?.stripTags() ?: getString(R.string.unavailable)
                binding.showImage.loadUrl(show.image?.original)
                binding.showGenres.text =
                    show.genres.joinToString(separator = ", ") { it.localizedName(binding.root.context, "genre") }
                binding.showStatus.text = show.status.localizedName(binding.root.context, "show_status")
                binding.showReleaseYear.text = if (show.premiered != null) {
                    show.premiered.year.toString()
                } else {
                    getString(R.string.unavailable)
                }
            }
            .disposeOnDestroy()

        viewModel.output.episodes
            .subscribe { episodes ->
                binding.showHeaderImage.loadUrl(episodes.firstOrNull { it.image != null }?.image?.original)
                binding.showSeasonsCount.text = episodes.groupBy { it.season }.size.toString()
            }
            .disposeOnDestroy()

        viewModel.input.showId.accept(args.showId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
