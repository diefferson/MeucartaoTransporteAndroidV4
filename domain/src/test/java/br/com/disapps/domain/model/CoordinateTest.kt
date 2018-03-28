package br.com.disapps.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class CoordinateTest{
    private lateinit var coordinate: Coordinate

    @Before
    fun setUp() {
        coordinate = Coordinate(
                FAKE_LATITUDE,
                FAKE_LONGITUDE
        )
    }

    @Test
    fun testCoordinateConstructorHappyCase() {
        assertThat(coordinate.latitude).isEqualTo(FAKE_LATITUDE)
        assertThat(coordinate.longitude).isEqualTo(FAKE_LONGITUDE)

    }

    companion object {
        const val FAKE_LATITUDE = "-25,430387594599"
        const val FAKE_LONGITUDE = "-49,263602228112"
    }

}