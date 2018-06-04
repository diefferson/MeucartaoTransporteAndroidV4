package br.com.disapps.meucartaotransporte.ui.intro

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import br.com.disapps.meucartaotransporte.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_intro.*
import org.koin.android.architecture.ext.viewModel


class IntroActivity : BaseActivity(){

    override val viewModel by viewModel<IntroViewModel>()

    override val activityLayout = R.layout.activity_intro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUpdate()

        setupClickListeners()

        observeViewModel()
    }

    override fun recreate() {
        super.recreate()
        viewModel.isRecreated = true
        viewModel.isError.value = false
        viewModel.isComplete.value = false
    }

    private fun observeViewModel() {
        viewModel.isComplete.observe(this, Observer {
            if (it != null && it) {
                intro_text.visibility = View.GONE
                btn_continue.visibility = View.VISIBLE
                progress.setPercent(100)
            }
        })

        viewModel.isError.observe(this, Observer {
            if(it!= null && it){
                downloading.visibility = View.GONE
                error.visibility = View.VISIBLE
            }
        })
    }

    private fun setupClickListeners() {
        btn_continue.setOnClickListener {
            downloading.visibility = View.GONE
            alert.visibility = View.VISIBLE
        }

        btn_ok.setOnClickListener {
            MainActivity.launch(this)
            finish()
        }

        btn_try_again.setOnClickListener {
            recreate()
        }
    }

    private fun setupUpdate(){
        progress.startDeterminate()
        progress.setPercent(0)
        viewModel.initData(cacheDir.absolutePath)
        viewModel.progress.observe(this, Observer {
            it?.let {
                progress.setPercent((it/2)-1)
            }
        })
    }
    companion object {
        fun launch(context: Context){
            context.startActivity(Intent(context, IntroActivity::class.java))
        }
    }
}