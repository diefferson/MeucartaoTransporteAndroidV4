package br.com.disapps.meucartaotransporte.base.ui.custom.animateCircleLoadingView.component.finish

import android.content.Context
import br.com.disapps.meucartaotransporte.R

/**
 * @author jlmd
 */
class FinishedOkView(context: Context, parentWidth: Int, mainColor: Int, secondaryColor: Int,
                     tintColor: Int) : FinishedView(context, parentWidth, mainColor, secondaryColor, tintColor) {

    protected override val drawable: Int
        get() = R.drawable.ic_checked_mark

    protected override val drawableTintColor: Int
        get() = tintColor

    protected override val circleColor: Int
        get() = mainColor
}
