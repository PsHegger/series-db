package io.github.pshegger.seriesdb.repository

import io.github.pshegger.seriesdb.datasource.ShowApiDataSource

class ShowRepository(private val api: ShowApiDataSource) {

    fun searchShows(q: String) = api.searchShows(q)

    fun getShowWithEpisodes(id: Int) = api.getShowWithEpisodes(id)

    fun getEpisodes(showId: Int) = api.getEpisodes(showId)
}
