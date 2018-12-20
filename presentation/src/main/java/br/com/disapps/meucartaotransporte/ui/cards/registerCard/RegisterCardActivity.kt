package br.com.disapps.meucartaotransporte.ui.cards.registerCard

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import br.com.disapps.domain.exception.KnownError
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import br.com.disapps.meucartaotransporte.util.*
import kotlinx.android.synthetic.main.activity_register_card.*
import org.koin.android.viewmodel.ext.android.viewModel


class RegisterCardActivity : BaseActivity(){

    override val viewModel by viewModel<RegisterCardViewModel>()
    override val activityLayout = R.layout.activity_register_card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupClickListeners()
        viewModelObservers()
        validateFields()
    }

    private fun setupClickListeners() {
        btn_register.setOnClickListener {
            hideKeyboard()
            if(validateConnection()){
                viewModel.name = card_name.text.toString()
                viewModel.code = card_code.text.toString()
                viewModel.cpf = card_cpf.text.toString()
                viewModel.consult()
            }else{
                result_container.removeAllViews()
                result_container.addView(getOfflineView())
                content.visibility = View.INVISIBLE
                result_container.visibility = View.VISIBLE
            }
        }

        card_cpf.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                btn_register.performClick()
                return@OnEditorActionListener true
            }
            return@OnEditorActionListener false
        })
    }

    override fun recreate() {
        viewModel.recreate()
        super.recreate()
    }

    private fun viewModelObservers() {
        viewModel.isFinished.observe(this, Observer { if(it!= null && it){finish()}})
    }

    private fun validateFields() {

        card_name.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                viewModel.isValidName.value = true
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

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

        viewModel.isValidName.observe(this, Observer {
            card_name_container.error = if(it != null && it){
                null
            }else{
                getString(R.string.invalid_name)
            }
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

    override fun setupError() {
        viewModel.getErrorObservable().observe(this, Observer {error ->
            error?.let {

                if(it.knownError == KnownError.CARD_EXISTS_EXCEPTION){
                    it.message = getString(R.string.card_already_registered)
                }

                result_container.removeAllViews()
                result_container.addView(getErrorView(it))

                content.visibility = View.INVISIBLE
                result_container.visibility = View.VISIBLE
            }
        })
    }

    override fun setupLoading() {
        viewModel.getIsLoadingObservable().observe(this, Observer {
            if(it != null){
                val loadingView = getLoadingView()
                result_container.removeAllViews()
                if(it){
                    showInterstitial(mInterstitialAd)
                    content.visibility = View.INVISIBLE
                    result_container.addView(loadingView)
                    result_container.visibility = View.VISIBLE
                }else{
                    content.visibility = View.VISIBLE
                    result_container.visibility = View.INVISIBLE
                }
            }
        })
    }

    companion object {
        fun launch(context: Context){
            context.startActivity(Intent(context, RegisterCardActivity::class.java))
        }
    }

}