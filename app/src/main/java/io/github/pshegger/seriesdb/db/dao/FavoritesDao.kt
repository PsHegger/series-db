package io.github.pshegger.seriesdb.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.github.pshegger.seriesdb.db.entity.FavoriteEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorites")
    fun getAll(): Single<List<FavoriteEntity>>

    @Query("SELECT * FROM favorites WHERE id = :id LIMIT 1")
    fun getById(id: Int): Maybe<FavoriteEntity>

    @Insert
    fun insert(favorite: FavoriteEntity): Completable

    @Delete
    fun delete(favorite: FavoriteEntity): Completable
}
