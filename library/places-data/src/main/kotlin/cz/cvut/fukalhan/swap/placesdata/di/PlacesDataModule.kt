package cz.cvut.fukalhan.swap.placesdata.di

import cz.cvut.fukalhan.swap.placesdata.data.GooglePlacesRepository
import cz.cvut.fukalhan.swap.placesdata.domain.GetPlacesPredictionsUseCase
import cz.cvut.fukalhan.swap.placesdata.domain.PlacesRepository
import cz.cvut.fukalhan.swap.placesdata.system.RetrofitBuilder
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val placesDataModule = module {
    singleOf(::GooglePlacesRepository) bind PlacesRepository::class
    single { RetrofitBuilder.apiService }
    factoryOf(::GetPlacesPredictionsUseCase)
}
