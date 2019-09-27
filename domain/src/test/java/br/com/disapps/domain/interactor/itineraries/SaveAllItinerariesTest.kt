package br.com.disapps.domain.interactor.itineraries

import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.mock.MockData
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.ItinerariesRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.Unconfined
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SaveAllItinerariesTest{

    private val itinerariesRepository: ItinerariesRepository = mock()
    private val contextExecutorMock: ContextExecutor = mock{ on{scheduler} doReturn Unconfined }
    private val postExecutionContextMock: PostExecutionContext = mock{ on{scheduler} doReturn Unconfined }
    private val logExceptionMock: LogException = mock()

    private lateinit var saveAllItinerariesJsonUseCase: SaveAllItinerariesJson

    @Before
    fun  setup(){
        saveAllItinerariesJsonUseCase = SaveAllItinerariesJson(itinerariesRepository, contextExecutorMock, postExecutionContextMock, logExceptionMock)
    }

    @Test
    fun runSuccessTest() = runBlocking{
      saveAllItinerariesJsonUseCase.run(SaveAllItinerariesJson.Params(City.CWB, MockData.ITINERARIES_JSON))

    }

    @Test(expected = KnownException::class)
    fun runErrorTest() = runBlocking{
        whenever(itinerariesRepository.saveAllFromJson(City.CWB, MockData.ITINERARIES_JSON)).thenThrow(KnownException::class.java)
        val result = saveAllItinerariesJsonUseCase.run(SaveAllItinerariesJson.Params(City.CWB, MockData.ITINERARIES_JSON))
    }
}