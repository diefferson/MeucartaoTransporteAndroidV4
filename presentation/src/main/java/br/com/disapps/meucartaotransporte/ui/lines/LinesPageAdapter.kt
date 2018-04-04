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
class LinesPageAdapter(fm: FragmentManager, var context: Context, var hasFavorite : Boolean) : FragmentPagerAdapter(fm){

    override fun getCount()= 2

    override fun getItem(position: Int): Fragment {

        if(hasFavorite){
            return when (position) {
                0 -> FavoritesLinesFragment.newInstance()
                1 -> AllLinesFragment.newInstance()
                else -> FavoritesLinesFragment.newInstance()
            }
        }

        return when (position) {
            0 -> AllLinesFragment.newInstance()
            1 -> FavoritesLinesFragment.newInstance()
            else -> AllLinesFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {

        if(hasFavorite){
            return when (position) {
                0 -> context.getString(R.string.favorites)
                1 ->  context.getString(R.string.lines)
                else -> context.getString(R.string.favorites)
            }

        }

        return when (position) {
            0 -> context.getString(R.string.lines)
            1 -> context.getString(R.string.favorites)
            else -> context.getString(R.string.lines)
        }
    }
}