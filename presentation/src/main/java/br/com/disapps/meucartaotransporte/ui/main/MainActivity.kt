package br.com.disapps.meucartaotransporte.ui.main

import android.content.Context
import android.os.Bundle
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import br.com.disapps.meucartaotransporte.util.extensions.setMutableText
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.architecture.ext.getViewModel
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {

    override val viewModel : MainViewModel
        get() = getViewModel()

    override val activityTag: String
        get() = MainActivity::class.java.simpleName

    override val activityName: String
        get() =  MainActivity::class.java.simpleName

    override val activityLayout: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myTextView.setMutableText(this, viewModel.text )

        myButton.setOnClickListener { viewModel.text.postValue("Novo Texto") }
    }
}

