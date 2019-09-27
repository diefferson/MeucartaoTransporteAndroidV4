package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Line
import br.com.disapps.domain.repository.LinesRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetLinesTest {

    private val linesRepositoryMock:LinesRepository = mock()
    private val linesMock:List<Line> = mock()
    private lateinit var getLinesUseCase: GetLines

    @Before
    fun setup(){
        getLinesUseCase = GetLines(linesRepositoryMock)
    }

    @Test
    fun runSuccessTest() = runBlocking{
        whenever(linesRepositoryMock.lines()).thenReturn(linesMock)
        val result = getLinesUseCase.run(UseCase.None())
        assertEquals(linesMock, result)
    }

    @Test(expected = KnownException::class)
    fun runErrorTest() = runBlocking{
        whenever(linesRepositoryMock.lines()).thenThrow(KnownException::class.java)
        val result = getLinesUseCase.run(UseCase.None())
    }


}