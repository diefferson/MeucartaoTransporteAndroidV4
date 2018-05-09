package br.com.disapps.meucartaotransporte.ui.settings

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import br.com.disapps.domain.model.InitialScreen
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.settings.dataUsage.DataUsageActivity
import br.com.disapps.meucartaotransporte.ui.settings.help.HelpActivity
import br.com.disapps.meucartaotransporte.util.iab.IabHelper
import br.com.disapps.meucartaotransporte.util.iab.IabResult
import br.com.disapps.meucartaotransporte.util.iab.Purchase
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.inject

/**
 * Created by dnso on 12/03/2018.
 */
class SettingsFragment : BaseFragment(){

    override val viewModel by viewModel<SettingsViewModel>()
    override val fragmentLayout = R.layout.fragment_settings

    val iabHelper : IabHelper by inject()

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

    fun removeAds() {

        val payload = ""


        try {

            iabHelper.launchPurchaseFlow(activity!!, SKU_PRO, RC_REQUEST, mPurchaseFinishedListener, payload)

        } catch (e: IabHelper.IabAsyncInProgressException) {
            complain("Erro ao iniciar o fluxo de compras. Outra operação assíncrona em andamento.")
        }
    }

    //Chamada de retorno quando a compra estiver concluída
    internal var mPurchaseFinishedListener: IabHelper.OnIabPurchaseFinishedListener = object : IabHelper.OnIabPurchaseFinishedListener {
        override fun onIabPurchaseFinished(result: IabResult, purchase: Purchase?) {
            if (iabHelper == null) return

            if (result.isFailure) {
                //complain("Erro ao comprar: " + result);
                return
            }
            if (!verifyDeveloperPayload(purchase)) {
                //complain("Erro ao comprar. Falha na verificação de autenticidade.");
                return
            }

            if (purchase!!.sku == SKU_PRO) {
                alert("Obrigado por assinar a versão PRO")
                iabHelper.flagEndAsync()
            }
        }

    }

    /** Verifies the developer payload of a purchase.  */
    internal fun verifyDeveloperPayload(p: Purchase?): Boolean {
        val payload = p?.developerPayload

        return true
    }

    internal fun complain(message: String) {
        Log.e("TAG", "**** Inapp Error: $message")
        alert(message)
    }

    internal fun alert(message: String) {
        val bld = android.app.AlertDialog.Builder(context)
        bld.setMessage(message)
        bld.setNeutralButton("OK", null)
        bld.create().show()
    }

    companion object {
        internal val SKU_PRO = "remove_ads"

        internal val RC_REQUEST = 10001
        fun newInstance() = SettingsFragment()
    }

}