package br.com.disapps.meucartaotransporte.app

import android.content.Context
import br.com.disapps.data.api.CustomDownloadManager
import br.com.disapps.data.api.RestClient
import br.com.disapps.data.dataSource.factory.*
import br.com.disapps.data.repository.*
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.database.RealmDatabase
import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.domain.interactor.buses.GetAllBuses
import br.com.disapps.domain.interactor.cards.*
import br.com.disapps.domain.interactor.itineraries.GetAllItineraries
import br.com.disapps.domain.interactor.itineraries.GetItinerary
import br.com.disapps.domain.interactor.itineraries.GetItineraryDirections
import br.com.disapps.domain.interactor.itineraries.SaveAllItinerariesJson
import br.com.disapps.domain.interactor.lines.*
import br.com.disapps.domain.interactor.preferences.*
import br.com.disapps.domain.interactor.schedules.*
import br.com.disapps.domain.interactor.shapes.GetShapes
import br.com.disapps.domain.interactor.shapes.SaveAllShapesJson
import br.com.disapps.domain.repository.*
import br.com.disapps.meucartaotransporte.exception.LogException
import br.com.disapps.meucartaotransporte.ui.cards.balance.BalanceViewModel
import br.com.disapps.meucartaotransporte.ui.cards.extract.ExtractViewModel
import br.com.disapps.meucartaotransporte.ui.cards.myCards.MyCardsViewModel
import br.com.disapps.meucartaotransporte.ui.cards.quickFind.QuickFindViewModel
import br.com.disapps.meucartaotransporte.ui.cards.registerCard.RegisterCardViewModel
import br.com.disapps.meucartaotransporte.ui.club.clubCard.ClubCardViewModel
import br.com.disapps.meucartaotransporte.ui.club.promotion.PromotionViewModel
import br.com.disapps.meucartaotransporte.ui.club.promotions.PromotionsViewModel
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.ui.intro.IntroViewModel
import br.com.disapps.meucartaotransporte.ui.line.LineViewModel
import br.com.disapps.meucartaotransporte.ui.line.itineraries.ItinerariesViewModel
import br.com.disapps.meucartaotransporte.ui.line.itineraries.itineraryDirection.ItineraryDirectionViewModel
import br.com.disapps.meucartaotransporte.ui.line.nextSchedules.NextSchedulesViewModel
import br.com.disapps.meucartaotransporte.ui.line.nextSchedules.nextSchedulesDay.NextSchedulesDayViewModel
import br.com.disapps.meucartaotransporte.ui.line.shapes.ShapesViewModel
import br.com.disapps.meucartaotransporte.ui.lines.LinesViewModel
import br.com.disapps.meucartaotransporte.ui.main.MainViewModel
import br.com.disapps.meucartaotransporte.ui.navigation.NavigationViewModel
import br.com.disapps.meucartaotransporte.ui.schedules.SchedulesViewModel
import br.com.disapps.meucartaotransporte.ui.settings.SettingsViewModel
import br.com.disapps.meucartaotransporte.ui.settings.dataUsage.DataUsageViewModel
import br.com.disapps.meucartaotransporte.widgets.busSchedules.BusSchedulesWidgetViewModel
import br.com.disapps.meucartaotransporte.widgets.cardBalance.CardBalanceWidgetViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by dnso on 08/03/2018.
 */
object AppInject {

    private const val CONTEXT = "applicationContext"

    fun modules() : List<Module> = listOf(
            applicationModule,
            viewModelModule,
            repositoriesModule,
            useCaseModule,
            dataSourceFactoryModule
    )

    private val applicationModule: Module = module {
        single(named(CONTEXT)){ App.instance!! as Context }
        single { RealmDatabase(get(named(CONTEXT))) as Database }
        single { Preferences(get(named(CONTEXT))) as PreferencesRepository}
        single { Preferences(get(named(CONTEXT)))}
        single { RestClient().api }
        single { get<Context>(named(CONTEXT)).assets  }
        single { LogException() as br.com.disapps.domain.exception.LogException}
        single { CustomDownloadManager(get(named(CONTEXT))) }
    }

