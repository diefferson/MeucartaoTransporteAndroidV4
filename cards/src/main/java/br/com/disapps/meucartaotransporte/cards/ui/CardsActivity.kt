package br.com.disapps.meucartaotransporte.cards.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.widget.FrameLayout
import br.com.disapps.meucartaotransporte.cards.R
import br.com.disapps.meucartaotransporte.cards.ui.quickFind.QuickFindFragment
import br.com.disapps.meucartaotransporte.base.ui.common.BaseFragmentActivity
import br.com.disapps.meucartaotransporte.base.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.base.ui.custom.SearchAnimationToolbar
import kotlinx.android.synthetic.main.include_container.*
import kotlinx.android.synthetic.main.include_toolbar_tabs.*
import org.koin.android.architecture.ext.getViewModel

class CardsActivity : BaseFragmentActivity(){

    override val container: FrameLayout by lazy { vContainer  }
    override val toolbar: SearchAnimationToolbar by lazy { vToolbar }
    override val tabs: TabLayout by lazy { vTabs }
    override val appBar: AppBarLayout by lazy { vAppBar }
    override val viewModel: BaseViewModel
        get() = getViewModel()
    override val activityLayout: Int
        get() = R.layout.activity_cards


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        replaceFragment( QuickFindFragment.newInstance())
    }

    companion object {
        fun launch(context: Context){
            val intent = Intent(context, CardsActivity::class.java)
            context.startActivity(intent)
        }
    }
}
