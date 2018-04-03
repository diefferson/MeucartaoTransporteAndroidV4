package br.com.disapps.meucartaotransporte.ui.common

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

abstract class BasePagerAdaper(fm: FragmentManager, var context: Context):  FragmentPagerAdapter(fm){

    var mScrollY: Int = 0
}