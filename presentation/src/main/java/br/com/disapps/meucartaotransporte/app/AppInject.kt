package br.com.disapps.meucartaotransporte.app

import android.content.Context
import br.com.disapps.data.api.RestClient
import br.com.disapps.data.dataSource.factory.*
import br.com.disapps.data.events.RxBus
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.database.RealmDatabase
import br.com.disapps.data.executor.JobExecutor
import br.com.disapps.data.repository.*
import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.cards.*
import br.com.disapps.domain.interactor.lines.*
import br.com.disapps.meucartaotransporte.executor.UIThread
import br.com.disapps.meucartaotransporte.ui.cards.balance.BalanceViewModel
import br.com.disapps.meucartaotransporte.ui.cards.extract.ExtractViewModel
import br.com.disapps.meucartaotransporte.ui.line.itineraries.ItinerariesViewModel
import br.com.disapps.meucartaotransporte.ui.lines.LinesViewModel
import br.com.disapps.meucartaotransporte.ui.main.MainViewModel
import br.com.disapps.meucartaotransporte.ui.cards.myCards.MyCardsViewModel
import br.com.disapps.meucartaotransporte.ui.cards.quickFind.QuickFindViewModel
import br.com.disapps.meucartaotransporte.ui.cards.registerCard.RegisterCardViewModel
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.ui.intro.IntroViewModel
import br.com.disapps.meucartaotransporte.ui.settings.SettingsViewModel
import br.com.disapps.meucartaotransporte.ui.line.shapes.ShapesViewModel
import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.interactor.buses.GetAllBuses
import br.com.disapps.domain.interactor.events.*
import br.com.disapps.domain.interactor.itineraries.*
import br.com.disapps.domain.interactor.preferences.*
import br.com.disapps.domain.interactor.schedules.*
import br.com.disapps.domain.interactor.shapes.GetAllShapesJson
import br.com.disapps.domain.interactor.shapes.GetShapes
import br.com.disapps.domain.interactor.shapes.SaveAllShapesJson
import br.com.disapps.domain.repository.*
import br.com.disapps.meucartaotransporte.ui.line.LineViewModel
import br.com.disapps.meucartaotransporte.ui.line.itineraries.itineraryDirection.ItineraryDirectionViewModel
import br.com.disapps.meucartaotransporte.ui.line.nextSchedules.NextSchedulesViewModel
import br.com.disapps.meucartaotransporte.ui.line.nextSchedules.nextSchedulesDay.NextSchedulesDayViewModel
import br.com.disapps.meucartaotransporte.ui.schedules.SchedulesViewModel
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
        bean { RxBus() }
        bean { UIThread() as PostExecutionThread }
        bean { JobExecutor() as  ThreadExecutor }
    }

    private val viewModelModule = applicationContext {
        viewModel { BaseViewModel() }
        viewModel { ItinerariesViewModel( getItineraryDirectionsUseCase = get()) }
        viewModel { LinesViewModel( getLinesUseCase = get(), updateLineUseCase =  get()) }
        viewModel { MainViewModel( getInitialScreenUseCase = get()) }
        viewModel { QuickFindViewModel() }
        viewModel { SettingsViewModel( getInitialScreenUseCase = get(), saveInitialScreenUseCase = get()) }
        viewModel { ShapesViewModel( getShapesUseCase = get(), getAllItinerariesUseCase = get(), getAllBusesUseCase = get()) }
        viewModel { MyCardsViewModel( getCardsUseCase = get(), deleteCardUseCase =  get()) }
        viewModel { BalanceViewModel( getCardUseCase = get()) }
        viewModel { RegisterCardViewModel( hasCardUseCase = get(), saveCardUseCase =  get(), getCardUseCase =  get()) }
        viewModel { ExtractViewModel( getExtractUseCase = get() ) }
        viewModel { IntroViewModel( getUpdateLinesEventUseCase = get(),getUpdateSchedulesEventUseCase =   get(),saveIsFirstAccessUseCase =  get()) }
        viewModel { LineViewModel( updateLineUseCase = get()) }
        viewModel { NextSchedulesViewModel( getLineScheduleDaysUseCase = get()) }
        viewModel { NextSchedulesDayViewModel( getLineSchedulesUseCase = get()) }
        viewModel { SchedulesViewModel( getAllPointSchedulesUseCase = get()) }
        viewModel { DataUsageViewModel( getDataUsageUseCase = get(), savePeriodUpdateLinesUseCase =  get(), savePeriodUpdateSchedulesUseCase =  get()) }
        viewModel { ItineraryDirectionViewModel(getItineraryUseCase = get()) }
    }

    private val useCaseModule: Module = applicationContext {
        factory { GetCard( cardRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetCards( cardRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { SaveCard( cardRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { DeleteCard( cardRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { HasCard( cardRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetExtract( cardRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetAllLinesJson( linesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { SaveAllLinesJson( linesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetLines( linesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { UpdateLine( linesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetAllItinerariesJson( itinerariesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { SaveAllItinerariesJson( itinerariesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetItineraryDirections( itinerariesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetItinerary( itinerariesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetAllItineraries( itinerariesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetAllShapesJson( shapesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { SaveAllShapesJson( shapesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetShapes( shapesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetAllSchedulesJson( schedulesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { SaveAllSchedulesJson( schedulesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetLineScheduleDays( schedulesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetLineSchedules( schedulesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetAllPointSchedules( schedulesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetUpdateDataEvent( eventsRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetUpdateLinesEvent( eventsRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetUpdateSchedulesEvent( eventsRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { PostEvent( eventsRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetInitialScreen( preferencesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetIsFirstAccess( preferencesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetDataUsage( preferencesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { SaveInitialScreen( preferencesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { SaveIsFirstAccess( preferencesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { SavePeriodUpdateLines( preferencesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { SavePeriodUpdateSchedules( preferencesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
        factory { GetAllBuses( busesRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
    }

    private val repositoriesModule: Module = applicationContext {
        bean { CardsDataRepository( cardsDataSourceFactory = get()) as CardsRepository }
        bean { LinesDataRepository( linesDataSourceFactory = get()) as LinesRepository }
        bean { ItinerariesDataRepository( itinerariesDataSourceFactory = get()) as ItinerariesRepository }
        bean { ShapesDataRepository( shapesDataSourceFactory = get()) as ShapesRepository }
        bean { SchedulesDataRepository( schedulesDataSourceFactory = get()) as SchedulesRepository }
        bean { EventsDataRepository( rxBus = get()) as EventsRepository }
        bean { BusesDataRepository( busesDataSourceFactory = get()) as BusesRepository }
    }

    private val dataSourceFactoryModule : Module = applicationContext {
        bean { CardsDataSourceFactory(database = get(), restApi = get()) }
        bean { LinesDataSourceFactory(database = get(), restApi = get(), preferences = get()) }
        bean { ItinerariesDataSourceFactory(database = get(), restApi = get(), preferences = get()) }
        bean { ShapesDataSourceFactory(database = get(), restApi = get(), preferences = get()) }
        bean { SchedulesDataSourceFactory(database = get(), restApi = get(), preferences = get()) }
        bean { BusesDataSourceFactory(database = get(), restApi = get()) }
    }
}