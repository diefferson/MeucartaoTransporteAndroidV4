package br.com.disapps.meucartaotransporte.ui.common

import android.support.v4.view.ViewPager

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
}
