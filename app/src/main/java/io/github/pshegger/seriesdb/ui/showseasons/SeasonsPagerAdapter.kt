package io.github.pshegger.seriesdb.ui.showseasons

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import io.github.pshegger.seriesdb.R
import io.github.pshegger.seriesdb.model.Episode

class SeasonsPagerAdapter(private val context: Context, fragmentManager: FragmentManager, episodes: List<Episode>) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val seasons = episodes.groupBy { it.season }
        .map { (number, episodes) ->
            Season(number, episodes.sortedBy { it.number })
        }
        .sortedBy { it.number }

    override fun getItem(position: Int): Fragment = EpisodesListFragment.newInstance(seasons[position].episodes)

    override fun getCount(): Int = seasons.size

    override fun getPageTitle(position: Int): CharSequence? = context.getString(R.string.season_name, seasons[position].number)

    private data class Season(val number: Int, val episodes: List<Episode>)
}
