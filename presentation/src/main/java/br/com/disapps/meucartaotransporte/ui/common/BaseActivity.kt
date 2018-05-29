package br.com.disapps.meucartaotransporte.ui.common

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.ViewGroup
import br.com.disapps.meucartaotransporte.BR
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.util.getLoadingView
import br.com.disapps.meucartaotransporte.util.inflateView
import com.appodeal.ads.Appodeal

/**
 * Created by diefferson on 09/03/2018.
 */
abstract class BaseActivity: AppCompatActivity(){

    abstract val viewModel: BaseViewModel
    abstract val activityLayout: Int
    private var binding: ViewDataBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        setupLoading()
        setupError()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        Appodeal.cache(this, Appodeal.NATIVE)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initDataBinding(){
        binding = DataBindingUtil.setContentView(this,activityLayout )
        binding?.setVariable(BR.viewModel, viewModel)
        binding?.setLifecycleOwner(this)
    }

    open fun setupLoading(){
        viewModel.getIsLoadingObservable().observe(this, Observer {
            if(it != null){
                val rootView = this.findViewById<ViewGroup>(android.R.id.content)
                val loadingView= getLoadingView()
                if(it){
                    rootView.addView(loadingView)
                }else{
                    rootView.removeView(rootView.findViewById(R.id.loading_view))
                }
            }
        })
    }

    open fun setupError(){
        viewModel.getErrorObservable().observe(this, Observer {
            val rootView = this.findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
            Snackbar.make(rootView, getString(R.string.unknow_error), Snackbar.LENGTH_SHORT).show()
        })
    }
}