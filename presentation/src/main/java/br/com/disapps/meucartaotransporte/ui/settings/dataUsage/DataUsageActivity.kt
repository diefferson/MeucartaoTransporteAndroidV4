package br.com.disapps.meucartaotransporte.ui.settings.dataUsage

import android.content.Context
import android.content.Intent
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import org.koin.android.architecture.ext.viewModel

class DataUsageActivity : BaseActivity(){
    override val viewModel by viewModel<DataUsageViewModel>()
    override val activityLayout = R.layout.activity_data_usage

    companion object {
        fun launch(context: Context){
            context.startActivity(Intent(context, DataUsageActivity::class.java))
        }
    }
}