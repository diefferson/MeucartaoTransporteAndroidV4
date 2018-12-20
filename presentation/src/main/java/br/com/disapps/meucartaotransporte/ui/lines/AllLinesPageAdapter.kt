package br.com.disapps.meucartaotransporte.ui.lines

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.lines.allLines.AllLinesFragment
import br.com.disapps.meucartaotransporte.ui.lines.favoritesLines.FavoritesLinesFragment
/**
 * Created by dnso on 14/03/2018.
 */
class AllLinesPageAdapter(fm: FragmentManager, var context: Context) : FragmentPagerAdapter(fm){

    override fun getCount()= 1

    override fun getItem(position: Int): Fragment {
        return  AllLinesFragment.newInstance()
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.getString(R.string.lines)
    }
}