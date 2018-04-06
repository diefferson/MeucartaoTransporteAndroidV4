package br.com.disapps.meucartaotransporte.ui.line

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TabLayout
import android.widget.FrameLayout
import br.com.disapps.domain.model.Line
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.model.LineVO
import br.com.disapps.meucartaotransporte.ui.cards.CardsFragment
import br.com.disapps.meucartaotransporte.ui.cards.extract.ExtractActivity
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.common.BaseFragmentActivity
import br.com.disapps.meucartaotransporte.ui.custom.SearchAnimationToolbar
import br.com.disapps.meucartaotransporte.ui.line.itineraries.ItineariesFragment
import br.com.disapps.meucartaotransporte.ui.line.nextSchedules.NextSchedulesFragment
import br.com.disapps.meucartaotransporte.ui.line.shapes.ShapesFragment
import br.com.disapps.meucartaotransporte.ui.lines.LinesFragment
import br.com.disapps.meucartaotransporte.ui.settings.SettingsFragment
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

    override fun setSearchQuery(query: String) {}

    override fun onSearchAction(isOpen: Boolean) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        setDisplayHomeAsUpEnabled()
        viewModel
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_schedules -> {
                viewModel.actualFragment = NextSchedulesFragment.newInstance()
                replaceFragment(viewModel.actualFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_itineraries -> {
                viewModel.actualFragment = ItineariesFragment.newInstance()
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