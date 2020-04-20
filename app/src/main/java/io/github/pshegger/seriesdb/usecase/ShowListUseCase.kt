package io.github.pshegger.seriesdb.usecase

import io.github.pshegger.seriesdb.model.Show
import io.github.pshegger.seriesdb.repository.FavoritesRepository
import io.github.pshegger.seriesdb.repository.ShowRepository
import io.reactivex.Single

class ShowListUseCase(
    private val showRepository: ShowRepository,
    private val favoritesRepository: FavoritesRepository
) {

    fun searchShows(q: String) = showRepository.searchShows(q)

    fun getFavoriteShows() = favoritesRepository.getFavorites()
        .map { shows -> shows.sortedBy { it.name } }

    fun toggleFavorite(show: Show): Single<List<Show>> =
        favoritesRepository.getFavorite(show.id)
            .map { true }
            .defaultIfEmpty(false)
            .flatMapCompletable { isFavorite ->
                if (isFavorite) {
                    favoritesRepository.removeFavorite(show)
                } else {
                    favoritesRepository.addFavorite(show)
                }
            }
            .andThen(getFavoriteShows())
}
