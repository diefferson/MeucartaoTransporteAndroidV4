package br.com.disapps.meucartaotransporte.app

import android.content.Context
import br.com.disapps.data.api.CustomDownloadManager
import br.com.disapps.data.api.RestClient
import br.com.disapps.data.dataSource.factory.*
import br.com.disapps.data.executor.JobContextExecutor
import br.com.disapps.data.repository.*
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.database.RealmDatabase
import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
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
import br.com.disapps.meucartaotransporte.executor.UIContext
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
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

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
        single(CONTEXT){ App.instance!! as Context }
        single { RealmDatabase(get(CONTEXT)) as Database }
        single { Preferences(get(CONTEXT)) as PreferencesRepository}
        single { Preferences(get(CONTEXT))}
        single { RestClient().api }
        single { UIContext() as PostExecutionContext }
        single { JobContextExecutor() as  ContextExecutor }
        single { get<Context>(CONTEXT).assets  }
        single { LogException() as br.com.disapps.domain.exception.LogException}
        single { CustomDownloadManager(get(CONTEXT)) }
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
        factory { GetPassValue( cardRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { GetCard( cardRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { GetCards( cardRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { SaveCard( cardRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { UpdateCard( cardRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { DeleteCard( cardRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { HasCard( cardRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { GetExtract( cardRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { SaveAllLinesJson( linesRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { SaveAllLinesJsonOnly( linesRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { GetLines( linesRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { UpdateLine( linesRepository = get(),  contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { SaveAllItinerariesJson( itinerariesRepository = get(),  contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { GetItineraryDirections( itinerariesRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { GetItinerary( itinerariesRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { GetAllItineraries( itinerariesRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { SaveAllShapesJson( shapesRepository = get(),  contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { GetShapes( shapesRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { SaveAllSchedulesJson( schedulesRepository = get(),  contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { SaveAllSchedulesJsonOnly( schedulesRepository = get(),  contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { GetLineScheduleDays( schedulesRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { GetLineSchedules( schedulesRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { GetAllPointSchedules( schedulesRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { GetInitialScreen( preferencesRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { GetIsDownloadedCwbItineraries( preferencesRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { GetIsDownloadedMetropolitanItineraries( preferencesRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { GetIsDownloadedCwbShapes( preferencesRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { GetIsDownloadedMetropolitanShapes( preferencesRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { GetDataUsage( preferencesRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { SaveInitialScreen( preferencesRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { GetIsPro( preferencesRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { SetIsPro( preferencesRepository = get(), contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { SaveIsFirstAccess( preferencesRepository = get(),  contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { GetAllBuses( busesRepository = get(),  contextExecutor = get(), postExecutionContext = get(), logException = get()) }
        factory { GetLine( linesRepository = get(),  contextExecutor = get(), postExecutionContext = get(), logException = get()) }
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