package io.github.pshegger.seriesdb.repository

import io.github.pshegger.seriesdb.datasource.FavoritesStoreDataSource
import io.github.pshegger.seriesdb.model.Show

class FavoritesRepository(private val store: FavoritesStoreDataSource) {

    fun getFavorites() = store.getFavorites()
    fun getFavorite(id: Int) = store.getFavorite(id)
    fun addFavorite(show: Show) = store.addFavorite(show)
    fun removeFavorite(show: Show) = store.removeFavorite(show)
}
