package br.com.disapps.meucartaotransporte.ui.line

import br.com.disapps.meucartaotransporte.ui.cards.CardsFragment
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class LineViewModel : BaseViewModel(){
    var actualFragment: BaseFragment = CardsFragment.newInstance()
    var isTabsVisible = true
}