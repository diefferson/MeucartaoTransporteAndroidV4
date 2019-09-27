package br.com.disapps.domain.interactor.shapes

import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.mock.MockData
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.ShapesRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.Unconfined
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SaveAllShapesJsonTest{

    private val shapesRepository: ShapesRepository = mock()
    private val contextExecutorMock: ContextExecutor = mock{ on{scheduler} doReturn Unconfined }
    private val postExecutionContextMock: PostExecutionContext = mock{ on{scheduler} doReturn Unconfined }
    private val logExceptionMock: LogException = mock()

    private lateinit var salveAllShapesJson: SaveAllShapesJson

    @Before
    fun  setup(){
        salveAllShapesJson = SaveAllShapesJson(shapesRepository, contextExecutorMock, postExecutionContextMock, logExceptionMock)
    }

    @Test
    fun runSuccessTest() = runBlocking{
        salveAllShapesJson.run(SaveAllShapesJson.Params(City.CWB, MockData.SHAPES_JSON))

    }

    @Test(expected = KnownException::class)
    fun runErrorTest() = runBlocking{
        whenever(shapesRepository.saveAllFromJson(City.CWB, MockData.SHAPES_JSON)).thenThrow(KnownException::class.java)
        val result = salveAllShapesJson.run(SaveAllShapesJson.Params(City.CWB, MockData.SHAPES_JSON))
    }
}