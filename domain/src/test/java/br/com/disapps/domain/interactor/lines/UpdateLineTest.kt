package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.model.Line
import br.com.disapps.domain.repository.LinesRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test

class UpdateLineTest{

    private val linesRepositoryMock: LinesRepository = mock()
    private val contextExecutorMock: ContextExecutor = mock{ on{scheduler} doReturn Unconfined }
    private val postExecutionContextMock: PostExecutionContext = mock{ on{scheduler} doReturn Unconfined }
    private val logExceptionMock: LogException = mock()
    private val lineMock : Line = mock()
    private lateinit var updateLineUseCase: UpdateLine

    @Before
    fun setup(){
        updateLineUseCase = UpdateLine(linesRepositoryMock, contextExecutorMock, postExecutionContextMock, logExceptionMock)
    }

    @Test
    fun buildUseCaseObservableSuccessTest() = runBlocking{
        updateLineUseCase.buildUseCaseObservable(UpdateLine.Params(lineMock))
    }

    @Test(expected = KnownException::class)
    fun buildUseCaseObservableErrorTest() = runBlocking{
        whenever(linesRepositoryMock.updateLine(lineMock)).thenThrow(KnownException::class.java)
        val result = updateLineUseCase.buildUseCaseObservable(UpdateLine.Params(lineMock))
    }
}