package br.com.disapps.domain.interactor.itineraries

import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.mock.MockData
import br.com.disapps.domain.model.BusStop
import br.com.disapps.domain.repository.ItinerariesRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.Unconfined
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetItineraryDirectionsTest{

    private val itinerariesRepository: ItinerariesRepository = mock()
    private val contextExecutorMock: ContextExecutor = mock{ on{scheduler} doReturn Unconfined }
    private val postExecutionContextMock: PostExecutionContext = mock{ on{scheduler} doReturn Unconfined }
    private val logExceptionMock: LogException = mock()
    private val itineraryDirectionsMock: List<String> = mock()

    private lateinit var getItineraryDirectionsUseCase: GetItineraryDirections

    @Before
    fun  setup(){
        getItineraryDirectionsUseCase = GetItineraryDirections(itinerariesRepository, contextExecutorMock, postExecutionContextMock, logExceptionMock)
    }

    @Test
    fun runSuccessTest() = runBlocking{
        whenever(itinerariesRepository.getItineraryDirections(MockData.CODE_LINE)).thenReturn(itineraryDirectionsMock)
        val result = getItineraryDirectionsUseCase.run(GetItineraryDirections.Params(MockData.CODE_LINE))
        assertEquals(itineraryDirectionsMock, result)
    }

    @Test(expected = KnownException::class)
    fun runErrorTest() = runBlocking{
        whenever(itinerariesRepository.getItineraryDirections(MockData.CODE_LINE)).thenThrow(KnownException::class.java)
        val result = getItineraryDirectionsUseCase.run(GetItineraryDirections.Params(MockData.CODE_LINE))
    }

}