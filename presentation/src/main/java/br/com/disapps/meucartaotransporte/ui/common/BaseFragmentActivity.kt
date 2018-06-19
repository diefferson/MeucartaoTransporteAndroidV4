package br.com.disapps.meucartaotransporte.ui.common

import android.animation.ValueAnimator
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import android.view.ViewGroup
import android.widget.FrameLayout
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.custom.SearchAnimationToolbar
import br.com.disapps.meucartaotransporte.util.clean
import br.com.disapps.meucartaotransporte.util.getLoadingView

/**
 * Created by diefferson on 29/11/2017.
 */
abstract class BaseFragmentActivity: BaseActivity(),
        IBaseFragmentActivityListener,
        SearchAnimationToolbar.OnSearchQueryChangedListener {

    abstract val container: FrameLayout
    abstract val toolbar : SearchAnimationToolbar
    abstract val tabs : TabLayout
    abstract val appBar : AppBarLayout

    private val fragmentTransaction : FragmentTransaction
        get() = supportFragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setupToolbar()
    }

    override fun onBackPressed() {
        if(toolbar.isSearchExpanded){
            toolbar.onBackPressed()
        }else{
            super.onBackPressed()
        }
    }

    private fun setupToolbar(){
        toolbar.setSupportActionBar(this)
        toolbar.setOnSearchQueryChangedListener(this)
    }

    override fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun setDisplayHomeAsUpEnabled() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setupTabs(viewPager: ViewPager, scrollable:Boolean) {
        showTabs()
        tabs.setupWithViewPager(viewPager, false)
        if(scrollable){
            tabs.tabMode = TabLayout.MODE_SCROLLABLE
        }
    }

    override fun replaceFragment(fragment: BaseFragment) {

        if(supportFragmentManager.findFragmentByTag(fragment.fragmentTag) == null){
            val ft = fragmentTransaction
            ft.replace(container.id, fragment, fragment.fragmentTag)
            ft.commit()
        }

        if(!fragment.hasTabs){
            toolbar.onBackPressed()
            appBar.setExpanded(true)
            hideTabs(true)
        }
    }

    override fun replaceAndBackStackFragment(fragment: BaseFragment) {

        if(supportFragmentManager.findFragmentByTag(fragment.fragmentTag) == null){
            val ft = fragmentTransaction
            ft.replace(container.id, fragment, fragment.fragmentTag)
            ft.addToBackStack(fragment.fragmentTag)
            ft.commit()
        }

        if(!fragment.hasTabs){
            toolbar.onBackPressed()
            appBar.setExpanded(true)
            hideTabs(true)
        }
    }

    override fun showLoading() {
        val rootView = this.findViewById<ViewGroup>(android.R.id.content)
        val loadingView = getLoadingView()
        rootView.addView(loadingView)
    }

    override fun dismissLoading() {
        val rootView = this.findViewById<ViewGroup>(android.R.id.content)
        rootView.removeView(rootView.findViewById(R.id.loading_view))
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

    override fun hideTabs(force: Boolean) {
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