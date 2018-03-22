package br.com.disapps.meucartaotransporte.ui.common

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.FrameLayout
import br.com.disapps.meucartaotransporte.BR
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.custom.CustomProgressDialog
import br.com.disapps.meucartaotransporte.util.extensions.toast

/**
 * Created by diefferson on 29/11/2017.
 */
abstract class BaseFragmentActivity: AppCompatActivity(), IBaseFragmentActivityListener {

    abstract val viewModel: BaseViewModel
    var binding: ViewDataBinding? = null
    abstract val activityLayout: Int
    abstract val container: FrameLayout
    abstract val toolbar : Toolbar
    abstract val initialFragment : BaseFragment
    var initialFragmentItemId: Int =0
    private val loading by lazy { CustomProgressDialog(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        setSupportActionBar(toolbar)
        replaceFragment(initialFragment)
        checkItemMenu(initialFragmentItemId)
        setupLoading()
        setupError()
    }

    override fun setTitle(title: String) {
        supportActionBar!!.title = title
    }

    override fun setDisplayHomeAsUpEnabled() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun replaceFragment(fragment: Fragment) {
        val ft = fragmentTransaction
        ft.replace(container.id, fragment, fragment.javaClass.simpleName)
        ft.commit()
    }

    override fun replaceAndBackStackFragment(fragment: Fragment) {
        val ft = fragmentTransaction
        ft.replace(container.id, fragment, fragment.javaClass.simpleName)
        ft.addToBackStack(fragment.javaClass.simpleName)
        ft.commit()
    }

    private val fragmentTransaction: FragmentTransaction
        get() = supportFragmentManager.beginTransaction()

    override fun checkItemMenu(itemId: Int) {
        //implements in child
    }

    private fun initDataBinding(){
        binding = DataBindingUtil.setContentView(this,activityLayout )
        binding?.setVariable(BR.viewModel, viewModel)
        binding?.setLifecycleOwner(this)
    }

    private fun setupLoading(){
        viewModel.getIsLoadingObservable().observe(this, Observer {
           if(it != null){
               if(it){
                   loading.show()
               }else{
                   loading.dismiss()
               }
           }
        })
    }

    private fun setupError(){
        viewModel.getErrorObservable().observe(this, Observer {
            if(it != null){
                toast(it.message)
            }else{
                toast(getString(R.string.unknow_error))
            }
        })
    }

    override fun showLoading() {
        loading.show()
    }

    override fun dismissLoading() {
        loading.dismiss()
    }
}