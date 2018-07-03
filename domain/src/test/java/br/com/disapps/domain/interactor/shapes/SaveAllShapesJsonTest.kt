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
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.runBlocking
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
    fun buildUseCaseObservableSuccessTest() = runBlocking{
        salveAllShapesJson.buildUseCaseObservable(SaveAllShapesJson.Params(City.CWB, MockData.SHAPES_JSON))

    }

    @Test(expected = KnownException::class)
    fun buildUseCaseObservableErrorTest() = runBlocking{
        whenever(shapesRepository.saveAllFromJson(City.CWB, MockData.SHAPES_JSON)).thenThrow(KnownException::class.java)
        val result = salveAllShapesJson.buildUseCaseObservable(SaveAllShapesJson.Params(City.CWB, MockData.SHAPES_JSON))
    }
}