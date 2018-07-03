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
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetItineraryTest{
    private val itinerariesRepository: ItinerariesRepository = mock()
    private val contextExecutorMock: ContextExecutor = mock{ on{scheduler} doReturn Unconfined }
    private val postExecutionContextMock: PostExecutionContext = mock{ on{scheduler} doReturn Unconfined }
    private val logExceptionMock: LogException = mock()
    private val itineraryMock: List<BusStop> = mock()

    private lateinit var getItineraryUseCase: GetItinerary

    @Before
    fun  setup(){
        getItineraryUseCase = GetItinerary(itinerariesRepository, contextExecutorMock, postExecutionContextMock, logExceptionMock)
    }

    @Test
    fun buildUseCaseObservableSuccessTest() = runBlocking{
        whenever(itinerariesRepository.getItinerary(MockData.CODE_LINE, MockData.DIRECTION)).thenReturn(itineraryMock)
        val result = getItineraryUseCase.buildUseCaseObservable(GetItinerary.Params(MockData.CODE_LINE,MockData.DIRECTION))
        assertEquals(itineraryMock, result)
    }

    @Test(expected = KnownException::class)
    fun buildUseCaseObservableErrorTest() = runBlocking{
        whenever(itinerariesRepository.getItinerary(MockData.CODE_LINE,MockData.DIRECTION)).thenThrow(KnownException::class.java)
        val result = getItineraryUseCase.buildUseCaseObservable(GetItinerary.Params(MockData.CODE_LINE,MockData.DIRECTION))
    }
}