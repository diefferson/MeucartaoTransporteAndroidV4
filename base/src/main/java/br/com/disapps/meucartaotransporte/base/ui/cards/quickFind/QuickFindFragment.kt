package br.com.disapps.meucartaotransporte.base.ui.cards.quickFind

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.base.model.CardVO
import br.com.disapps.meucartaotransporte.base.ui.cards.balance.BalanceActivity
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
        btn_quick_find.setOnClickListener { viewModel.consult() }
        card_cpf.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                btn_quick_find.performClick()
                return@OnEditorActionListener true
            }
            return@OnEditorActionListener false
        })
    }

    private fun viewModelObservers() {
        viewModel.code.observe(this, Observer { viewModel.isValidCode.value = true })
        viewModel.cpf.observe(this, Observer { viewModel.isValidCpf.value = true })
        viewModel.isSuccess.observe(this, Observer {
            if(it != null && it){
                BalanceActivity.launch(context!!, CardVO(cpf = viewModel.cpf.value.toString(), code = viewModel.code.value.toString()))
                viewModel.isSuccess.value = false
            }
        })
    }

    companion object {
        fun newInstance() = QuickFindFragment()
    }
}