package br.com.disapps.meucartaotransporte.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.FrameLayout
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.databinding.ActivityMainBinding
import br.com.disapps.meucartaotransporte.ui.cards.CardsFragment
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.common.BaseFragmentActivity
import br.com.disapps.meucartaotransporte.ui.itineraries.ItineariesFragment
import br.com.disapps.meucartaotransporte.ui.lines.LinesFragment
import br.com.disapps.meucartaotransporte.ui.settings.SettingsFragment
import br.com.disapps.meucartaotransporte.ui.shapes.ShapesFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_container.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.architecture.ext.getViewModel

class MainActivity : BaseFragmentActivity<ActivityMainBinding>(){

    override val viewModel : MainViewModel
        get() = getViewModel()

    override val activityLayout: Int
        get() = R.layout.activity_main

    override val container: FrameLayout
        get() = vContainer

    override val toolbar: Toolbar
        get() = vToolbar

    override val initialFragment: BaseFragment<*>
        get() = CardsFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_cards -> {
                replaceFragment(CardsFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_lines -> {
                replaceFragment(LinesFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_settings -> {
                replaceFragment(SettingsFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun checkItemMenu(itemId: Int){
        navigation.menu.findItem(itemId).isChecked = true
    }

    init {
        initialFragmentItemId = R.id.nav_cards
    }
}

