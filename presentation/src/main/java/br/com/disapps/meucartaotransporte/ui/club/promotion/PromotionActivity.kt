package br.com.disapps.meucartaotransporte.ui.club.promotion

import android.content.Context
import android.content.Intent
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import org.koin.android.architecture.ext.viewModel

class PromotionActivity : BaseActivity(){

    override val viewModel  by viewModel<PromotionViewModel>()

    override val activityLayout= R.layout.activity_promotion

    companion object {
        fun launch(context: Context){
            context.startActivity(Intent(context, PromotionActivity::class.java))
        }
    }
}