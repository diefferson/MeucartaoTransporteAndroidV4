package br.com.disapps.meucartaotransporte.base.ui.cards.registerCard

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import br.com.disapps.domain.exception.KnownError
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.base.ui.common.BaseActivity
import br.com.disapps.meucartaotransporte.base.util.*
import com.appodeal.ads.Appodeal
import kotlinx.android.synthetic.main.activity_register_card.*
import org.koin.android.architecture.ext.viewModel


class RegisterCardActivity : BaseActivity(){

    override val viewModel by viewModel<RegisterCardViewModel>()
    override val activityLayout = R.layout.activity_register_card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupClickListeners()

        viewModelObservers()
    }

    private fun setupClickListeners() {
        btn_register.setOnClickListener {
            hideKeyboard()
            if(validateConnection()){
                viewModel.consult()
                Appodeal.show(this, Appodeal.INTERSTITIAL)
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
        viewModel.code.observe(this, Observer { viewModel.isValidCode.value = true })
        viewModel.cpf.observe(this, Observer { viewModel.isValidCpf.value = true })
        viewModel.name.observe(this, Observer { viewModel.isValidName.value = true })
        viewModel.isFinished.observe(this, Observer { if(it!= null && it){finish()}})
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
                    content.visibility = View.INVISIBLE
                    result_container.addView(loadingView)
                    result_container.visibility = View.VISIBLE
                }else{
                    content.visibility = View.VISIBLE
                    result_container.visibility = View.INVISIBLE
                    Appodeal.hide(this, Appodeal.MREC)
                    Appodeal.destroy(Appodeal.MREC)
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