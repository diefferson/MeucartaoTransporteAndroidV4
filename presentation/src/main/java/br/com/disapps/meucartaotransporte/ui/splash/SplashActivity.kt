package br.com.disapps.meucartaotransporte.ui.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.disapps.domain.interactor.preferences.GetIsFirstAccess
import br.com.disapps.meucartaotransporte.ui.intro.IntroActivity
import br.com.disapps.meucartaotransporte.ui.main.MainActivity
import org.koin.android.ext.android.inject
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

/**
 * Created by diefferson on 15/03/2018.
 */
class SplashActivity : AppCompatActivity(){

    private val getIsFirstAccessUseCase by inject<GetIsFirstAccess>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        getIsFirstAccess()
    }

    private fun getIsFirstAccess(){
        getIsFirstAccessUseCase.execute(Unit) {
            if(it){
                IntroActivity.launch(this@SplashActivity)
            }else{
                MainActivity.launch(this@SplashActivity)
            }
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getIsFirstAccessUseCase.dispose()
    }
}