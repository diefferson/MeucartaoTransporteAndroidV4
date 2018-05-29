package br.com.disapps.domain.exception

open class KnownException(var knownError: KnownError = KnownError.UNKNOWN_EXCEPTION, message:String) : Exception( message)
