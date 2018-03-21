package br.com.disapps.meucartaotransporte.ui.registerCard

import android.content.Context
import android.content.Intent
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import org.koin.android.architecture.ext.getViewModel

class RegisterCardActivity : BaseActivity(){

    override val viewModel: RegisterCardViewModel
        get() = getViewModel()

    override val activityLayout: Int
        get() = R.layout.activity_register_card

    companion object {

        fun launch(context: Context){
            val intent = Intent(context, RegisterCardActivity::class.java)
            context.startActivity(intent)
        }
    }
}