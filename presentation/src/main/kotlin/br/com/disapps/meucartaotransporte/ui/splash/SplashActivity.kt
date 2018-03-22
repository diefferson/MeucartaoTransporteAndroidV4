package br.com.disapps.meucartaotransporte.ui.splash

import android.support.v7.app.AppCompatActivity
import android.content.Intent
import br.com.disapps.meucartaotransporte.ui.main.MainActivity
import android.os.Bundle
import br.com.disapps.meucartaotransporte.R


/**
 * Created by diefferson on 15/03/2018.
 */
class SplashActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}