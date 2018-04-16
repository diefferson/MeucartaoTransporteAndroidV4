package br.com.disapps.meucartaotransporte.ui.settings.help

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import br.com.disapps.meucartaotransporte.R
import kotlinx.android.synthetic.main.activity_help.*
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import br.com.disapps.meucartaotransporte.util.extensions.getBitmapFromVectorDrawable
import br.com.disapps.meucartaotransporte.util.extensions.getCustomChromeTabs


class HelpActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        onClickActions()
    }

    private fun onClickActions() {

        rateApp.setOnClickListener {
            this.getCustomChromeTabs().launchUrl(this, Uri.parse(resources.getString(R.string.url_app)))
        }

        faq.setOnClickListener {
            this.getCustomChromeTabs().launchUrl(this, Uri.parse(resources.getString(R.string.url_faq)))
        }

        privacy.setOnClickListener {
            this.getCustomChromeTabs().launchUrl(this, Uri.parse(resources.getString(R.string.url_privacy)))
        }

        contact.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse(getString(R.string.mailto)))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_subject))
            startActivity(Intent.createChooser(emailIntent, getString(R.string.mail_title)))
        }

        about.setOnClickListener {
            AboutActivity.launch(this)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun launch(context: Context){
            val intent = Intent(context, HelpActivity::class.java)
            context.startActivity(intent)
        }
    }
}