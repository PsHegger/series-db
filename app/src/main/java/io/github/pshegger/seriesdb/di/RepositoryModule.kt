package io.github.pshegger.seriesdb.di

import io.github.pshegger.seriesdb.repository.FavoritesRepository
import io.github.pshegger.seriesdb.repository.ShowRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { ShowRepository(get()) }
    factory { FavoritesRepository(get()) }
}
