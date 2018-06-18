package br.com.disapps.meucartaotransporte.ui.settings

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import br.com.disapps.domain.model.InitialScreen
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.main.MainViewModel
import br.com.disapps.meucartaotransporte.ui.settings.dataUsage.DataUsageActivity
import br.com.disapps.meucartaotransporte.ui.settings.help.HelpActivity
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.architecture.ext.viewModel
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName


/**
 * Created by dnso on 12/03/2018.
 */
class SettingsFragment : BaseFragment(){

    override val viewModel by viewModel<SettingsViewModel>()
    override val fragmentLayout = R.layout.fragment_settings
    private val mainViewModel: MainViewModel by viewModel()
    override val fragmentTag  = "SettingsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iAppActivityListener.setTitle(getString(R.string.settings))

        help.setOnClickListener { HelpActivity.launch(context!!) }
        initial_screen.setOnClickListener { initialScreen() }
        share.setOnClickListener { shareIt() }
        data_usage.setOnClickListener { DataUsageActivity.launch(context!!) }
        remove_ads.setOnClickListener { removeAds() }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getInitialScreen()
    }

    private fun initialScreen() {

        val items = arrayOf(getString(R.string.cards),getString(R.string.lines))

        val position = if (InitialScreen.CARDS.toString() == viewModel.initialScreen.value) {
            0
        } else {
            1
        }

        AlertDialog.Builder(context!!).apply {
            setTitle(getString(R.string.initial_screen))
            setPositiveButton("Ok", null)
            setSingleChoiceItems(items, position, { _, n ->
                if (n == 0) {
                    viewModel.saveInitialScreen(InitialScreen.CARDS)
                } else {
                    viewModel.saveInitialScreen(InitialScreen.LINES)
                }
            })
        }.show()
    }

    private fun shareIt() {
        startActivity(Intent.createChooser(
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.action_share_subject))
                    putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.action_share_text))
                },
                getString(R.string.action_share)
        ))
    }

    private fun removeAds() {
        mainViewModel.launchPurchaseFlow(activity!!)
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }

}