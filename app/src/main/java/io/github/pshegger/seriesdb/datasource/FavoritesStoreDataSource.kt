package io.github.pshegger.seriesdb.datasource

import io.github.pshegger.seriesdb.db.dao.FavoritesDao
import io.github.pshegger.seriesdb.db.dao.GenreDao
import io.github.pshegger.seriesdb.db.entity.FavoriteEntity
import io.github.pshegger.seriesdb.db.entity.GenreEntity
import io.github.pshegger.seriesdb.model.Genre
import io.github.pshegger.seriesdb.model.Show
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class FavoritesStoreDataSource(private val favoritesDao: FavoritesDao, private val genreDao: GenreDao) {

    fun getFavorites(): Single<List<Show>> =
        favoritesDao.getAll()
            .subscribeOn(Schedulers.io())
            .flatMap { favorites ->
                genreDao.getGenresForShows(*favorites.map { it.id }.toIntArray())
                    .map { genres ->
                        favorites.map { favorite ->
                            val showGenres = genres
                                .filter { it.showId == favorite.id }
                                .map { Genre.valueOf(it.name) }
                            favorite.toShow(showGenres)
                        }
                    }
            }

    fun getFavorite(id: Int): Maybe<Show> =
        favoritesDao.getById(id)
            .subscribeOn(Schedulers.io())
            .flatMap { favorite ->
                genreDao.getGenresForShows(id).toMaybe()
                    .map { genres ->
                        favorite.toShow(genres.map { Genre.valueOf(it.name) })
                    }
            }

    fun addFavorite(show: Show): Completable =
        favoritesDao.insert(FavoriteEntity.fromShow(show))
            .subscribeOn(Schedulers.io())
            .andThen(
                genreDao.insert(*show.genres.map { GenreEntity(show.id, it.name) }.toTypedArray())
            )

    fun removeFavorite(show: Show) = favoritesDao.delete(FavoriteEntity.fromShow(show))
        .subscribeOn(Schedulers.io())
}
