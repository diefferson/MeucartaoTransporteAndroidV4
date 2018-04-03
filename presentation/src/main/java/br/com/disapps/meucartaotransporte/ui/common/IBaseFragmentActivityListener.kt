package br.com.disapps.meucartaotransporte.ui.common

import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import com.oshi.libsearchtoolbar.SearchAnimationToolbar

/**
 * Created by diefferson on 29/11/2017.
 */
interface IBaseFragmentActivityListener {

    fun setTitle(title: String)

    fun setDisplayHomeAsUpEnabled()

    fun replaceFragment(fragment: BaseFragment)

    fun replaceAndBackStackFragment(fragment: BaseFragment)

    fun showLoading()

    fun dismissLoading()

    fun setupTabs(viewPager: ViewPager)

    fun animateSearchAction()

    fun setPagerAdaper(pagerAdapter: BasePagerAdaper)
}
