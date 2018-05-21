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

        btn_continue.setOnClickListener {
            downloading.visibility = View.GONE
            alert.visibility = View.VISIBLE
        }

        btn_ok.setOnClickListener {
            MainActivity.launch(this)
            finish()
        }


        viewModel.isComplete.observe(this, Observer {
            if(it!= null && it){
                btn_continue.visibility = View.VISIBLE
            }
        })
    }

    private fun setupUpdate(){
        progress.startDeterminate()
        viewModel.initData(cacheDir.absolutePath)
        viewModel.progress.observe(this, Observer {
            it?.let {
                progress.setPercent(it/2)
            }
        })
    }
    companion object {
        fun launch(context: Context){
            context.startActivity(Intent(context, IntroActivity::class.java))
        }
    }
}