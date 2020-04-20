package io.github.pshegger.seriesdb.usecase

import io.github.pshegger.seriesdb.repository.ShowRepository

class ShowSeasonsUseCase(private val showRepository: ShowRepository) {

    fun getEpisodes(showId: Int) = showRepository.getEpisodes(showId)
}
