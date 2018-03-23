package br.com.disapps.domain.model

import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

class ShapeTest{

    private lateinit var shape: Shape

    @Before
    fun setUp(){
        shape = Shape(
                FAKE_CODE,
                FAKE_LINE_CODE,
                FAKE_COORDINATES
        )
    }

    @Test
    fun testShapeConstructorHappyCase(){
        Assertions.assertThat(shape.code).isEqualTo(FAKE_CODE)
        Assertions.assertThat(shape.lineCode).isEqualTo(FAKE_LINE_CODE)
        Assertions.assertThat(shape.coordinates).isEqualTo(FAKE_COORDINATES)
    }

    companion object {
        const val FAKE_CODE  = "0012938"
        const val FAKE_LINE_CODE = "001"
        val FAKE_COORDINATES = ArrayList<Coordinate>()
    }

}