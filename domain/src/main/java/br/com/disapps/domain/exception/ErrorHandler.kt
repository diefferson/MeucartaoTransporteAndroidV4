package br.com.disapps.domain.exception

interface ErrorHandler{
    fun handler(exception:Throwable):KnownException
}