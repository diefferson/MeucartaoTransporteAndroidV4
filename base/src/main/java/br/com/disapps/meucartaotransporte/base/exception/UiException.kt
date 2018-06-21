package br.com.disapps.meucartaotransporte.base.exception

import br.com.disapps.domain.exception.KnownError

class UiException(val knownError: KnownError, var message:String)