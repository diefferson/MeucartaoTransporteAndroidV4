package br.com.disapps.domain.interactor.itineraries

import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.mock.MockData.CODE_LINE
import br.com.disapps.domain.model.BusStop
import br.com.disapps.domain.repository.ItinerariesRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetAllItinerariesTest{

    private val itinerariesRepository: ItinerariesRepository = mock()
    private val itinerariesMock: List<BusStop> = mock()

    private lateinit var getAllItineraries: GetAllItineraries

    @Before
    fun  setup(){
        getAllItineraries = GetAllItineraries(itinerariesRepository)
    }

    @Test
    fun runSuccessTest() = runBlocking{
        whenever(itinerariesRepository.getAllItineraries(CODE_LINE)).thenReturn(itinerariesMock)
        val result = getAllItineraries.run(GetAllItineraries.Params(CODE_LINE))
        assertEquals(itinerariesMock, result)
    }

    @Test(expected = KnownException::class)
    fun runErrorTest() = runBlocking{
        whenever(itinerariesRepository.getAllItineraries(CODE_LINE)).thenThrow(KnownException::class.java)
        val result = getAllItineraries.run(GetAllItineraries.Params(CODE_LINE))
    }
}