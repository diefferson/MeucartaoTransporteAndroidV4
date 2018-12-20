package br.com.disapps.meucartaotransporte.ui.common

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.disapps.meucartaotransporte.R

/**
 * Created by diefferson on 29/11/2017.
 */
abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel
    abstract val fragmentLayout: Int
    abstract val fragmentTag:String

    var hasTabs: Boolean = false

    val iAppActivityListener: IBaseFragmentActivityListener by lazy{
        activity as IBaseFragmentActivityListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(fragmentLayout,container, false)
        setupLoading()
        setupError()
        return view
    }

    open fun setupLoading(){
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

    open fun setupError(){
        viewModel.getErrorObservable().observe(this, Observer {
            val rootView = activity!!.findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
            Snackbar.make(rootView, getString(R.string.unknown_error), Snackbar.LENGTH_SHORT).show()
        })
    }
}