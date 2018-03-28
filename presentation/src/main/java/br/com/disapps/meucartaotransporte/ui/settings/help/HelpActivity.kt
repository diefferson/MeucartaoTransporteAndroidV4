package br.com.disapps.meucartaotransporte.ui.settings.help

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import org.koin.android.architecture.ext.getViewModel

class HelpActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
    }

    fun rateApp(view: View){
        startActivity( Intent(Intent.ACTION_VIEW, Uri.parse(resources.getString(R.string.url_app))) )
    }

    fun faq(view: View){
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(resources.getString(R.string.url_faq))))
    }

    fun privacy(view: View){
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(resources.getString(R.string.url_privacy))))
    }

    fun contact(view:View){
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse(getString(R.string.mailto)))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_subject))
        startActivity(Intent.createChooser(emailIntent, getString(R.string.mail_title)))
    }

    fun about(view: View){
        AboutActivity.launch(this)
    }

    companion object {

        fun launch(context: Context){
            val intent = Intent(context, HelpActivity::class.java)
            context.startActivity(intent)
        }
    }
}