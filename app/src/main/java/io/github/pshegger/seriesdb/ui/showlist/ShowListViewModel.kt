package io.github.pshegger.seriesdb.ui.showlist

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.github.pshegger.seriesdb.model.Show
import io.github.pshegger.seriesdb.ui.base.BaseViewModel
import io.github.pshegger.seriesdb.usecase.ShowListUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class ShowListViewModel(private val useCase: ShowListUseCase) : BaseViewModel() {

    interface Input {
        val searchQuery: Relay<String>
        val toggleFavoriteShow: Relay<Show>
    }

    interface Output {
        val searchResults: Relay<List<Show>>
        val searchLoading: Relay<Boolean>
        val favoriteShows: Relay<List<Show>>
    }

    val input = object : Input {
        override val searchQuery = BehaviorRelay.create<String>()
        override val toggleFavoriteShow = PublishRelay.create<Show>()
    }

    val output = object : Output {
        override val searchResults = PublishRelay.create<List<Show>>()
        override val searchLoading = BehaviorRelay.createDefault(false)
        override val favoriteShows = PublishRelay.create<List<Show>>()
    }

    override fun onViewCreated() {
        super.onViewCreated()

        useCase.getFavoriteShows()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(output.favoriteShows)
            .disposeOnDestroy()

        input.toggleFavoriteShow
            .flatMap { show ->
                useCase.toggleFavorite(show)
                    .observeOn(AndroidSchedulers.mainThread())
                    .toObservable()
            }
            .subscribe(output.favoriteShows)
            .disposeOnDestroy()

        input.searchQuery
            .skipWhile { it.length < 3 }
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { output.searchLoading.accept(true) }
            .flatMap {  q ->
                useCase.searchShows(q)
                    .toObservable()
                    .observeOn(AndroidSchedulers.mainThread())
            }
            .doAfterNext { output.searchLoading.accept(false) }
            .subscribe(output.searchResults)
            .disposeOnDestroy()
    }
}
