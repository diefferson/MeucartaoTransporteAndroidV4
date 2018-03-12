package br.com.disapps.meucartaotransporte.ui.main

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.FrameLayout
import br.com.disapps.meucartaotransporte.R
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

class MainActivity : BaseFragmentActivity(), NavigationView.OnNavigationItemSelectedListener {

    override val viewModel : MainViewModel
        get() = getViewModel()

    override val activityTag: String
        get() = MainActivity::class.java.simpleName

    override val activityName: String
        get() =  getString(R.string.app_name)

    override val activityLayout: Int
        get() = R.layout.activity_main

    override val container: FrameLayout
        get() = vContainer

    override val toolbar: Toolbar
        get() = vToolbar

    override val initialFragment: BaseFragment
        get() = CardsFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDrawer()
    }

    private fun setupDrawer(){
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, vToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)

        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_cards -> replaceFragment(CardsFragment.newInstance())
            R.id.nav_lines -> replaceFragment(LinesFragment.newInstance())
            R.id.nav_shapes -> replaceFragment(ShapesFragment.newInstance())
            R.id.nav_itineraries -> replaceFragment(ItineariesFragment.newInstance())
            R.id.nav_share -> {}
            R.id.nav_ads -> {}
            R.id.nav_settings -> replaceFragment(SettingsFragment.newInstance())
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}

