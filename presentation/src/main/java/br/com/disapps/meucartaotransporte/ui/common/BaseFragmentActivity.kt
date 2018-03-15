package br.com.disapps.meucartaotransporte.ui.common

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.FrameLayout

/**
 * Created by diefferson on 29/11/2017.
 */
abstract class BaseFragmentActivity : AppCompatActivity(), IBaseFragmentActivityListener {

    abstract val viewModel: BaseViewModel
    abstract val activityLayout: Int
    abstract val container: FrameLayout
    abstract val toolbar : Toolbar
    abstract val initialFragment : BaseFragment
    var initialFragmentItemId: Int =0

    private val fragmentTransaction: FragmentTransaction
        get() = supportFragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityLayout)
        setSupportActionBar(toolbar)
        replaceFragment(initialFragment)
        checkItemMenu(initialFragmentItemId)
    }

    override fun setTitle(title: String) {
        supportActionBar!!.title = title
    }

    override fun setDisplayHomeAsUpEnabled() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun replaceFragment(fragment: Fragment) {
        val ft = fragmentTransaction
        ft.replace(container.id, fragment, fragment.javaClass.simpleName)
        ft.commit()
    }

    override fun replaceAndBackStackFragment(fragment: Fragment) {
        val ft = fragmentTransaction
        ft.replace(container.id, fragment, fragment.javaClass.simpleName)
        ft.addToBackStack(fragment.javaClass.simpleName)
        ft.commit()
    }

    override fun checkItemMenu(itemId: Int) {
        //implements in child
    }
}