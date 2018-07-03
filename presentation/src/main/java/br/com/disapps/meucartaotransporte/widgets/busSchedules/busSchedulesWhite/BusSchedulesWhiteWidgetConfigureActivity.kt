package br.com.disapps.meucartaotransporte.widgets.busSchedules.busSchedulesWhite

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.util.getEmptyView
import br.com.disapps.meucartaotransporte.util.getLoadingView
import br.com.disapps.meucartaotransporte.widgets.busSchedules.BusSchedulesPreferences
import br.com.disapps.meucartaotransporte.widgets.busSchedules.BusSchedulesWidgetViewModel
import br.com.disapps.meucartaotransporte.widgets.busSchedules.LinesListAdapter
import br.com.disapps.meucartaotransporte.widgets.busSchedules.StopsListAdapter
import kotlinx.android.synthetic.main.fragment_recycler.*
import org.koin.android.architecture.ext.viewModel

/**
 * The configuration screen for the [BusSchedulesWhiteWidget] AppWidget.
 */
class BusSchedulesWhiteWidgetConfigureActivity : AppCompatActivity() {

    companion object {
        const val PREFS_NAME = "br.com.disapps.meucartaotransporte.widgets.busSchedules.busSchedulesWhite.BusSchedulesWhiteWidget"
        const val PREF_PREFIX_KEY = "appwidgetBusSchedulesWhite_"
    }

    private var mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    private val viewModel by viewModel<BusSchedulesWidgetViewModel>()

    private val linesAdapter: LinesListAdapter by lazy{
        LinesListAdapter(ArrayList()).apply {
            setOnItemClickListener { _, _, position ->

                BusSchedulesPreferences.setLineCode(this@BusSchedulesWhiteWidgetConfigureActivity,
                        PREFS_NAME,
                        PREF_PREFIX_KEY+mAppWidgetId,
                        linesAdapter.getLine(position).code)

                BusSchedulesPreferences.setLineName(this@BusSchedulesWhiteWidgetConfigureActivity,
                        PREFS_NAME,
                        PREF_PREFIX_KEY+mAppWidgetId,
                        linesAdapter.getLine(position).name)

                BusSchedulesPreferences.setLineColor(this@BusSchedulesWhiteWidgetConfigureActivity,
                        PREFS_NAME,
                        PREF_PREFIX_KEY +mAppWidgetId,
                        linesAdapter.getLine(position).color
                )

                title = getString(R.string.select_a_stop)
                viewModel.getLinesSchedules(linesAdapter.getLine(position).code)
            }
        }
    }

    private val stopsAdapter : StopsListAdapter by lazy{
        StopsListAdapter(ArrayList()).apply {
            setOnItemClickListener { _, _, position ->
                val appWidgetManager = AppWidgetManager.getInstance(this@BusSchedulesWhiteWidgetConfigureActivity)

                BusSchedulesPreferences.setStopCode(this@BusSchedulesWhiteWidgetConfigureActivity,
                        PREFS_NAME,
                        PREF_PREFIX_KEY+mAppWidgetId,
                        stopsAdapter.getStop(position).busStopCode)

                BusSchedulesPreferences.setStopName(this@BusSchedulesWhiteWidgetConfigureActivity,
                        PREFS_NAME,
                        PREF_PREFIX_KEY+mAppWidgetId,
                        stopsAdapter.getStop(position).busStopName)

                BusSchedulesWhiteWidget.updateAppWidget(this@BusSchedulesWhiteWidgetConfigureActivity, appWidgetManager, mAppWidgetId)

                // Certifique-se de que devolvemos o appWidgetId original
                val resultValue = Intent()
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId)
                setResult(Activity.RESULT_OK, resultValue)
                finish()
            }
        }
    }

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setResult(Activity.RESULT_CANCELED)
        setContentView(R.layout.fragment_recycler)
        title = getString(R.string.select_a_line)

        // Find the widget id from the intent.
        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        initRecyclerView()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLines()
    }

    private fun initRecyclerView() {
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = linesAdapter
        }
    }

    private fun observeViewModel(){
        viewModel.lines.observe(this, Observer {
            if(it!= null && it.isNotEmpty()){
                linesAdapter.setNewData(it)
                hideErrorView()
            }else{
                showErrorView(getEmptyView(getString(R.string.no_results)))
            }
        })

        viewModel.lineSchedules.observe(this, Observer {
            if(it!= null && it.isNotEmpty()){
                stopsAdapter.setNewData(it)
                hideErrorView()
                recycler.adapter = stopsAdapter
            }else{
                showErrorView(getEmptyView(getString(R.string.no_results)))
            }
        })
    }

    fun setupLoading() {
        viewModel.getIsLoadingObservable().observe(this, Observer {
            if(it!= null && it ){
                showErrorView(getLoadingView())
            }
        })
    }

    private fun showErrorView(view : View?){
        error_view.removeAllViews()
        error_view?.addView(view)
        error_view.visibility = View.VISIBLE
    }

    private fun hideErrorView(){
        error_view.removeAllViews()
        error_view.visibility = View.GONE
    }
}
