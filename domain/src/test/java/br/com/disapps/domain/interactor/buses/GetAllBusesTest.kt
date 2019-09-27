package br.com.disapps.domain.interactor.buses

import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.mock.MockData.CODE_LINE
import br.com.disapps.domain.mock.MockData.CODE_LINE_ERROR
import br.com.disapps.domain.model.Bus
import br.com.disapps.domain.repository.BusesRepository
import com.nhaarman.mockito_kotlin.*
import kotlinx.coroutines.Unconfined
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetAllBusesTest{

    private val busesRepositoryMock: BusesRepository = mock()
    private val logExceptionMock: LogException = mock()
    private val contextExecutorMock: ContextExecutor = mock{ on { scheduler } doReturn(Unconfined) }
    private val postExecutionContextMock: PostExecutionContext= mock{ on { scheduler } doReturn(Unconfined) }
    private val mockBuses: List<Bus> = mock()

    private lateinit var getAllBusesUseCase :GetAllBuses

    @Before
    fun setup(){
        getAllBusesUseCase = GetAllBuses(busesRepositoryMock, contextExecutorMock, postExecutionContextMock, logExceptionMock)

    }

    @Test
    fun testrunSuccessCase() = runBlocking {
        whenever(busesRepositoryMock.getAllBuses(CODE_LINE)).thenReturn(mockBuses)
        val result = getAllBusesUseCase.run(GetAllBuses.Params(CODE_LINE))
        assertEquals(mockBuses, result)
    }

    @Test(expected = KnownException::class)
    fun testrunErrorCase() = runBlocking {
        whenever(busesRepositoryMock.getAllBuses(CODE_LINE_ERROR)).thenThrow(KnownException::class.java)
        val result = getAllBusesUseCase.run(GetAllBuses.Params(CODE_LINE_ERROR))
    }

}