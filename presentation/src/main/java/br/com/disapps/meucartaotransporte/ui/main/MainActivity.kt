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
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.InAppBillingStatus
import br.com.disapps.meucartaotransporte.ui.cards.CardsFragment
import br.com.disapps.meucartaotransporte.ui.club.ClubFragment
import br.com.disapps.meucartaotransporte.ui.common.BaseFragmentActivity
import br.com.disapps.meucartaotransporte.ui.custom.SearchAnimationToolbar
import br.com.disapps.meucartaotransporte.ui.lines.LinesFragment
import br.com.disapps.meucartaotransporte.ui.settings.SettingsFragment
import br.com.disapps.meucartaotransporte.util.alertNeutral
import br.com.disapps.meucartaotransporte.util.iab.IabBroadcastReceiver
import br.com.disapps.meucartaotransporte.util.iab.IabHelper
import br.com.disapps.meucartaotransporte.util.iab.IabResult
import br.com.disapps.meucartaotransporte.util.toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_container.*
import kotlinx.android.synthetic.main.include_toolbar_tabs.*
import org.koin.android.architecture.ext.viewModel

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

        initInAppBilling()
        MobileAds.initialize(applicationContext, getString(R.string.ad_app_id))

        observeViewModel(savedInstanceState)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        viewModel.getInitialScreen()
    }

    private fun observeViewModel(savedInstanceState: Bundle?) {

        viewModel.initialScreen.observe(this, Observer {
            setupActualFragment(savedInstanceState, it ?: 0)
        })

        viewModel.isPro.observe(this, Observer {
            if (it != null && it) {

            } else {

            }
        })

        viewModel.resultInAppBilling.observe(this, Observer {
            it?.let {
                when (it) {
                    InAppBillingStatus.ERROR_INIT -> alertNeutral(getString(R.string.inapp_error_init))
                    InAppBillingStatus.ERROR_QUERY -> alertNeutral(getString(R.string.inapp_error_query))
                    InAppBillingStatus.ERROR_QUERY_INVENTORY -> alertNeutral(getString(R.string.inapp_error_inventory))
                    InAppBillingStatus.SUCCESS -> alertNeutral(getString(R.string.inapp_success))
                }
            }
        })
    }

    override fun onBackPressed() {
        if(toolbar.isSearchExpanded){
            toolbar.onBackPressed()
        }else{
            if(gettingOut){
                super.onBackPressed()
            }else{
                gettingOut = true
                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                } else {
                    Log.d("ADS", "The interstitial wasn't loaded yet.")
                }
                toast(getString(R.string.press_to_exit))
                mInterstitialAd.adListener = object: AdListener() {
                    override fun onAdClosed() {
                        finish()
                    }
                }
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
            2-> R.id.nav_club
            3-> R.id.nav_settings
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
            R.id.nav_club -> {
                fragmentSelected  = 2
                replaceFragment(ClubFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_settings -> {
                fragmentSelected  = 3
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (viewModel.iabHelper == null) return
        if (!viewModel.iabHelper!!.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun initInAppBilling() {
        viewModel.iabHelper = IabHelper(this)
        viewModel.iabHelper?.startSetup(object : IabHelper.OnIabSetupFinishedListener {

            override fun onIabSetupFinished(result: IabResult) {

                if (viewModel.iabHelper == null) return

                mBroadcastReceiver = IabBroadcastReceiver(this@MainActivity)
                val broadcastFilter = IntentFilter(IabBroadcastReceiver.ACTION)
                registerReceiver(mBroadcastReceiver, broadcastFilter)

                viewModel.queryInventoryAsync()
            }
        })
    }

    override fun receivedBroadcast() {
        viewModel.queryInventoryAsync()
    }

    companion object {
        private const val FRAGMENT_SELECTED = "fragmentSelected"
        fun launch(context: Context){
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}

