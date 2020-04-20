package io.github.pshegger.seriesdb.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.pshegger.seriesdb.db.entity.GenreEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface GenreDao {

    @Query("SELECT * FROM favorite_genres WHERE show_id IN (:showIds)")
    fun getGenresForShows(vararg showIds: Int): Single<List<GenreEntity>>

    @Insert
    fun insert(vararg genres: GenreEntity): Completable
}
