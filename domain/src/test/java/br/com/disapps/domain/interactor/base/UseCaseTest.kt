package br.com.disapps.domain.interactor.base

import br.com.disapps.domain.exception.KnownError
import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import com.nhaarman.mockito_kotlin.*
import kotlinx.coroutines.Unconfined
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertEquals

class UseCaseTest{

    private val contextExecutorMock: ContextExecutor = mock{ on{scheduler} doReturn Unconfined }
    private val postExecutionContextMock: PostExecutionContext = mock{ on{scheduler} doReturn Unconfined }
    private val logExceptionMock: LogException = mock()
    private val successCallbackMock : (Boolean) -> Unit = mock()
    private val errorCallbackMock : (e: Throwable) -> Unit = mock()
    private val knownExceptionMock : KnownException = mock()
    private val outMemoryErrorMock : OutOfMemoryError = mock()
    private lateinit var testUseCase : TestUseCase

    @Before
    fun setup(){
        testUseCase = TestUseCase(contextExecutorMock, postExecutionContextMock, logExceptionMock)
    }

    @Test
    fun runSuccessTest() = runBlocking{
        val result:Boolean = testUseCase.run(TestUseCase.Params(true))
        assertEquals(true, result)
    }

    @Test(expected = KnownException::class)
    fun runErrorTest() = runBlocking{
        val result = testUseCase.run(TestUseCase.Params(false, error = knownExceptionMock))
    }

    @Test
    fun useCaseExecuteSuccessTest(){
        testUseCase.execute(TestUseCase.Params(true), onSuccess = successCallbackMock)
        verify(successCallbackMock).invoke(true)
    }

    @Test
    fun useCaseExecuteErrorTest(){
        testUseCase.execute(TestUseCase.Params(false, error = knownExceptionMock), onError = errorCallbackMock)
        verify(errorCallbackMock).invoke(knownExceptionMock)
        verify(logExceptionMock).logException(knownExceptionMock)
    }

    @Test
    fun useCaseExecuteErrorOutMemoryTest(){
        testUseCase.execute(TestUseCase.Params(false, isOutOfMemoryError = true, error = outMemoryErrorMock), onError = errorCallbackMock )
        verify(errorCallbackMock).invoke(any<KnownException>())
    }

    @Test
    fun useCaseExecuteRepeatTest() = runBlocking {
        testUseCase.execute(TestUseCase.Params(true), true, 500,errorCallbackMock,successCallbackMock)
        delay(1200)
        verify(successCallbackMock, times(2)).invoke(true)
        verify(errorCallbackMock).invoke(any())
    }

    @Test
    fun disposeUseCaseTest()= runBlocking {
        testUseCase.execute(TestUseCase.Params(true), true, 500,errorCallbackMock,successCallbackMock)
        verify(successCallbackMock).invoke(true)
        Mockito.reset(successCallbackMock)
        testUseCase.dispose()
        delay(500)
        verifyNoMoreInteractions(successCallbackMock)
        delay(500)
    }

    class TestUseCase(contextExecutor: ContextExecutor,
                      postExecutionContext: PostExecutionContext,
                      logException: LogException) : UseCase<Boolean, TestUseCase.Params>(contextExecutor, postExecutionContext, logException){

        private var cont = 0

        override suspend fun run(params: Params): Boolean {

            if(cont == 2){
                throw KnownException(KnownError.UNKNOWN_EXCEPTION, "")
            }

            if(params.isOutOfMemoryError){
                throw params.error
            }

            if(params.isSuccess){
                cont++
                return params.isSuccess
            }else{
                throw params.error
            }
        }

        class Params(val isSuccess :Boolean,
                     val isOutOfMemoryError: Boolean = false,
                     val error: Throwable = Exception())
    }
}