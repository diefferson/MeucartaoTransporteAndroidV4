package br.com.disapps.domain.interactor.base

interface CompletableCallback{
      fun onComplete()

      fun onError(error : Throwable)
}

interface SingleCallback{

    fun sucess()

    fun error(error : Throwable)

}

interface ObservableCallback{

    fun next()

    fun complete()

    fun error(error : Throwable)

}