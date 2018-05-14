package br.com.disapps.meucartaotransporte.app

import android.content.Context
import br.com.disapps.data.api.RestClient
import br.com.disapps.data.dataSource.factory.*
import br.com.disapps.data.events.EventBus
import br.com.disapps.data.executor.JobContextExecutor
import br.com.disapps.data.repository.*
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.database.RealmDatabase
import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.buses.GetAllBuses
import br.com.disapps.domain.interactor.cards.*
import br.com.disapps.domain.interactor.events.GetUpdateLinesEvent
import br.com.disapps.domain.interactor.events.GetUpdateSchedulesEvent
import br.com.disapps.domain.interactor.events.PostEvent
import br.com.disapps.domain.interactor.itineraries.*
import br.com.disapps.domain.interactor.lines.*
import br.com.disapps.domain.interactor.preferences.*
import br.com.disapps.domain.interactor.schedules.*
import br.com.disapps.domain.interactor.shapes.GetAllShapesJson
import br.com.disapps.domain.interactor.shapes.GetShapes
import br.com.disapps.domain.interactor.shapes.SaveAllShapesJson
import br.com.disapps.domain.repository.*
import br.com.disapps.meucartaotransporte.executor.UIContext
import br.com.disapps.meucartaotransporte.ui.cards.balance.BalanceViewModel
import br.com.disapps.meucartaotransporte.ui.cards.extract.ExtractViewModel
import br.com.disapps.meucartaotransporte.ui.cards.myCards.MyCardsViewModel
import br.com.disapps.meucartaotransporte.ui.cards.quickFind.QuickFindViewModel
import br.com.disapps.meucartaotransporte.ui.cards.registerCard.RegisterCardViewModel
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
import br.com.disapps.meucartaotransporte.ui.schedules.SchedulesViewModel
import br.com.disapps.meucartaotransporte.ui.settings.SettingsViewModel
import br.com.disapps.meucartaotransporte.ui.settings.dataUsage.DataUsageViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * Created by dnso on 08/03/2018.
 */
object AppInject {

    fun modules() : List<Module> = listOf(
            applicationModule,
            viewModelModule,
            repositoriesModule,
            useCaseModule,
            dataSourceFactoryModule
    )

    private val applicationModule: Module = applicationContext {
        bean("applicationContext") { App.instance!! as Context }
        bean { RealmDatabase(get("applicationContext")) as Database }
        bean { Preferences(get("applicationContext")) as PreferencesRepository}
        bean { Preferences(get("applicationContext"))}
        bean { RestClient().api }
        bean { EventBus() }
        bean { UIContext() as PostExecutionContext }
        bean { JobContextExecutor() as  ContextExecutor }
        bean { (get("applicationContext") as Context).assets  }
    }

    private val viewModelModule = applicationContext {
        viewModel { BaseViewModel() }
        viewModel { ItinerariesViewModel( getItineraryDirectionsUseCase = get()) }
        viewModel { LinesViewModel( getLinesUseCase = get(), updateLineUseCase =  get()) }
        viewModel { MainViewModel( getInitialScreenUseCase = get(), getIsProUseCase = get(), setIsProUseCase = get()) }
        viewModel { QuickFindViewModel() }
        viewModel { SettingsViewModel( getInitialScreenUseCase = get(), saveInitialScreenUseCase = get()) }
        viewModel { ShapesViewModel( getShapesUseCase = get(), getAllItinerariesUseCase = get(), getAllBusesUseCase = get()) }
        viewModel { MyCardsViewModel( getCardsUseCase = get(), deleteCardUseCase =  get()) }
        viewModel { BalanceViewModel( getCardUseCase = get()) }
        viewModel { RegisterCardViewModel( hasCardUseCase = get(), saveCardUseCase =  get(), getCardUseCase =  get()) }
        viewModel { ExtractViewModel( getExtractUseCase = get() ) }
        viewModel { IntroViewModel( initLinesUseCase = get(),initSchedulesUseCase =   get(),saveIsFirstAccessUseCase =  get()) }
        viewModel { LineViewModel( updateLineUseCase = get()) }
        viewModel { NextSchedulesViewModel( getLineScheduleDaysUseCase = get()) }
        viewModel { NextSchedulesDayViewModel( getLineSchedulesUseCase = get()) }
        viewModel { SchedulesViewModel( getAllPointSchedulesUseCase = get()) }
        viewModel { DataUsageViewModel( getDataUsageUseCase = get(), savePeriodUpdateLinesUseCase =  get(), savePeriodUpdateSchedulesUseCase =  get()) }
        viewModel { ItineraryDirectionViewModel(getItineraryUseCase = get()) }
    }

