package br.com.disapps.meucartaotransporte.ui.common

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.opengl.Visibility
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
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
    abstract val tabs : TabLayout
    abstract val appBar : AppBarLayout
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
        supportActionBar?.title = title
    }

    override fun setDisplayHomeAsUpEnabled() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setupTabs(viewPager: ViewPager) {
        tabs.visibility = View.VISIBLE
        tabs.setupWithViewPager(viewPager, false)
    }

    override fun replaceFragment(fragment: BaseFragment) {
        if(supportFragmentManager.findFragmentByTag(fragment.javaClass.simpleName) == null){
            val ft = fragmentTransaction
            ft.replace(container.id, fragment, fragment.javaClass.simpleName)
            ft.commit()
            if(!fragment.hasTabs){
                appBar.setExpanded(true, true)
                tabs.visibility = View.GONE
            }
        }
    }

    override fun replaceAndBackStackFragment(fragment: BaseFragment) {
        if(supportFragmentManager.findFragmentByTag(fragment.javaClass.simpleName) == null){
            val ft = fragmentTransaction
            ft.replace(container.id, fragment, fragment.javaClass.simpleName)
            ft.addToBackStack(fragment.javaClass.simpleName)
            ft.commit()
            if(!fragment.hasTabs){
                appBar.setExpanded(true, true)
                tabs.visibility = View.GONE
            }
        }
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