package io.github.pshegger.seriesdb.ui.showlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.widget.textChanges
import io.github.pshegger.seriesdb.databinding.FragmentShowListBinding
import io.github.pshegger.seriesdb.databinding.ItemShowBinding
import io.github.pshegger.seriesdb.model.Show
import io.github.pshegger.seriesdb.ui.base.BaseFragment
import io.github.pshegger.seriesdb.utils.navigateTo
import org.koin.android.ext.android.inject

class ShowListFragment : BaseFragment() {

    override val viewModel by inject<ShowListViewModel>()

    private var _binding: FragmentShowListBinding? = null
    private val binding get() = _binding!!

    private val searchResultAdapter = ShowListAdapter()
    private val favoriteShowsAdapter = ShowListAdapter()

    private val showDetailsAction = { show: Show ->
        navigateTo(ShowListFragmentDirections.actionToShowDetails(show.id))
    }
    private val toggleFavoriteAction = { show: Show ->
        viewModel.input.toggleFavoriteShow.accept(show)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentShowListBinding.inflate(inflater, container, false).let { layoutBinding ->
            _binding = layoutBinding

            binding.searchInput.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    (binding.root as MotionLayout).transitionToEnd()
                } else {
                    (binding.root as MotionLayout).transitionToStart()
                }
            }

            binding.favoriteShows.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = favoriteShowsAdapter
            }

            binding.searchResultList.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = searchResultAdapter
            }

            favoriteShowsAdapter.setShowSelectedListener(showDetailsAction)
            favoriteShowsAdapter.setToggleFavoriteListener(toggleFavoriteAction)

            searchResultAdapter.setShowSelectedListener(showDetailsAction)
            searchResultAdapter.setToggleFavoriteListener(toggleFavoriteAction)

            binding.root
        }

    override fun onResume() {
        super.onResume()

        binding.searchInput.textChanges()
            .map { it.toString() }
            .subscribe(viewModel.input.searchQuery)
            .disposeOnPause()

        viewModel.output.searchLoading
            .subscribe { isLoading -> binding.searchLoading.isInvisible = !isLoading }
            .disposeOnPause()

        viewModel.output.searchResults
            .subscribe { shows ->
                searchResultAdapter.updateShows(shows)
                binding.searchResultList.scrollToPosition(0)
            }
            .disposeOnPause()

        viewModel.output.favoriteShows
            .subscribe { shows ->
                val favoriteIds = shows.map { it.id }
                favoriteShowsAdapter.updateShows(shows)
                favoriteShowsAdapter.updateFavoriteIds(favoriteIds)
                searchResultAdapter.updateFavoriteIds(favoriteIds)
                binding.favoriteShows.scrollToPosition(0)
                binding.emptyFavorites.isVisible = shows.isEmpty()
            }
            .disposeOnDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBackPressed(): Boolean {
        if (binding.searchInput.hasFocus()) {
            binding.searchInput.clearFocus()
            return true
        }
        return super.onBackPressed()
    }
}
