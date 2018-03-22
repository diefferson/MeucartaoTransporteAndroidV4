package br.com.disapps.meucartaotransporte.ui.registerCard

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_register_card.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_register.setOnClickListener { viewModel.consult() }
        viewModelObservers()
    }

    private fun viewModelObservers() {
        viewModel.code.observe(this, Observer { viewModel.isValidCode.value = true })
        viewModel.cpf.observe(this, Observer { viewModel.isValidCpf.value = true })
    }
}