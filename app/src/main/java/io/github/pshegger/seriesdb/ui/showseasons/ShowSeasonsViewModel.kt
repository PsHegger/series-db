package io.github.pshegger.seriesdb.ui.showseasons

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.Relay
import io.github.pshegger.seriesdb.model.Episode
import io.github.pshegger.seriesdb.ui.base.BaseViewModel
import io.github.pshegger.seriesdb.usecase.ShowSeasonsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers

class ShowSeasonsViewModel(private val useCase: ShowSeasonsUseCase) : BaseViewModel() {

    interface Input {
        val showId: Relay<Int>
    }

    interface Output {
        val episodes: Relay<List<Episode>>
    }

    val input = object : Input {
        override val showId = BehaviorRelay.create<Int>()
    }

    val output = object : Output {
        override val episodes = BehaviorRelay.create<List<Episode>>()
    }

    override fun onViewCreated() {
        super.onViewCreated()

        input.showId
            .flatMap { showId ->
                useCase.getEpisodes(showId)
                    .toObservable()
                    .observeOn(AndroidSchedulers.mainThread())
            }
            .subscribe(output.episodes)
            .disposeOnDestroy()
    }
}
