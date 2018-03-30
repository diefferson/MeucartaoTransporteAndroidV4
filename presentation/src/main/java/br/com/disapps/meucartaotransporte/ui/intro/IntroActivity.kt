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
import br.com.disapps.meucartaotransporte.ui.main.MainActivity
import br.com.disapps.meucartaotransporte.util.extensions.fromHtml
import kotlinx.android.synthetic.main.activity_intro.*
import org.koin.android.architecture.ext.getViewModel

class IntroActivity : BaseActivity(){

    override val viewModel: IntroViewModel
        get() = getViewModel()

    override val activityLayout: Int
        get() = R.layout.activity_intro

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

        setupViewPager()

        btn_skip.setOnClickListener { MainActivity.launch(this) }
        btn_next.setOnClickListener { view_pager.currentItem = view_pager.currentItem+1 }
    }

    private fun setupViewPager() {
        view_pager.apply {
            adapter = IntroPageAdapter(this@IntroActivity, layouts)

            addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
                override fun onPageSelected(position: Int) {
                    if (position == layouts.size - 1) {
                        btn_next.visibility = View.INVISIBLE
                    } else {
                        btn_next.text = getString(R.string.next)
                        btn_next.visibility = View.VISIBLE
                    }
                }
            })
        }

        pageIndicatorView.setViewPager(view_pager)
    }

    companion object {

        fun launch(context: Context){
            val intent = Intent(context, IntroActivity::class.java)
            context.startActivity(intent)
        }
    }
}