package br.com.disapps.meucartaotransporte.exception

import br.com.disapps.domain.exception.KnownError

class UiError(val knownError: KnownError,val message:String)