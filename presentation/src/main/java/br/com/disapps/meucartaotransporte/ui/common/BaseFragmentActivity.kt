package br.com.disapps.meucartaotransporte.ui.common

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import br.com.disapps.meucartaotransporte.BR
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.custom.CustomProgressDialog
import br.com.disapps.meucartaotransporte.util.extensions.toast
import android.animation.ValueAnimator
import br.com.disapps.meucartaotransporte.ui.custom.SearchAnimationToolbar
import br.com.disapps.meucartaotransporte.util.extensions.clean

/**
 * Created by diefferson on 29/11/2017.
 */
abstract class BaseFragmentActivity: AppCompatActivity(),
        IBaseFragmentActivityListener,
        SearchAnimationToolbar.OnSearchQueryChangedListener {

    abstract val viewModel: BaseViewModel
    abstract val activityLayout: Int
    abstract val container: FrameLayout
    abstract val toolbar : SearchAnimationToolbar
    abstract val tabs : TabLayout
    abstract val appBar : AppBarLayout
    private var binding: ViewDataBinding? = null
    private val loading by lazy { CustomProgressDialog(this) }

    private val fragmentTransaction : FragmentTransaction
        get() = supportFragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        setupToolbar()
        setupLoading()
        setupError()
    }

    override fun onBackPressed() {
        if(toolbar.isSearchExpanded){
            toolbar.onBackPressed()
        }else{
            super.onBackPressed()
        }
    }

    private fun initDataBinding(){
        binding = DataBindingUtil.setContentView(this,activityLayout )
        binding?.setVariable(BR.viewModel, viewModel)
        binding?.setLifecycleOwner(this)
    }

    private fun setupToolbar(){
        toolbar.setSupportActionBar(this)
        toolbar.setOnSearchQueryChangedListener(this)
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

    override fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun setDisplayHomeAsUpEnabled() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setupTabs(viewPager: ViewPager) {
        showTabs()
        tabs.setupWithViewPager(viewPager, false)
    }

    override fun replaceFragment(fragment: BaseFragment) {

        if(supportFragmentManager.findFragmentByTag(fragment.javaClass.simpleName) == null){
            val ft = fragmentTransaction
            ft.replace(container.id, fragment, fragment.javaClass.simpleName)
            ft.commit()
        }

        if(!fragment.hasTabs){
            toolbar.onBackPressed()
            appBar.setExpanded(true)
            hideTabs(true)
        }
    }

    override fun replaceAndBackStackFragment(fragment: BaseFragment) {

        if(supportFragmentManager.findFragmentByTag(fragment.javaClass.simpleName) == null){
            val ft = fragmentTransaction
            ft.replace(container.id, fragment, fragment.javaClass.simpleName)
            ft.addToBackStack(fragment.javaClass.simpleName)
            ft.commit()
        }

        if(!fragment.hasTabs){
            toolbar.onBackPressed()
            appBar.setExpanded(true)
            hideTabs(true)
        }
    }

    override fun showLoading() {
        loading.show()
    }

    override fun dismissLoading() {
        loading.dismiss()
    }

    override fun animateSearchAction() {
        toolbar.onSearchIconClick()
    }

    final override fun onSearchCollapsed() {
        showTabs()
        onSearchAction(false)
    }

    final override fun onSearchExpanded() {
        hideTabs()
        onSearchAction(true)
    }

    final override fun onSearchQueryChanged(query: String) {
        setSearchQuery(query.clean().toLowerCase())
    }

    final override fun onSearchSubmitted(query: String) {
        setSearchQuery(query.clean().toLowerCase())
    }

    open fun setSearchQuery(query: String){}
    open fun onSearchAction(isOpen: Boolean){}
    open fun getIsTabsVisible(): Boolean { return false}
    open fun setIsTabsVisible(visible : Boolean){}

    private fun showTabs() {
        if(!getIsTabsVisible()){
            setIsTabsVisible(true)
            val headerHeight = appBar.height
            val toolbarHeight = toolbar.height
            val scale = this.resources.displayMetrics.density
            val tabsHeight = (48 * scale + 0.5f).toInt()
            ValueAnimator.ofInt(headerHeight, (toolbarHeight+tabsHeight)).apply {
                duration = 200
                addUpdateListener { animation ->
                    appBar.layoutParams.height = animation.animatedValue as Int
                    appBar.requestLayout()
                }

            }.start()
        }
    }

    private fun hideTabs(force: Boolean = false) {
        if(getIsTabsVisible() || force){
            setIsTabsVisible(false)
            val scale = this.resources.displayMetrics.density
            val headerHeight = if(appBar.height > 0) appBar.height else (56 * scale + 0.5f).toInt()
            val toolbarHeight = if(toolbar.height > 0) toolbar.height else (56 * scale + 0.5f).toInt()

            ValueAnimator.ofInt(headerHeight, toolbarHeight).apply {
                duration = 200
                addUpdateListener { animation ->
                    appBar.layoutParams.height = animation.animatedValue as Int
                    appBar.requestLayout()
                }

            }.start()
        }
    }
}