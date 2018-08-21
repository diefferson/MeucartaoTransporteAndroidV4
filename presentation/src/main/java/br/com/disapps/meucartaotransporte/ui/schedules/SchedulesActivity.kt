package br.com.disapps.meucartaotransporte.ui.schedules

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.app.App
import br.com.disapps.meucartaotransporte.model.SchedulesDetail
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import br.com.disapps.meucartaotransporte.util.*
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_schedules.*
import kotlinx.android.synthetic.main.include_toolbar_schedules.*
import org.koin.android.viewmodel.ext.android.viewModel

class SchedulesActivity : BaseActivity (){

    override val viewModel by viewModel<SchedulesViewModel>()
    override val activityLayout = R.layout.activity_schedules
    private lateinit var schedulesDetail : SchedulesDetail

    private val adapter : SchedulesListAdapter by lazy {
        SchedulesListAdapter(ArrayList(), 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        schedulesDetail = intent.getSerializableExtra(SCHEDULES_DETAIL) as SchedulesDetail
        setTheme(getCustomTheme(schedulesDetail.lineColor))
        super.onCreate(savedInstanceState)

        setSupportActionBar(vToolbar)
        day.text = getDayName(this, schedulesDetail.day)
        pointer.text = schedulesDetail.busStopName

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initRecyclerView()
        observeViewModel()

        showAds()
    }

    private fun showAds() {

        showInterstitial(mInterstitialAd)

        adView.loadAdIfIsPro()
        if (App.instance == null || App.instance!!.preferences.getIsProSync()) {
            adView.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSchedules(schedulesDetail.lineCode, schedulesDetail.day, schedulesDetail.busStopCode)
    }

    private fun initRecyclerView() {
        schedules_recycler.apply {
            layoutManager = GridLayoutManager(context, 4)
            adapter = this@SchedulesActivity.adapter.apply {
                day = schedulesDetail.day
            }
        }
    }

    private fun observeViewModel(){
        viewModel.schedules.observe(this, Observer {
            adapter.apply {
                setNewData(it)
                emptyView = getEmptyView(getString(R.string.no_results))
            }
        })
    }

    companion object {
        private const val SCHEDULES_DETAIL = "schedulesDetail"
        fun launch(context: Context, schedulesDetail: SchedulesDetail){
            context.startActivity(Intent(context, SchedulesActivity::class.java).apply {
                putExtra(SCHEDULES_DETAIL, schedulesDetail)
            })
        }
    }
}