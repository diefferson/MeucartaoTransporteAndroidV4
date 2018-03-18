package br.com.disapps.meucartaotransporte.ui.common

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.disapps.meucartaotransporte.BR

/**
 * Created by diefferson on 09/03/2018.
 */
abstract class BaseActivity<DTB : ViewDataBinding> : AppCompatActivity(){

    abstract val viewModel: BaseViewModel
    abstract val activityLayout: Int
    var binding: ViewDataBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
    }

    private fun initDataBinding(){
        binding = DataBindingUtil.setContentView<DTB>(this,activityLayout )
        binding?.setVariable(BR.viewModel, viewModel)
       // binding?.setLifecycleOwner(this)
    }
}