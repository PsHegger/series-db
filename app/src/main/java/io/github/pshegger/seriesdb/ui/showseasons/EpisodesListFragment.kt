package io.github.pshegger.seriesdb.ui.showseasons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.pshegger.seriesdb.databinding.FragmentEpisodesListBinding
import io.github.pshegger.seriesdb.model.Episode

class EpisodesListFragment : Fragment() {

    companion object {
        private const val EXTRA_EPISODES = "episodes"

        fun newInstance(episodes: List<Episode>) = EpisodesListFragment().apply {
            arguments = Bundle().apply {
                putParcelableArray(EXTRA_EPISODES, episodes.toTypedArray())
            }
        }
    }

    private var _binding: FragmentEpisodesListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentEpisodesListBinding.inflate(inflater, container, false)?.let { layoutBinding ->
            _binding = layoutBinding

            val episodes = arguments?.getParcelableArray(EXTRA_EPISODES)
                ?.toList()
                ?.map { it as Episode }
                ?: emptyList()

            (binding.root as RecyclerView).apply {
                layoutManager = LinearLayoutManager(context)
                adapter = EpisodeListAdapter(episodes)
            }

            binding.root
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
