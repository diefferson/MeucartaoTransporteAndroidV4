package br.com.disapps.meucartaotransporte.ui.intro

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import br.com.disapps.meucartaotransporte.util.inflateView

class IntroPageAdapter(var context: BaseActivity,var  layouts:IntArray): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = context.inflateView(layouts[position], container)
        container.addView(view)
        return view
    }

    override fun getCount() = layouts.size

    override fun isViewFromObject(view: View, obj: Any)= view === obj

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }
}