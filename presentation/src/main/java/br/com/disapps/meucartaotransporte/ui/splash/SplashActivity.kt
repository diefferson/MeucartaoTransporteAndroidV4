package br.com.disapps.meucartaotransporte.ui.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.meucartaotransporte.app.App
import br.com.disapps.meucartaotransporte.ui.intro.IntroActivity
import br.com.disapps.meucartaotransporte.ui.main.MainActivity
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

/**
 * Created by diefferson on 15/03/2018.
 */
class SplashActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        getIsFirstAccess()
    }

    private fun getIsFirstAccess(){
        if(Preferences(this).getIsFirstAccess()){
            IntroActivity.launch(this)
        }else{
            MainActivity.launch(this)
        }
        finish()
    }
}