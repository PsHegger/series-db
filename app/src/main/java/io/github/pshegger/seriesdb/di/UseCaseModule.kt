package io.github.pshegger.seriesdb.di

import io.github.pshegger.seriesdb.usecase.ShowDetailUseCase
import io.github.pshegger.seriesdb.usecase.ShowListUseCase
import io.github.pshegger.seriesdb.usecase.ShowSeasonsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { ShowListUseCase(get(), get()) }
    factory { ShowDetailUseCase(get()) }
    factory { ShowSeasonsUseCase(get()) }
}
