package io.github.pshegger.seriesdb.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.pshegger.seriesdb.db.dao.FavoritesDao
import io.github.pshegger.seriesdb.db.dao.GenreDao
import io.github.pshegger.seriesdb.db.entity.FavoriteEntity
import io.github.pshegger.seriesdb.db.entity.GenreEntity

@Database(
    entities = [
        FavoriteEntity::class,
        GenreEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
    abstract fun genreDao(): GenreDao
}