    private val useCaseModule: Module = applicationContext {
        factory { GetCard( cardRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { GetCards( cardRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { SaveCard( cardRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { DeleteCard( cardRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { HasCard( cardRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { GetExtract( cardRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { GetAllLinesJson( linesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { SaveAllLinesJson( linesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { GetLines( linesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { UpdateLine( linesRepository = get(),  contextExecutor = get(), postExecutionContext = get()) }
        factory { GetAllItinerariesJson( itinerariesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { SaveAllItinerariesJson( itinerariesRepository = get(),  contextExecutor = get(), postExecutionContext = get()) }
        factory { GetItineraryDirections( itinerariesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { GetItinerary( itinerariesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { GetAllItineraries( itinerariesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { GetAllShapesJson( shapesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { SaveAllShapesJson( shapesRepository = get(),  contextExecutor = get(), postExecutionContext = get()) }
        factory { GetShapes( shapesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { GetAllSchedulesJson( schedulesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { SaveAllSchedulesJson( schedulesRepository = get(),  contextExecutor = get(), postExecutionContext = get()) }
        factory { GetLineScheduleDays( schedulesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { GetLineSchedules( schedulesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { GetAllPointSchedules( schedulesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { GetUpdateLinesEvent( eventsRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { GetUpdateSchedulesEvent( eventsRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { PostEvent( eventsRepository = get(),  contextExecutor = get(), postExecutionContext = get()) }
        factory { GetInitialScreen( preferencesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { GetIsFirstAccess( preferencesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { GetDataUsage( preferencesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { SaveInitialScreen( preferencesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { GetIsPro( preferencesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { SetIsPro( preferencesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { SaveIsFirstAccess( preferencesRepository = get(),  contextExecutor = get(), postExecutionContext = get()) }
        factory { SavePeriodUpdateLines( preferencesRepository = get(),  contextExecutor = get(), postExecutionContext = get()) }
        factory { SavePeriodUpdateSchedules( preferencesRepository = get(),  contextExecutor = get(), postExecutionContext = get()) }
        factory { GetAllBuses( busesRepository = get(),  contextExecutor = get(), postExecutionContext = get()) }
        factory { InitLines(linesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
        factory { InitSchedules(schedulesRepository = get(), contextExecutor = get(), postExecutionContext = get()) }
    }

    private val repositoriesModule: Module = applicationContext {
        bean { CardsDataRepository( cardsDataSourceFactory = get()) as CardsRepository }
        bean { LinesDataRepository( linesDataSourceFactory = get()) as LinesRepository }
        bean { ItinerariesDataRepository( itinerariesDataSourceFactory = get()) as ItinerariesRepository }
        bean { ShapesDataRepository( shapesDataSourceFactory = get()) as ShapesRepository }
        bean { SchedulesDataRepository( schedulesDataSourceFactory = get()) as SchedulesRepository }
        bean { EventsDataRepository( eventBus = get()) as EventsRepository }
        bean { BusesDataRepository( busesDataSourceFactory = get()) as BusesRepository }
    }

    private val dataSourceFactoryModule : Module = applicationContext {
        bean { CardsDataSourceFactory(database = get(), restApi = get()) }
        bean { LinesDataSourceFactory(database = get(), restApi = get(), preferences = get(), assetManager = get()) }
        bean { ItinerariesDataSourceFactory(database = get(), restApi = get(), preferences = get()) }
        bean { ShapesDataSourceFactory(database = get(), restApi = get(), preferences = get()) }
        bean { SchedulesDataSourceFactory(database = get(), restApi = get(), preferences = get(), assetManager = get()) }
        bean { BusesDataSourceFactory(database = get(), restApi = get()) }
    }
}