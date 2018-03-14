package br.com.disapps.meucartaotransporte.ui.common

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by diefferson on 09/03/2018.
 */
abstract class BaseActivity : AppCompatActivity(){

    abstract val viewModel: ViewModel
    abstract val activityLayout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityLayout)
    }
}