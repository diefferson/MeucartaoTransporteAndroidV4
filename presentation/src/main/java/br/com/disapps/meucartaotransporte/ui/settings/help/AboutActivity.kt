package br.com.disapps.meucartaotransporte.ui.settings.help

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.disapps.meucartaotransporte.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        var versionName = ""
        val manager = this.packageManager
        try {
            val info = manager.getPackageInfo(this.packageName, 0)
            versionName = info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
        }


        app_version.append(" $versionName")
    }


    companion object {

        fun launch(context: Context){
            val intent = Intent(context, AboutActivity::class.java)
            context.startActivity(intent)
        }
    }
}