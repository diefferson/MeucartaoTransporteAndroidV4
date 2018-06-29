package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.mock.MockData
import br.com.disapps.domain.repository.LinesRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test

class SaveAllLinesJsonOnlyTest{


    private val linesRepositoryMock: LinesRepository = mock()
    private val contextExecutorMock: ContextExecutor = mock{ on{scheduler} doReturn Unconfined }
    private val postExecutionContextMock: PostExecutionContext = mock{ on{scheduler} doReturn Unconfined }
    private val logExceptionMock: LogException = mock()
    private lateinit var saveAllLinesJsonOnly: SaveAllLinesJsonOnly

    @Before
    fun setup(){
        saveAllLinesJsonOnly = SaveAllLinesJsonOnly(linesRepositoryMock, contextExecutorMock, postExecutionContextMock, logExceptionMock)
    }

    @Test
    fun buildUseCaseObservableSuccessTest() = runBlocking{
        saveAllLinesJsonOnly.buildUseCaseObservable(SaveAllLinesJsonOnly.Params(MockData.PATH))
    }

    @Test(expected = KnownException::class)
    fun buildUseCaseObservableErrorTest() = runBlocking{
        whenever(linesRepositoryMock.saveAllLinesFromJson(MockData.PATH)).thenThrow(KnownException::class.java)
        val result = saveAllLinesJsonOnly.buildUseCaseObservable(SaveAllLinesJsonOnly.Params(MockData.PATH))
    }

}