package br.com.disapps.domain.interactor.shapes

import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.mock.MockData
import br.com.disapps.domain.model.Shape
import br.com.disapps.domain.repository.ShapesRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals


class GetShapesTest{

    private val shapesRepositoryMock: ShapesRepository = mock()
    private val shapesMock :List<Shape> = mock()
    private lateinit var getShapesUseCase: GetShapes

    @Before
    fun setup(){
        getShapesUseCase = GetShapes(shapesRepositoryMock)
    }

    @Test
    fun runSuccessTest() = runBlocking{
        whenever(shapesRepositoryMock.getShapes(MockData.CODE_LINE)).thenReturn(shapesMock)
        val result = getShapesUseCase.run(GetShapes.Params(MockData.CODE_LINE))
        assertEquals(shapesMock, result)
    }

    @Test(expected = KnownException::class)
    fun runErrorTest() = runBlocking{
        whenever(shapesRepositoryMock.getShapes(MockData.CODE_LINE)).thenThrow(KnownException::class.java)
        val result = getShapesUseCase.run(GetShapes.Params(MockData.CODE_LINE))
    }
}