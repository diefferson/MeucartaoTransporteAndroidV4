package br.com.disapps.meucartaotransporte.ui.club

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.club.clubCard.ClubCardFragment
import br.com.disapps.meucartaotransporte.ui.club.promotions.PromotionsFragment

class ClubPageAdapter(fm: FragmentManager, var context: Context) : FragmentPagerAdapter(fm){

    override fun getCount()= 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PromotionsFragment.newInstance()
            1 -> ClubCardFragment.newInstance()
            else -> PromotionsFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> context.getString(R.string.promotions)
            1 -> context.getString(R.string.my_card)
            else -> context.getString(R.string.promotions)
        }
    }
}