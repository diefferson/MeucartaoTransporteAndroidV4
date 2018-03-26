package br.com.disapps.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class BusStopTest{

    private lateinit var busStop : BusStop

    @Before
    fun setUp() {
        busStop = BusStop(
                FAKE_CODE,
                FAKE_NAME,
                FAKE_LINE_CODE,
                FAKE_LATITUDE,
                FAKE_LONGITUDE,
                FAKE_SEQUENCE,
                FAKE_DIRECTION,
                FAKE_TYPE
        )
    }

    @Test
    fun testBusStopConstructorHappyCase() {
        assertThat(busStop.code).isEqualTo(FAKE_CODE)
        assertThat(busStop.name).isEqualTo(FAKE_NAME)
        assertThat(busStop.lineCode).isEqualTo(FAKE_LINE_CODE)
        assertThat(busStop.latitude).isEqualTo(FAKE_LATITUDE)
        assertThat(busStop.longitude).isEqualTo(FAKE_LONGITUDE)
        assertThat(busStop.sequence).isEqualTo(FAKE_SEQUENCE)
        assertThat(busStop.direction).isEqualTo(FAKE_DIRECTION)
        assertThat(busStop.type).isEqualTo(FAKE_TYPE)
    }

    companion object {
        const val FAKE_CODE = "001H110049"
        const val FAKE_NAME = "Rua Tibagi, 386 - Centro"
        const val FAKE_LINE_CODE = "001"
        const val FAKE_LATITUDE = "-25,430387594599"
        const val FAKE_LONGITUDE = "-49,263602228112"
        const val FAKE_SEQUENCE = 3
        const val FAKE_DIRECTION = "Horário"
        const val FAKE_TYPE = "Novo mobiliário"
    }
}

