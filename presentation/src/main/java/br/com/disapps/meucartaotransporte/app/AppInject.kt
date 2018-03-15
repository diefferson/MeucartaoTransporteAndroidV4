package br.com.disapps.meucartaotransporte.app

import android.content.Context
import br.com.disapps.data.api.RestClient
import br.com.disapps.data.database.Database
import br.com.disapps.data.database.RealmDatabase
import br.com.disapps.data.executor.JobExecutor
import br.com.disapps.data.preferences.Preferences
import br.com.disapps.domain.executor.ThreadExecutor
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
            applicationModule, viewModelModule
    )

    private val applicationModule: Module = applicationContext {
        bean("applicationContext") { App.instance!! as Context }
        bean { RealmDatabase(get("applicationContext")) as Database }
        bean { UIThread() as PostExecutionThread }
        bean { JobExecutor() as  ThreadExecutor }
        bean { RestClient().api }
        bean { Preferences(get("applicationContext"))}
    }

    private val viewModelModule = applicationContext {
        viewModel { CardsViewModel() }
        viewModel { ItinerariesViewModel() }
        viewModel { LinesViewModel() }
        viewModel { MainViewModel() }
        viewModel { QuickFindViewModel() }
        viewModel { SettingsViewModel() }
        viewModel { ShapesViewModel() }
        viewModel { MyCardsViewModel() }
        viewModel { AllLinesViewModel() }
    }

}