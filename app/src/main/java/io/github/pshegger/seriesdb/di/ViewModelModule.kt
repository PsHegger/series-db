package io.github.pshegger.seriesdb.di

import io.github.pshegger.seriesdb.ui.showdetail.ShowDetailViewModel
import io.github.pshegger.seriesdb.ui.showlist.ShowListViewModel
import io.github.pshegger.seriesdb.ui.showseasons.ShowSeasonsViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { ShowListViewModel(get()) }
    factory { ShowDetailViewModel(get()) }
    factory { ShowSeasonsViewModel(get()) }
}
