package br.com.disapps.meucartaotransporte.ui.balance

import android.content.Context
import android.content.Intent
import android.os.Bundle
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import org.koin.android.architecture.ext.getViewModel

class BalanceActivity : BaseActivity() {

    override val viewModel: BalanceViewModel
        get() = getViewModel()

    override val activityLayout: Int
        get() = R.layout.activity_balance

    override fun onResume() {
        super.onResume()
        val card = intent.extras.getSerializable(CARD) as CardVO
        viewModel.getCard(card.code, card.cpf)
    }

    companion object {
        private const val CARD = "card"
        fun launch(context: Context,card : CardVO){
            val intent = Intent(context, BalanceActivity::class.java)
            val params = Bundle()
            params.putSerializable(CARD, card)
            intent.putExtras(params)
            context.startActivity(intent)
        }
    }
}