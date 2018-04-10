package br.com.disapps.meucartaotransporte.ui.custom

import android.support.annotation.IdRes
import android.view.View
import br.com.disapps.domain.model.Schedule
import com.chad.library.adapter.base.BaseViewHolder

class CustomViewHolder(view: View) : BaseViewHolder(view){

    fun setSchedule(@IdRes viewId: Int, value: Schedule): BaseViewHolder {
        val view = getView<CustomSchedule>(viewId)
        view.setIsAdapt(value.adapt)
        view.setSchedule(value.time)
        return this
    }

}