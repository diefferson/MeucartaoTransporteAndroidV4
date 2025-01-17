package br.com.disapps.meucartaotransporte.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.View
import br.com.disapps.meucartaotransporte.R
import java.text.Normalizer


fun String.isCPF(): Boolean{
    val cpfClean = this.replace(".", "").replace("-", "")

    //## check if size is eleven
    if (cpfClean.length != 11)
        return false

    //## check if is number
    try {
        cpfClean.toLong()
    }catch (e : Exception){
        return false
    }

    //continue
    val dvCurrent10 = cpfClean.substring(9,10).toInt()
    val dvCurrent11= cpfClean.substring(10,11).toInt()

    //the sum of the nine first digits determines the tenth digit
    val cpfNineFirst = IntArray(9)
    var i = 9
    while (i > 0 ) {
        cpfNineFirst[i-1] = cpfClean.substring(i-1, i).toInt()
        i--
    }
    //multiple the nine digits for your weights: 10,9..2
    val sumProductNine = IntArray(9)
    var weight = 10
    var position = 0
    while (weight >= 2){
        sumProductNine[position] = weight * cpfNineFirst[position]
        weight--
        position++
    }
    //Verify the nineth digit
    var dvForTenthDigit = sumProductNine.sum() % 11
    dvForTenthDigit = 11 - dvForTenthDigit //rule for tenth digit
    if(dvForTenthDigit > 9)
        dvForTenthDigit = 0
    if (dvForTenthDigit != dvCurrent10)
        return false

    //### verify tenth digit
    val cpfTenFirst = cpfNineFirst.copyOf(10)
    cpfTenFirst[9] = dvCurrent10
    //multiple the nine digits for your weights: 10,9..2
    val sumProductTen = IntArray(10)
    var w = 11
    var p = 0
    while (w >= 2){
        sumProductTen[p] = w * cpfTenFirst[p]
        w--
        p++
    }
    //Verify the nineth digit
    var dvForeleventhDigit = sumProductTen.sum() % 11
    dvForeleventhDigit = 11 - dvForeleventhDigit //rule for tenth digit
    if(dvForeleventhDigit > 9)
        dvForeleventhDigit = 0
    if (dvForeleventhDigit != dvCurrent11)
        return false

    return true

}

fun String.clean() : String {
    return Normalizer.normalize(this, Normalizer.Form.NFD).replace("\\p{InCombiningDiacriticalMarks}".toRegex(), "")
}

/**
 * Extension function to get the center of a view
 * @return Pair containing (x|y)
 */
fun View.getCenter(): Pair<Float, Float> {
    val cx = this.x + this.width / 2
    val cy = this.y + this.height / 2
    return Pair(cx, cy)
}

fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
    var drawable = ContextCompat.getDrawable(context, drawableId)
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        drawable = DrawableCompat.wrap(drawable!!).mutate()
    }

    val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap
}

fun Context.getCustomChromeTabs(): CustomTabsIntent {
    return CustomTabsIntent.Builder(null).apply {
        setToolbarColor(ContextCompat.getColor(this@getCustomChromeTabs, R.color.colorPrimary))
        setCloseButtonIcon(getBitmapFromVectorDrawable(this@getCustomChromeTabs, R.drawable.ic_arrow_back_white))
        setStartAnimations(this@getCustomChromeTabs, R.anim.slide_in_right,  R.anim.slide_out_left)
        setExitAnimations(this@getCustomChromeTabs, R.anim.slide_in_left, R.anim.slide_out_right)
        setShowTitle(true)
    }.build()
}