package io.github.pshegger.seriesdb.usecase

import io.github.pshegger.seriesdb.repository.ShowRepository

class ShowDetailUseCase(private val showRepository: ShowRepository) {

    fun getShowWithEpisodes(id: Int) = showRepository.getShowWithEpisodes(id)
}
