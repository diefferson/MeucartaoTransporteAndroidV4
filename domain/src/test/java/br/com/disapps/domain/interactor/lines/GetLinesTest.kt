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
import kotlin.test.assertEquals

class GetLinesTest {

    private val linesRepositoryMock:LinesRepository = mock()
    private val contextExecutorMock: ContextExecutor = mock{ on{scheduler} doReturn Unconfined }
    private val postExecutionContextMock: PostExecutionContext = mock{ on{scheduler} doReturn Unconfined }
    private val logExceptionMock: LogException = mock()
    private val linesMock:List<Line> = mock()
    private lateinit var getLinesUseCase: GetLines

    @Before
    fun setup(){
        getLinesUseCase = GetLines(linesRepositoryMock, contextExecutorMock, postExecutionContextMock, logExceptionMock)
    }

    @Test
    fun buildUseCaseObservableSuccessTest() = runBlocking{
        whenever(linesRepositoryMock.lines()).thenReturn(linesMock)
        val result = getLinesUseCase.buildUseCaseObservable(Unit)
        assertEquals(linesMock, result)
    }

    @Test(expected = KnownException::class)
    fun buildUseCaseObservableErrorTest() = runBlocking{
        whenever(linesRepositoryMock.lines()).thenThrow(KnownException::class.java)
        val result = getLinesUseCase.buildUseCaseObservable(Unit)
    }


}