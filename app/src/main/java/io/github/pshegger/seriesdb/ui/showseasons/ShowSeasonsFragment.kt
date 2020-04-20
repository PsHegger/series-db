package io.github.pshegger.seriesdb.ui.showseasons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import io.github.pshegger.seriesdb.databinding.FragmentShowSeasonsBinding
import io.github.pshegger.seriesdb.ui.base.BaseFragment
import org.koin.android.ext.android.inject

class ShowSeasonsFragment : BaseFragment() {

    override val viewModel by inject<ShowSeasonsViewModel>()

    private var _binding: FragmentShowSeasonsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ShowSeasonsFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentShowSeasonsBinding.inflate(inflater, container, false).let { layoutBinding ->
            _binding = layoutBinding

            binding.tabLayout.setupWithViewPager(binding.seasonPager)

            binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.output.episodes
            .subscribe { episodes ->
                binding.seasonPager.adapter = SeasonsPagerAdapter(
                    requireContext(),
                    childFragmentManager,
                    episodes
                )
            }
            .disposeOnDestroy()

        viewModel.input.showId.accept(args.showId)
    }
}
