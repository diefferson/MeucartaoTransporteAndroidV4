package br.com.disapps.meucartaotransporte.ui.line

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TabLayout
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.LineVO
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.common.BaseFragmentActivity
import br.com.disapps.meucartaotransporte.ui.custom.SearchAnimationToolbar
import br.com.disapps.meucartaotransporte.ui.line.itineraries.ItinerariesFragment
import br.com.disapps.meucartaotransporte.ui.line.nextSchedules.NextSchedulesFragment
import br.com.disapps.meucartaotransporte.ui.line.shapes.ShapesFragment
import br.com.disapps.meucartaotransporte.util.extensions.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_container.*
import kotlinx.android.synthetic.main.include_toolbar_tabs.*
import org.koin.android.architecture.ext.viewModel

class LineActivity : BaseFragmentActivity(){

    override val viewModel by viewModel<LineViewModel>()

    override val activityLayout = R.layout.activity_line

    override val container: FrameLayout by lazy { vContainer  }

    override val toolbar: SearchAnimationToolbar by lazy { vToolbar }

    override val tabs: TabLayout by lazy { vTabs }

    override val appBar: AppBarLayout by lazy { vAppBar }

    override val initialFragment: BaseFragment by lazy { viewModel.actualFragment }

    override fun getIsTabsVisible() = viewModel.isTabsVisible

    override fun setIsTabsVisible(visible: Boolean) { viewModel.isTabsVisible = visible }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        setDisplayHomeAsUpEnabled()

        viewModel.line = intent.extras.getSerializable(LINE) as LineVO

        setTitle(viewModel.line.name)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_line, menu)

        if (viewModel.line.favorite) {
            menu.getItem(0).setIcon(R.drawable.star)
        } else {
            menu.getItem(0).setIcon(R.drawable.star_outline)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_fav_line ->{ toast("fav")}
        }
        return super.onOptionsItemSelected(item)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_schedules -> {
                viewModel.actualFragment = NextSchedulesFragment.newInstance(viewModel.line.code)
                replaceFragment(viewModel.actualFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_itineraries -> {
                viewModel.actualFragment = ItinerariesFragment.newInstance()
                replaceFragment(viewModel.actualFragment )
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_map -> {
                viewModel.actualFragment = ShapesFragment.newInstance()
                replaceFragment(viewModel.actualFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    companion object {
        private const val LINE = "line"
        fun launch(context: Context,line : LineVO){
            context.startActivity(Intent(context, LineActivity::class.java).apply {
                putExtras(Bundle().apply{ putSerializable(LINE, line)})
            })
        }
    }
}