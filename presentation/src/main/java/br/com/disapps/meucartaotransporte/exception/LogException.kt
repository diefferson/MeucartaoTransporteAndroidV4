package br.com.disapps.meucartaotransporte.exception

import br.com.disapps.domain.exception.LogException
import com.crashlytics.android.Crashlytics

class LogException : LogException {
    override fun logException(throwable: Throwable) {
        Crashlytics.logException(throwable)
    }
}