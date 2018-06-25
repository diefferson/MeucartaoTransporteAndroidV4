package br.com.disapps.meucartaotransporte.ui.cards.quickFind

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.ui.cards.balance.BalanceActivity
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
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
        validateFields()
    }

    private fun setupClickListeners() {

        btn_quick_find.setOnClickListener {
            viewModel.code = card_code.text.toString()
            viewModel.cpf = card_cpf.text.toString()
            viewModel.consult()
        }

        card_cpf.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                btn_quick_find.performClick()
                return@OnEditorActionListener true
            }
            return@OnEditorActionListener false
        })
    }

    private fun validateFields() {

        card_code.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                viewModel.isValidCode.value = true
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        card_cpf.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                viewModel.isValidCpf.value = true
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        viewModel.isValidCode.observe(this, Observer {
            card_code_container.error = if(it != null && it){
                null
            }else{
                getString(R.string.invalid_code)
            }
        })

        viewModel.isValidCpf.observe(this, Observer {
            card_cpf_container.error = if(it != null && it){
                null
            }else{
                getString(R.string.invalid_cpf)
            }
        })
    }

    private fun viewModelObservers() {

        viewModel.isSuccess.observe(this, Observer {
            if(it != null && it){
                BalanceActivity.launch(context!!, CardVO(cpf = viewModel.cpf, code = viewModel.code))
                viewModel.isSuccess.value = false
            }
        })

    }

    companion object {
        fun newInstance() = QuickFindFragment()
    }
}