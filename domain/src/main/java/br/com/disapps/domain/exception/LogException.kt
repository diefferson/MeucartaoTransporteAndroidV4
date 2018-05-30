package br.com.disapps.domain.exception

interface LogException{
    fun logException(throwable: Throwable)
}