package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.model.Line
import br.com.disapps.domain.repository.LinesRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UpdateLineTest{

    private val linesRepositoryMock: LinesRepository = mock()
    private val lineMock : Line = mock()
    private lateinit var updateLineUseCase: UpdateLine

    @Before
    fun setup(){
        updateLineUseCase = UpdateLine(linesRepositoryMock)
    }

    @Test
    fun runSuccessTest() = runBlocking{
        updateLineUseCase.run(UpdateLine.Params(lineMock))
    }

    @Test(expected = KnownException::class)
    fun runErrorTest() = runBlocking{
        whenever(linesRepositoryMock.updateLine(lineMock)).thenThrow(KnownException::class.java)
        val result = updateLineUseCase.run(UpdateLine.Params(lineMock))
    }
}