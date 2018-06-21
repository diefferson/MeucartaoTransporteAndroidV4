package br.com.disapps.meucartaotransporte.cards.ui.quickFind

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import br.com.disapps.meucartaotransporte.cards.R
import br.com.disapps.meucartaotransporte.base.model.CardVO
import br.com.disapps.meucartaotransporte.cards.ui.balance.BalanceActivity
import br.com.disapps.meucartaotransporte.cards.ui.extract.ExtractActivity
import br.com.disapps.meucartaotransporte.base.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_quick_find.*
import org.koin.android.architecture.ext.viewModel

/**
 * Created by dnso on 12/03/2018.
 */
class QuickFindFragment: BaseFragment(){

    override val viewModel by viewModel<QuickFindViewModel>()
    override val fragmentTag = "QuickFindFragment"
    override val fragmentLayout = R.layout.fragment_quick_find

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelObservers()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        btn_quick_find_balance.setOnClickListener { viewModel.consult() }
        btn_quick_find_extract.setOnClickListener { viewModel.consult(true) }
    }

    private fun viewModelObservers() {
        viewModel.code.observe(this, Observer { viewModel.isValidCode.value = true })
        viewModel.cpf.observe(this, Observer { viewModel.isValidCpf.value = true })
        viewModel.isSuccess.observe(this, Observer {
            if(it != null && it){
                if(viewModel.isExtract){
                    ExtractActivity.launch(context!!, CardVO(cpf = viewModel.cpf.value.toString(), code = viewModel.code.value.toString()))
                }else{
                    BalanceActivity.launch(context!!, CardVO(cpf = viewModel.cpf.value.toString(), code = viewModel.code.value.toString()))
                }

                viewModel.isSuccess.value = false
            }
        })
    }

    companion object {
        fun newInstance() = QuickFindFragment()
    }
}