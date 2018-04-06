package br.com.disapps.meucartaotransporte.ui.line

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.meucartaotransporte.model.LineVO
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.ui.line.nextSchedules.NextSchedulesFragment

class LineViewModel : BaseViewModel(){
    var actualFragment: BaseFragment = NextSchedulesFragment.newInstance()
    var isTabsVisible = true
    val line = MutableLiveData<LineVO>()
}