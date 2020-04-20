package io.github.pshegger.seriesdb.datasource

import io.github.pshegger.seriesdb.model.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class ShowApiDataSource(retrofit: Retrofit) {

    private val api = retrofit.create(ShowApi::class.java)

    fun searchShows(q: String) = api.searchShows(q)
        .subscribeOn(Schedulers.io())
        .map { it.map(SearchResult::show) }

    fun getShowWithEpisodes(id: Int) = api.getShowWithEpisodes(id)
        .subscribeOn(Schedulers.io())

    fun getEpisodes(showId: Int) = api.getEpisodes(showId)
        .subscribeOn(Schedulers.io())

    interface ShowApi {

        @GET("/search/shows")
        fun searchShows(@Query("q") q: String): Single<List<SearchResult>>

        @GET("/shows/{id}")
        fun getShowWithEpisodes(
            @Path("id") id: Int,
            @Query("embed") embed: String = "episodes"
        ): Single<ShowWithEmbed<EpisodeEmbed>>

        @GET("/shows/{id}/episodes")
        fun getEpisodes(@Path("id") id: Int): Single<List<Episode>>
    }
}
