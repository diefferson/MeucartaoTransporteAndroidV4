package br.com.disapps.domain.interactor.itineraries

import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.mock.MockData.CODE_LINE
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

class GetAllItinerariesTest{

    private val itinerariesRepository: ItinerariesRepository = mock()
    private val contextExecutorMock: ContextExecutor = mock{ on{scheduler} doReturn Unconfined }
    private val postExecutionContextMock: PostExecutionContext = mock{ on{scheduler} doReturn Unconfined }
    private val logExceptionMock: LogException = mock()
    private val itinerariesMock: List<BusStop> = mock()

    private lateinit var getAllItineraries: GetAllItineraries

    @Before
    fun  setup(){
        getAllItineraries = GetAllItineraries(itinerariesRepository, contextExecutorMock, postExecutionContextMock, logExceptionMock)
    }

    @Test
    fun buildUseCaseObservableSuccessTest() = runBlocking{
        whenever(itinerariesRepository.getAllItineraries(CODE_LINE)).thenReturn(itinerariesMock)
        val result = getAllItineraries.buildUseCaseObservable(GetAllItineraries.Params(CODE_LINE))
        assertEquals(itinerariesMock, result)
    }

    @Test(expected = KnownException::class)
    fun buildUseCaseObservableErrorTest() = runBlocking{
        whenever(itinerariesRepository.getAllItineraries(CODE_LINE)).thenThrow(KnownException::class.java)
        val result = getAllItineraries.buildUseCaseObservable(GetAllItineraries.Params(CODE_LINE))
    }
}