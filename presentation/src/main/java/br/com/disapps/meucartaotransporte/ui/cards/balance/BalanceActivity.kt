package br.com.disapps.meucartaotransporte.ui.cards.balance

import android.content.Context
import android.content.Intent
import android.os.Bundle
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import org.koin.android.architecture.ext.viewModel

class BalanceActivity : BaseActivity() {

    override val viewModel by viewModel<BalanceViewModel>()

    override val activityLayout = R.layout.activity_balance

    override fun onResume() {
        super.onResume()
        val card = intent.getSerializableExtra(CARD) as CardVO
        viewModel.getCard(card.code, card.cpf)
    }

    companion object {
        private const val CARD = "card"
        fun launch(context: Context,card : CardVO){
            context.startActivity(Intent(context, BalanceActivity::class.java).apply {
                putExtra(CARD, card)
            })
        }
    }
}