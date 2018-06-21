package br.com.disapps.meucartaotransporte.cards.ui

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.cards.ui.myCards.MyCardsFragment
import br.com.disapps.meucartaotransporte.cards.ui.quickFind.QuickFindFragment

/**
 * Created by dnso on 14/03/2018.
 */
class CardsPageAdapter(fm: FragmentManager, var context: Context) : FragmentPagerAdapter(fm){

    override fun getCount()= 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MyCardsFragment.newInstance()
            1 -> QuickFindFragment.newInstance()
            else -> MyCardsFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> context.getString(R.string.cards)
            1 -> context.getString(R.string.quick_find)
            else -> context.getString(R.string.cards)
        }
    }
}