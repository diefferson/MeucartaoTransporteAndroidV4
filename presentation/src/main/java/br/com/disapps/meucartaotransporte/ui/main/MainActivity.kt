package br.com.disapps.meucartaotransporte.ui.main

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TabLayout
import android.util.Log
import android.widget.FrameLayout
import br.com.disapps.meucartaotransporte.BuildConfig
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.cards.CardsFragment
import br.com.disapps.meucartaotransporte.ui.common.BaseFragmentActivity
import br.com.disapps.meucartaotransporte.ui.custom.SearchAnimationToolbar
import br.com.disapps.meucartaotransporte.ui.lines.LinesFragment
import br.com.disapps.meucartaotransporte.ui.settings.SettingsFragment
import br.com.disapps.meucartaotransporte.util.extensions.toast
import com.appodeal.ads.Appodeal
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_container.*
import kotlinx.android.synthetic.main.include_toolbar_tabs.*
import org.koin.android.architecture.ext.viewModel
import br.com.disapps.meucartaotransporte.util.iab.*
import com.appodeal.ads.InterstitialCallbacks

class MainActivity : BaseFragmentActivity(), IabBroadcastReceiver.IabBroadcastListener{

    override val viewModel by viewModel<MainViewModel>()
    override val activityLayout = R.layout.activity_main
    override val container: FrameLayout by lazy { vContainer  }
    override val toolbar: SearchAnimationToolbar by lazy { vToolbar }
    override val tabs: TabLayout by lazy { vTabs }
    override val appBar: AppBarLayout by lazy { vAppBar }
    private var fragmentSelected = 0
    private var gettingOut = false

    internal var mBroadcastReceiver: IabBroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAppodeal()
        initInAppBilling()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        viewModel.getInitialScreen()
        viewModel.initialScreen.observe(this, Observer {
            setupActualFragment(savedInstanceState, it?:0)
        })
    }

    private fun initAppodeal() {
        Appodeal.setAutoCacheNativeIcons(true)
        Appodeal.setAutoCacheNativeMedia(false)
        Appodeal.initialize(this, BuildConfig.APPODEAL_APPKEY, Appodeal.INTERSTITIAL or Appodeal.BANNER or Appodeal.NATIVE or Appodeal.MREC)
    }

    override fun onBackPressed() {
        if(toolbar.isSearchExpanded){
            toolbar.onBackPressed()
        }else{
            if(gettingOut){
                super.onBackPressed()
            }else{
                gettingOut = true
                toast(getString(R.string.press_to_exit))
                Appodeal.show(this, Appodeal.INTERSTITIAL)
                Appodeal.setInterstitialCallbacks(object : InterstitialCallbacks {
                    override fun onInterstitialLoaded(b: Boolean) {}
                    override fun onInterstitialFailedToLoad() {}
                    override fun onInterstitialShown() {}
                    override fun onInterstitialClicked() {}
                    override fun onInterstitialClosed() {
                        finish()
                    }
                })
            }
        }
    }

    private fun setupActualFragment(savedInstanceState: Bundle?, initialFragment: Int){

        savedInstanceState?.let {
            fragmentSelected = it.getInt(FRAGMENT_SELECTED, 0)
        }?: run{
            fragmentSelected = initialFragment
        }

        navigation.selectedItemId = when(fragmentSelected){
            0 -> R.id.nav_cards
            1-> R.id.nav_lines
            2-> R.id.nav_settings
            else -> R.id.nav_cards
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(FRAGMENT_SELECTED, fragmentSelected)
    }

    override fun getIsTabsVisible() = viewModel.isTabsVisible

    override fun setIsTabsVisible(visible: Boolean) {
        viewModel.isTabsVisible = visible
    }

    override fun setSearchQuery(query: String) {
        viewModel.searchText.value = query
    }

    override fun onSearchAction(isOpen: Boolean) {
        viewModel.onSearchAction.value = isOpen
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_cards -> {
                fragmentSelected  = 0
                replaceFragment( CardsFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_lines -> {
                fragmentSelected  = 1
                replaceFragment(LinesFragment.newInstance() )
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_settings -> {
                fragmentSelected  = 2
                replaceFragment(SettingsFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    public override fun onDestroy() {
        super.onDestroy()
        mBroadcastReceiver?.let{ unregisterReceiver(mBroadcastReceiver) }
        viewModel.iabHelper?.disposeWhenFinished()
        viewModel.iabHelper = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (viewModel.iabHelper == null) return
        if (!viewModel.iabHelper!!.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun initInAppBilling() {
        viewModel.iabHelper = IabHelper(this)
        viewModel.iabHelper?.enableDebugLogging(true)
        viewModel.iabHelper?.startSetup(object : IabHelper.OnIabSetupFinishedListener {

            override fun onIabSetupFinished(result: IabResult) {

                if (!result.isSuccess) {
                    Log.d(TAG, "Problema ao configurar a faturação no aplicativo: $result")
                }

                if (viewModel.iabHelper == null) return

                mBroadcastReceiver = IabBroadcastReceiver(this@MainActivity)
                val broadcastFilter = IntentFilter(IabBroadcastReceiver.ACTION)
                registerReceiver(mBroadcastReceiver, broadcastFilter)

                Log.d(TAG, "Configuração bem sucedida. Consultando inventário.")
                viewModel.queryInventoryAsync()
            }
        })
    }

    override fun receivedBroadcast() {
        viewModel.queryInventoryAsync()
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val FRAGMENT_SELECTED = "fragmentSelected"
        fun launch(context: Context){
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}

