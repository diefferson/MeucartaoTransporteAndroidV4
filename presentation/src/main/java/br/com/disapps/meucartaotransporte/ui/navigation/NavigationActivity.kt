package br.com.disapps.meucartaotransporte.ui.navigation

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import br.com.disapps.meucartaotransporte.ui.cards.CardsActivity
import br.com.disapps.meucartaotransporte.ui.line.LineActivity
import br.com.disapps.meucartaotransporte.ui.lines.LinesActivity
import org.koin.android.architecture.ext.viewModel

class NavigationActivity : AppCompatActivity() {

    val viewModel:NavigationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ATTENTION: This was auto-generated to handle app links.
        val appLinkIntent = intent
        val appLinkAction = appLinkIntent.action
        val appLinkData = appLinkIntent.data

        when {
            LINES.containsMatchIn(appLinkData.toString()) -> {
                LinesActivity.launch(this)
                finish()
            }
            LINE.containsMatchIn(appLinkData.toString()) -> {
                val codeLine = appLinkData.getQueryParameter(LINE_CODE)
                viewModel.getLine(codeLine)
                viewModel.line.observe(this, Observer {
                    it?.let {
                        LineActivity.launch(this,null, it,null )
                        finish()
                    }
                })
            }
            CARD.containsMatchIn(appLinkData.toString()) -> {
                CardsActivity.launch(this)
                finish()
            }
        }
    }

    companion object {
        const val LINE_CODE = "codigo"
        val CARD = """/cartao""".toRegex()
        val LINES = """/linhas""".toRegex()
        val LINE = """/linha""".toRegex()
    }
}
