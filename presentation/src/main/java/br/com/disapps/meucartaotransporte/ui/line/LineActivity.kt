package br.com.disapps.meucartaotransporte.ui.line

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.LineVO
import br.com.disapps.meucartaotransporte.ui.common.BaseFragmentActivity
import br.com.disapps.meucartaotransporte.ui.custom.SearchAnimationToolbar
import br.com.disapps.meucartaotransporte.ui.line.itineraries.ItinerariesFragment
import br.com.disapps.meucartaotransporte.ui.line.nextSchedules.NextSchedulesFragment
import br.com.disapps.meucartaotransporte.ui.line.shapes.ShapesFragment
import br.com.disapps.meucartaotransporte.util.getAccentColorStateList
import br.com.disapps.meucartaotransporte.util.getCustomTheme
import kotlinx.android.synthetic.main.activity_line.*
import kotlinx.android.synthetic.main.include_container.*
import kotlinx.android.synthetic.main.include_toolbar_animation.*
import org.koin.android.architecture.ext.viewModel

class LineActivity : BaseFragmentActivity(){

    override val viewModel by viewModel<LineViewModel>()
    override val activityLayout = R.layout.activity_line
    override val container: FrameLayout by lazy { vContainer  }
    override val toolbar: SearchAnimationToolbar by lazy { vToolbar }
    override val tabs: TabLayout by lazy { vTabs }
    override val appBar: AppBarLayout by lazy { vAppBar }
    override fun getIsTabsVisible() = viewModel.isTabsVisible
    override fun setIsTabsVisible(visible: Boolean) { viewModel.isTabsVisible = visible }
    private var fragmentSelected = 0

    private val customAnimation : TransitionAnimation by lazy {
        TransitionAnimation(
                context = this,
                window = window,
                collapsingToolbarBackground = collapsingToolbarBackground,
                roundedImage = roundedImage
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.line = intent.extras.getSerializable(LINE) as LineVO
        setTheme(getCustomTheme(viewModel.line.color))
        super.onCreate(savedInstanceState)
        setDisplayHomeAsUpEnabled()
        setTitle(viewModel.line.name)
        setupBottomNavigation()
        setupAnimations(savedInstanceState)
        setupActualFragment(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(FRAGMENT_SELECTED, fragmentSelected)
        outState?.putBoolean(IS_RELOADED, true)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        customAnimation.hideView()
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
                onBackPressed()
                return true
            }
            R.id.action_fav_line ->{
                viewModel.favoriteLine()
                if(viewModel.line.favorite){
                    item.setIcon(R.drawable.star)
                    setResult(RESULT_LINE_FAVOR, Intent().apply {
                        putExtra(LINE_CODE, viewModel.line.code)
                    })
                }else{
                    item.setIcon(R.drawable.star_outline)
                    setResult(RESULT_LINE_DISFAVOR,Intent().apply {
                        putExtra(LINE_CODE, viewModel.line.code)
                    })
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_schedules -> {
                fragmentSelected = 0
                replaceFragment(NextSchedulesFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_itineraries -> {
                fragmentSelected = 1
                replaceFragment(ItinerariesFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_map -> {
                fragmentSelected = 2
                replaceFragment( ShapesFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun setupActualFragment(savedInstanceState: Bundle?){

        savedInstanceState?.let {
            fragmentSelected = it.getInt(FRAGMENT_SELECTED, 0)
        }

        when(fragmentSelected){
            0 ->replaceFragment( NextSchedulesFragment.newInstance() )
            1-> replaceFragment( ItinerariesFragment.newInstance() )
            2-> replaceFragment( ShapesFragment.newInstance())
        }
    }

    private fun setupBottomNavigation(){
        navigation.itemTextColor = getAccentColorStateList( this)
        navigation.itemIconTintList = getAccentColorStateList(this)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun changeBarColors(restore : Boolean = false){
        if (!restore && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }
    }

    private fun setupAnimations(savedInstanceState: Bundle?){
        var isReloaded = false
        savedInstanceState?.let {
            isReloaded = it.getBoolean(IS_RELOADED, false)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            changeBarColors(isReloaded)
            customAnimation.setTransitions()
        }
    }

    companion object {
        private const val LINE = "line"
        private const val FRAGMENT_SELECTED = "fragmentSelected"
        private const val IS_RELOADED = "isReloaded"
        const val REQUEST_ID = 93
        const val RESULT_LINE_FAVOR = 1
        const val RESULT_LINE_DISFAVOR = 0
        const val LINE_CODE = "lineCode"

        fun launch(context:Context, startFragment: Fragment?, line : LineVO, viewAnimation : View){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Intent(context, LineActivity::class.java).apply {
                    putExtras(Bundle().apply{ putSerializable(LINE, line)})
                    val transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(context as BaseFragmentActivity, viewAnimation, context.getString(R.string.roundedColorView_transitionName))
                    if(startFragment!= null){
                        startFragment.startActivityForResult(this, REQUEST_ID, transitionActivityOptions.toBundle())
                    }else{
                        context.startActivity(this, transitionActivityOptions.toBundle())
                    }
                }
            } else{
                if(startFragment != null){
                    startFragment.startActivityForResult(Intent(context, LineActivity::class.java).apply {
                        putExtras(Bundle().apply{ putSerializable(LINE, line)})
                    }, REQUEST_ID)
                }else{
                    context.startActivity(Intent(context, LineActivity::class.java).apply {
                        putExtras(Bundle().apply{ putSerializable(LINE, line)})
                    })
                }
            }
        }
    }
}