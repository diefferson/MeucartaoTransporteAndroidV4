package br.com.disapps.meucartaotransporte.app

import android.content.Context
import br.com.disapps.data.api.RestClient
import br.com.disapps.data.entity.mapper.CardEntityMapper
import br.com.disapps.data.entity.mapper.CardRequestMapper
import br.com.disapps.data.entity.mapper.LineEntityMapper
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.database.RealmDatabase
import br.com.disapps.data.executor.JobExecutor
import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.data.repository.CardsDataRepository
import br.com.disapps.data.repository.LinesDataRepository
import br.com.disapps.data.repository.dataSource.cards.CardsDataSourceFactory
import br.com.disapps.data.repository.dataSource.lines.LinesDataSourceFactory
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.cards.GetCard
import br.com.disapps.domain.repository.CardsRepository
import br.com.disapps.domain.repository.LinesRepository
import br.com.disapps.meucartaotransporte.executor.UIThread
import br.com.disapps.meucartaotransporte.ui.allLines.AllLinesViewModel
import br.com.disapps.meucartaotransporte.ui.cards.CardsViewModel
import br.com.disapps.meucartaotransporte.ui.itineraries.ItinerariesViewModel
import br.com.disapps.meucartaotransporte.ui.lines.LinesViewModel
import br.com.disapps.meucartaotransporte.ui.main.MainViewModel
import br.com.disapps.meucartaotransporte.ui.myCards.MyCardsViewModel
import br.com.disapps.meucartaotransporte.ui.quickFind.QuickFindViewModel
import br.com.disapps.meucartaotransporte.ui.settings.SettingsViewModel
import br.com.disapps.meucartaotransporte.ui.shapes.ShapesViewModel
import org.buffer.android.boilerplate.domain.executor.PostExecutionThread
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
            mappersModule,
            dataSourceFactoryModule
    )

    private val applicationModule: Module = applicationContext {
        bean("applicationContext") { App.instance!! as Context }
        bean { RealmDatabase(get("applicationContext")) as Database }
        bean { Preferences(get("applicationContext"))}
        bean { RestClient().api }
        bean { UIThread() as PostExecutionThread }
        bean { JobExecutor() as  ThreadExecutor }
    }

    private val viewModelModule = applicationContext {
        viewModel { CardsViewModel( getCardUseCase = get()) }
        viewModel { ItinerariesViewModel() }
        viewModel { LinesViewModel() }
        viewModel { MainViewModel() }
        viewModel { QuickFindViewModel() }
        viewModel { SettingsViewModel() }
        viewModel { ShapesViewModel() }
        viewModel { MyCardsViewModel() }
        viewModel { AllLinesViewModel() }
    }

    private val repositoriesModule: Module = applicationContext {
        bean { CardsDataRepository( cardsDataSourceFactory = get(), cardEntityMapper = get(), cardRequestMapper = get()) as CardsRepository }
        bean { LinesDataRepository( linesDataSourceFactory = get(), lineEntityMapper = get()) as LinesRepository }
    }

    private val useCaseModule: Module = applicationContext {
        factory { GetCard( cardRepository = get(), threadExecutor = get(), postExecutionThread = get()) }
    }

    private val mappersModule: Module = applicationContext {
        bean { CardEntityMapper() }
        bean { CardRequestMapper() }
        bean { LineEntityMapper() }
    }

    private val dataSourceFactoryModule : Module = applicationContext {
        bean { CardsDataSourceFactory( database = get(), restApi = get()) }
        bean { LinesDataSourceFactory( database = get(), restApi = get()) }
    }
}