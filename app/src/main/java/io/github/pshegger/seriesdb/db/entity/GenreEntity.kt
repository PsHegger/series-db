package io.github.pshegger.seriesdb.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "favorite_genres",
    primaryKeys = ["show_id", "name"],
    foreignKeys = [
        ForeignKey(
            entity = FavoriteEntity::class,
            parentColumns = ["id"],
            childColumns = ["show_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GenreEntity(
    @ColumnInfo(name = "show_id") val showId: Int,
    val name: String
)
