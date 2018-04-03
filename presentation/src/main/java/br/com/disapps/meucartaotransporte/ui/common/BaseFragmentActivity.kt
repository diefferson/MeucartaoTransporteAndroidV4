package br.com.disapps.meucartaotransporte.ui.common

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewPropertyAnimator
import android.widget.FrameLayout
import br.com.disapps.meucartaotransporte.BR
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.custom.CustomProgressDialog
import br.com.disapps.meucartaotransporte.util.custom.ObservableScrollViewCallbacks
import br.com.disapps.meucartaotransporte.util.custom.ScrollState
import br.com.disapps.meucartaotransporte.util.custom.ScrollUtils
import br.com.disapps.meucartaotransporte.util.custom.Scrollable
import br.com.disapps.meucartaotransporte.util.extensions.toast
import com.oshi.libsearchtoolbar.SearchAnimationToolbar
import kotlinx.android.synthetic.main.fragment_lines.view.*


/**
 * Created by diefferson on 29/11/2017.
 */
abstract class BaseFragmentActivity: AppCompatActivity(),
        IBaseFragmentActivityListener,
        SearchAnimationToolbar.OnSearchQueryChangedListener,
        ObservableScrollViewCallbacks {

    abstract val viewModel: BaseViewModel
    var binding: ViewDataBinding? = null
    abstract val activityLayout: Int
    abstract val container: FrameLayout
    abstract val toolbar : SearchAnimationToolbar
    abstract val tabs : TabLayout
    abstract val appBar : AppBarLayout
    abstract val initialFragment : BaseFragment
    private val loading by lazy { CustomProgressDialog(this) }

    private var mBaseTranslationY: Int = 0
    private var mPagerAdapter:BasePagerAdaper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        toolbar.setSupportActionBar(this)
        toolbar.setOnSearchQueryChangedListener(this)
        replaceFragment(initialFragment)
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
        }

        if(!fragment.hasTabs){
            appBar.setExpanded(true, false)
            tabs.visibility = View.GONE
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
            tabs.visibility = View.GONE
        }
    }

    private val fragmentTransaction: FragmentTransaction
        get() = supportFragmentManager.beginTransaction()


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

    override fun animateSearchAction() {
        toolbar.onSearchIconClick()
    }

    override fun onSearchCollapsed() {
        //searchText.setText(R.string.collapsed)
    }

    override fun onSearchQueryChanged(query: String) {
       // searchText.setText("Searching: $query")
    }

    override fun onSearchExpanded() {
        //searchText.setText(R.string.expanded)
    }

    override fun onSearchSubmitted(query: String) {
        //searchText.setText(getString(R.string.submitted, query))
    }

    override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
        if (dragging) {
            val toolbarHeight = toolbar.height
            val currentHeaderTranslationY = appBar.translationY
            if (firstScroll) {
                if (-toolbarHeight < currentHeaderTranslationY) {
                    mBaseTranslationY = scrollY
                }
            }
            val headerTranslationY = ScrollUtils.getFloat(-(scrollY - mBaseTranslationY).toFloat(), -toolbarHeight.toFloat(), 0f)
            appBar.animate().cancel()
            appBar.translationY = headerTranslationY
        }
    }

    override fun onDownMotionEvent() {

    }

    override fun onUpOrCancelMotionEvent(scrollState: ScrollState) {
        mBaseTranslationY = 0

        val fragment = getCurrentFragment()
        val view = fragment?.view ?: return

        // ObservableXxxViews have same API
        // but currently they don't have any common interfaces.
        adjustToolbar(scrollState, view)
    }

    private fun adjustToolbar(scrollState: ScrollState, view: View) {
        val toolbarHeight = toolbar.height
        val scrollView = view.findViewById<View>(R.id.lines_recycler) as Scrollable
        val scrollY = scrollView.currentScrollY
        if (scrollState === ScrollState.DOWN) {
            showToolbar()
        } else if (scrollState === ScrollState.UP) {
            if (toolbarHeight <= scrollY) {
                hideToolbar()
            } else {
                showToolbar()
            }
        } else {
            // Even if onScrollChanged occurs without scrollY changing, toolbar should be adjusted
            if (toolbarIsShown() || toolbarIsHidden()) {
                // Toolbar is completely moved, so just keep its state
                // and propagate it to other pages
                propagateToolbarState(toolbarIsShown())
            } else {
                // Toolbar is moving but doesn't know which to move:
                // you can change this to hideToolbar()
                showToolbar()
            }
        }
    }

    private fun showToolbar() {
        val headerTranslationY = appBar.translationY
        if (headerTranslationY != 0f) {
            appBar.animate().cancel()
            appBar.animate().translationY(0f).setDuration(200).start()
        }
        propagateToolbarState(true)
    }

    private fun hideToolbar() {
        val headerTranslationY = appBar.translationY
        val toolbarHeight = toolbar.height
        if (headerTranslationY != (-toolbarHeight).toFloat()) {
            appBar.animate().cancel()
            appBar.animate().translationY(-toolbarHeight.toFloat()).setDuration(200).start()
        }
        propagateToolbarState(false)
    }

    private fun toolbarIsShown(): Boolean {
        return appBar.translationY == 0f
    }

    private fun toolbarIsHidden(): Boolean {
        return appBar.translationY == -toolbar.height.toFloat()
    }

    private fun getCurrentFragment(): Fragment? {
        return mPagerAdapter?.getItem(tabs.view_pager.currentItem)
    }

    private fun propagateToolbarState(isShown: Boolean) {
        val toolbarHeight = toolbar.height

        // Set scrollY for the fragments that are not created yet
        mPagerAdapter?.mScrollY = (if (isShown) 0 else toolbarHeight)

        // Set scrollY for the active fragments
        for (i in 0 until mPagerAdapter!!.count) {
            // Skip current item
            if (i == tabs.view_pager.currentItem) {
                continue
            }

            // Skip destroyed or not created item
            val f = mPagerAdapter?.getItem(i) ?: continue

            val view = f.view ?: continue
            propagateToolbarState(isShown, view!!, toolbarHeight)
        }
    }

    private fun propagateToolbarState(isShown: Boolean, view: View, toolbarHeight: Int) {
        val scrollView = view.findViewById<View>(R.id.lines_recycler) as Scrollable
        if (isShown) {
            // Scroll up
            if (0 < scrollView.currentScrollY) {
                scrollView.scrollVerticallyTo(0)
            }
        } else {
            // Scroll down (to hide padding)
            if (scrollView.currentScrollY < toolbarHeight) {
                scrollView.scrollVerticallyTo(toolbarHeight)
            }
        }
    }

    override fun setPagerAdaper(pagerAdapter: BasePagerAdaper) {
        mPagerAdapter = pagerAdapter
    }
}