package br.com.disapps.meucartaotransporte.app

import br.com.disapps.meucartaotransporte.ui.main.MainViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * Created by dnso on 08/03/2018.
 */
object AppInject {

    fun modules() : List<Module> = listOf(
            appModule, viewModelModule
    )

    val appModule: Module = applicationContext {
        bean { provideApp() }
    }

    val viewModelModule = applicationContext {
        viewModel { MainViewModel() }
    }

    private fun provideApp(): App = App.instance!!

}