package br.com.disapps.meucartaotransporte.ui.cards.quickFind

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.ui.cards.balance.BalanceActivity
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.util.extensions.toast
import kotlinx.android.synthetic.main.fragment_quick_find.*
import org.koin.android.architecture.ext.getViewModel
import org.koin.android.architecture.ext.viewModel

/**
 * Created by dnso on 12/03/2018.
 */
class QuickFindFragment: BaseFragment(){

    override val viewModel by viewModel<QuickFindViewModel>()

    override val fragmentLayout = R.layout.fragment_quick_find

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelObservers()

        btn_quick_find.setOnClickListener { viewModel.consult() }
    }

    private fun viewModelObservers() {
        viewModel.code.observe(this, Observer { viewModel.isValidCode.value = true })
        viewModel.cpf.observe(this, Observer { viewModel.isValidCpf.value = true })
        viewModel.isSuccess.observe(this, Observer {
            if(it != null){
                if(it){
                    BalanceActivity.launch(context!!, CardVO(cpf = viewModel.cpf.value.toString(), code = viewModel.code.value.toString()))
                }else{
                    activity?.toast("Erro")
                }
            }
        })
    }

    companion object {
        fun newInstance() = QuickFindFragment()
    }
}