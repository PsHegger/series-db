package io.github.pshegger.seriesdb.ui.showdetail

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.Relay
import io.github.pshegger.seriesdb.model.Episode
import io.github.pshegger.seriesdb.model.Show
import io.github.pshegger.seriesdb.ui.base.BaseViewModel
import io.github.pshegger.seriesdb.usecase.ShowDetailUseCase
import io.reactivex.android.schedulers.AndroidSchedulers

class ShowDetailViewModel(private val useCase: ShowDetailUseCase) : BaseViewModel() {

    interface Input {
        val showId: Relay<Int>
    }

    interface Output {
        val show: Relay<Show>
        val episodes: Relay<List<Episode>>
    }

    val input = object : Input {
        override val showId = BehaviorRelay.create<Int>()
    }

    val output = object : Output {
        override val show = BehaviorRelay.create<Show>()
        override val episodes = BehaviorRelay.create<List<Episode>>()
    }

    override fun onViewCreated() {
        super.onViewCreated()

        input.showId
            .flatMap {  id ->
                useCase.getShowWithEpisodes(id)
                    .toObservable()
                    .observeOn(AndroidSchedulers.mainThread())
            }
            .subscribe {
                output.show.accept(it.show)
                output.episodes.accept(it.embedded.episodes)
            }
            .disposeOnDestroy()
    }
}
