package br.com.disapps.meucartaotransporte.ui.common

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.disapps.meucartaotransporte.BR
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.util.extensions.toast

/**
 * Created by diefferson on 29/11/2017.
 */
abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel
    abstract val fragmentLayout: Int
    var binding: ViewDataBinding? = null

    val iAppActivityListener: IBaseFragmentActivityListener by lazy{
        activity as IBaseFragmentActivityListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            initDataBinding(inflater,container)


    private fun initDataBinding(inflater: LayoutInflater, container: ViewGroup?) : View?{
        binding = DataBindingUtil.inflate(inflater, fragmentLayout, container, false)
        binding?.setVariable(BR.viewModel, viewModel)
        binding?.setLifecycleOwner(this)
        setupLoading()
        setupError()
        return binding?.root
    }

    private fun setupLoading(){
        viewModel.getIsLoadingObservable().observe(this, Observer {
            if(it != null){
                if(it){
                   iAppActivityListener.showLoading()
                }else{
                    iAppActivityListener.dismissLoading()
                }
            }
        })
    }

    private fun setupError(){
        viewModel.getErrorObservable().observe(this, Observer {
            if(it != null){
                activity?.toast(it.message)
            }else{
                activity?.toast(getString(R.string.unknow_error))
            }
        })
    }
}