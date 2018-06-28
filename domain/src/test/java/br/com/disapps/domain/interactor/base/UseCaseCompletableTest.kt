package br.com.disapps.domain.interactor.base

import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import com.nhaarman.mockito_kotlin.*
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test

class UseCaseCompletableTest {

    private val contextExecutorMock: ContextExecutor = mock{ on{scheduler} doReturn Unconfined }
    private val postExecutionContextMock: PostExecutionContext = mock{ on{scheduler} doReturn Unconfined }
    private val logExceptionMock: LogException = mock()
    private val completeCallbackMock : () -> Unit = mock()
    private val errorCallbackMock : (e: Throwable) -> Unit = mock()
    private val knownExceptionMock : KnownException = mock()
    private val outMemoryErrorMock : OutOfMemoryError = mock()

    lateinit var testUseCase :TestUseCaseCompletable

    @Before
    fun setup(){
        testUseCase = TestUseCaseCompletable(contextExecutorMock, postExecutionContextMock, logExceptionMock)
    }

    @Test
    fun buildUseCaseObservableSuccessTest() = runBlocking{
        val result = testUseCase.buildUseCaseObservable(TestUseCaseCompletable.Params(true))
    }

    @Test(expected = KnownException::class)
    fun buildUseCaseObservableErrorTest() = runBlocking{
        val result = testUseCase.buildUseCaseObservable(TestUseCaseCompletable.Params(false, error = knownExceptionMock))
    }

    @Test
    fun useCaseExecuteSuccessTest()= runBlocking{
        testUseCase.execute(TestUseCaseCompletable.Params(true), onComplete = completeCallbackMock)
        delay(400)
        verify(completeCallbackMock).invoke()
    }

    @Test
    fun useCaseExecuteErrorTest(){
        testUseCase.execute(TestUseCaseCompletable.Params(false, error = knownExceptionMock), onError = errorCallbackMock)
        verify(errorCallbackMock).invoke(knownExceptionMock)
        verify(logExceptionMock).logException(knownExceptionMock)
    }

    @Test
    fun useCaseExecuteErrorOutMemoryTest(){
        testUseCase.execute(TestUseCaseCompletable.Params(false, isOutOfMemoryError = true, error = outMemoryErrorMock), onError = errorCallbackMock )
        verify(errorCallbackMock).invoke(any<KnownException>())
    }

    @Test
    fun disposeUseCaseTest()= runBlocking {
        testUseCase.execute(TestUseCaseCompletable.Params(true), errorCallbackMock,completeCallbackMock)
        testUseCase.dispose()
        delay(400)
        verifyNoMoreInteractions(completeCallbackMock)
    }

    class TestUseCaseCompletable(contextExecutor: ContextExecutor,
                                 postExecutionContext: PostExecutionContext,
                                 logException: LogException) : UseCaseCompletable<TestUseCaseCompletable.Params>(contextExecutor, postExecutionContext, logException){

        override suspend fun buildUseCaseObservable(params: Params) {
            if(params.isOutOfMemoryError){
                throw params.error
            }

            if(!params.isSuccess){
                throw params.error
            }

            delay(200)
        }

        class Params(val isSuccess :Boolean,
                     val isOutOfMemoryError: Boolean = false,
                     val error: Throwable = Exception())
    }
}