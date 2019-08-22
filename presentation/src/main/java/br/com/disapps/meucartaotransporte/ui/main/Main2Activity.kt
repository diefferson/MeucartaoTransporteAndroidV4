package br.com.disapps.meucartaotransporte.ui.main

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.InAppBillingStatus
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import br.com.disapps.meucartaotransporte.util.alertNeutral
import br.com.disapps.meucartaotransporte.util.iab.IabBroadcastReceiver
import br.com.disapps.meucartaotransporte.util.iab.IabHelper
import br.com.disapps.meucartaotransporte.util.iab.IabResult
import com.google.android.gms.ads.MobileAds
import org.koin.android.viewmodel.ext.android.viewModel


class Main2Activity : BaseActivity(), IabBroadcastReceiver.IabBroadcastListener{

    override val viewModel by viewModel<MainViewModel>()
    override val activityLayout = R.layout.activity_main_2

    internal var mBroadcastReceiver: IabBroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initInAppBilling()
        MobileAds.initialize(applicationContext, getString(R.string.ad_app_id))

        observeViewModel(savedInstanceState)

        viewModel.getInitialScreen()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun observeViewModel(savedInstanceState: Bundle?) {

        viewModel.initialScreen.observe(this, Observer {

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

                mBroadcastReceiver = IabBroadcastReceiver(this@Main2Activity)
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
            val intent = Intent(context, Main2Activity::class.java)
            context.startActivity(intent)
        }
    }
}

