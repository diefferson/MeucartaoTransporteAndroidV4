package br.com.disapps.meucartaotransporte.ui.intro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.util.extensions.fromHtml
import kotlinx.android.synthetic.main.activity_intro.*
import org.koin.android.architecture.ext.getViewModel

class IntroActivity : BaseActivity(){

    override val viewModel: BaseViewModel
        get() = getViewModel()

    override val activityLayout: Int
        get() = R.layout.activity_intro

    private lateinit var dots: ArrayList<TextView>

    private val layouts : IntArray by lazy {
        intArrayOf(R.layout.intro_slide0,
                R.layout.intro_slide1,
                R.layout.intro_slide2,
                R.layout.intro_slide3,
                R.layout.intro_slide4,
                R.layout.intro_slide5,
                R.layout.intro_slide6,
                R.layout.intro_slide7,
                R.layout.intro_slide8,
                R.layout.intro_slide9,
                R.layout.intro_slide10
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        addBottomDots(0)

        val viewPagerAdapter = IntroPageAdapter(this, layouts)
        view_pager.adapter = viewPagerAdapter
        view_pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                addBottomDots(position)

                if (position == layouts.size - 1) {
                    btn_next.visibility = View.GONE
                } else {

                    btn_next.text = getString(R.string.next)
                    btn_next.visibility = View.VISIBLE
                }
            }
        })
    }
    
    private fun addBottomDots(currentPage: Int) {
        dots = ArrayList()
        layoutDots.removeAllViews()
        for (i in 0..(layouts.size-1)) {
            dots [i] = TextView(this)
            dots[i].text = fromHtml("&#8226;")
            dots[i].textSize = 35f
            dots[i].setTextColor(ContextCompat.getColor(this, R.color.dot_inactive))
            layoutDots.addView(dots[i])
        }
        dots[currentPage].setTextColor(ContextCompat.getColor(this,R.color.dot_active))
    }


    companion object {

        fun launch(context: Context){
            val intent = Intent(context, IntroActivity::class.java)
            context.startActivity(intent)
        }
    }
}