    private val viewModelModule = module {
        viewModel { BaseViewModel() }
        viewModel { ItinerariesViewModel( getItineraryDirectionsUseCase = get(), getIsDownloadedCwbItinerariesUseCase = get(), getIsDownloadedMetropolitanItinerariesUseCase = get()) }
        viewModel { LinesViewModel( getLinesUseCase = get(), updateLineUseCase =  get()) }
        viewModel { MainViewModel( getInitialScreenUseCase = get(), getIsProUseCase = get(), setIsProUseCase = get()) }
        viewModel { QuickFindViewModel() }
        viewModel { SettingsViewModel( getInitialScreenUseCase = get(), saveInitialScreenUseCase = get()) }
        viewModel { ShapesViewModel( getShapesUseCase = get(), getAllItinerariesUseCase = get(), getAllBusesUseCase = get(), getIsDownloadedCwbShapesUseCase = get(), getIsDownloadedMetropolitanShapesUseCase = get()) }
        viewModel { MyCardsViewModel( getCardsUseCase = get(), deleteCardUseCase =  get()) }
        viewModel { BalanceViewModel( getCardUseCase = get(), updateCard = get(), getPassValue = get()) }
        viewModel { RegisterCardViewModel( hasCardUseCase = get(), saveCardUseCase =  get(), getCardUseCase =  get()) }
        viewModel { ExtractViewModel( getExtractUseCase = get(), updateCard = get() ) }
        viewModel { IntroViewModel( saveAllLinesJsonUseCase = get(),saveAllSchedulesJsonUseCase =   get(),saveIsFirstAccessUseCase =  get()) }
        viewModel { LineViewModel( updateLineUseCase = get()) }
        viewModel { NextSchedulesViewModel( getLineScheduleDaysUseCase = get()) }
        viewModel { NextSchedulesDayViewModel( getLineSchedulesUseCase = get()) }
        viewModel { SchedulesViewModel( getAllPointSchedulesUseCase = get()) }
        viewModel { DataUsageViewModel( getDataUsageUseCase = get()) }
        viewModel { ItineraryDirectionViewModel(getItineraryUseCase = get()) }
        viewModel { CardBalanceWidgetViewModel(getCardsUseCase = get()) }
        viewModel { BusSchedulesWidgetViewModel(getLinesUseCase = get(), getLineSchedulesUseCase = get())}
        viewModel { ClubCardViewModel()}
        viewModel { PromotionsViewModel()}
        viewModel { NavigationViewModel(getLineUseCase = get())}
        viewModel { PromotionViewModel() }
    }

    private val useCaseModule: Module = module {
        factory { GetPassValue( cardRepository = get()) }
        factory { GetCard( cardRepository = get()) }
        factory { GetCards( cardRepository = get()) }
        factory { SaveCard( cardRepository = get()) }
        factory { UpdateCard( cardRepository = get()) }
        factory { DeleteCard( cardRepository = get()) }
        factory { HasCard( cardRepository = get()) }
        factory { GetExtract( cardRepository = get()) }
        factory { SaveAllLinesJson( linesRepository = get()) }
        factory { SaveAllLinesJsonOnly( linesRepository = get()) }
        factory { GetLines( linesRepository = get()) }
        factory { UpdateLine( linesRepository = get()) }
        factory { SaveAllItinerariesJson( itinerariesRepository = get()) }
        factory { GetItineraryDirections( itinerariesRepository = get()) }
        factory { GetItinerary( itinerariesRepository = get()) }
        factory { GetAllItineraries( itinerariesRepository = get()) }
        factory { SaveAllShapesJson( shapesRepository = get()) }
        factory { GetShapes( shapesRepository = get()) }
        factory { SaveAllSchedulesJson( schedulesRepository = get()) }
        factory { SaveAllSchedulesJsonOnly( schedulesRepository = get()) }
        factory { GetLineScheduleDays( schedulesRepository = get()) }
        factory { GetLineSchedules( schedulesRepository = get()) }
        factory { GetAllPointSchedules( schedulesRepository = get()) }
        factory { GetInitialScreen( preferencesRepository = get()) }
        factory { GetIsDownloadedCwbItineraries( preferencesRepository = get()) }
        factory { GetIsDownloadedMetropolitanItineraries( preferencesRepository = get()) }
        factory { GetIsDownloadedCwbShapes( preferencesRepository = get()) }
        factory { GetIsDownloadedMetropolitanShapes( preferencesRepository = get()) }
        factory { GetDataUsage( preferencesRepository = get()) }
        factory { SaveInitialScreen( preferencesRepository = get()) }
        factory { GetIsPro( preferencesRepository = get()) }
        factory { SetIsPro( preferencesRepository = get()) }
        factory { SaveIsFirstAccess( preferencesRepository = get()) }
        factory { GetAllBuses( busesRepository = get()) }
        factory { GetLine( linesRepository = get()) }
    }

    private val repositoriesModule: Module = module {
        single { CardsDataRepository( cardsDataSourceFactory = get()) as CardsRepository }
        single { LinesDataRepository( linesDataSourceFactory = get()) as LinesRepository }
        single { ItinerariesDataRepository( itinerariesDataSourceFactory = get()) as ItinerariesRepository }
        single { ShapesDataRepository( shapesDataSourceFactory = get()) as ShapesRepository }
        single { SchedulesDataRepository( schedulesDataSourceFactory = get()) as SchedulesRepository }
        single { BusesDataRepository( busesDataSourceFactory = get()) as BusesRepository }
    }

    private val dataSourceFactoryModule : Module = module {
        single { CardsDataSourceFactory(database = get(), restApi = get()) }
        single { LinesDataSourceFactory(database = get(), restApi = get(), preferences = get(), assetManager = get()) }
        single { ItinerariesDataSourceFactory(database = get(), restApi = get(), preferences = get()) }
        single { ShapesDataSourceFactory(database = get(), restApi = get(), preferences = get()) }
        single { SchedulesDataSourceFactory(database = get(), restApi = get(), preferences = get(), assetManager = get()) }
        single { BusesDataSourceFactory(database = get(), restApi = get()) }
    }
}