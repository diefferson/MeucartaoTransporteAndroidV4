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
    abstract val initialFragment : BaseFragment
    private var isTabsVisible = true

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
        replaceFragment(initialFragment)
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
            appBar.setExpanded(true, false)
            hideTabs()
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
            appBar.setExpanded(true, false)
            hideTabs()
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

    override fun onSearchCollapsed() {
        showTabs()
        onSearchAction(false)
    }

    override fun onSearchExpanded() {
        hideTabs()
        onSearchAction(true)
    }

    override fun onSearchQueryChanged(query: String) {
        setSearchQuery(query)
    }

    override fun onSearchSubmitted(query: String) {
        setSearchQuery(query)
    }

    abstract fun setSearchQuery(query: String)

    abstract fun onSearchAction(isOpen: Boolean)

    private fun showTabs() {
        if(!isTabsVisible){
            isTabsVisible = true
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

    private fun hideTabs() {
        if(isTabsVisible){
            isTabsVisible = false
            val headerHeight = appBar.height
            val toolbarHeight = toolbar.height
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