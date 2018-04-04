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
import android.view.View
import android.widget.FrameLayout
import br.com.disapps.meucartaotransporte.BR
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.custom.CustomProgressDialog
import br.com.disapps.meucartaotransporte.util.extensions.toast
import com.oshi.libsearchtoolbar.SearchAnimationToolbar

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

    override fun showLoading() {
        loading.show()
    }

    override fun dismissLoading() {
        loading.dismiss()
    }

    override fun animateSearchAction() {
        hideToolbar()
     //   toolbar.onSearchIconClick()
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

    private fun showToolbar() {
        val headerTranslationY = appBar.translationY
        if (headerTranslationY != 0f) {
            appBar.animate().cancel()
            appBar.animate().translationY(0f).setDuration(200).start()
        }
    }

    private fun hideToolbar() {
        val headerTranslationY = appBar.translationY
        val toolbarHeight = toolbar.height
        if (headerTranslationY != (-toolbarHeight).toFloat()) {
            appBar.animate().cancel()
            appBar.animate().translationY(-toolbarHeight.toFloat()).setDuration(200).start()
        }
    }

    private fun toolbarIsShown(): Boolean {
        return appBar.translationY == 0f
    }

    private fun toolbarIsHidden(): Boolean {
        return appBar.translationY == -toolbar.height.toFloat()
    }



}