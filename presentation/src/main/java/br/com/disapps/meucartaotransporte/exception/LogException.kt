package br.com.disapps.meucartaotransporte.exception

import br.com.disapps.domain.exception.LogException
import com.crashlytics.android.Crashlytics

class LogException : LogException {
    override fun logException(throwable: Throwable) {
        try {
            Crashlytics.logException(throwable)
        }catch (e :Exception){
            e.printStackTrace()
        }

    }
}