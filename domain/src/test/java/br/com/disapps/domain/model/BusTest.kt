package br.com.disapps.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class BusTest{

    private lateinit var bus : Bus

    @Before
    fun setUp() {
        bus = Bus(
             FAKE_PREFIX,
             FAKE_TIME,
             FAKE_LATITUDE,
             FAKE_LONGITUDE,
             FAKE_LINE
        )
    }

    @Test
    fun testBusConstructorHappyCase() {
        assertThat(bus.prefix).isEqualTo(FAKE_PREFIX)
        assertThat(bus.time).isEqualTo(FAKE_TIME)
        assertThat(bus.line).isEqualTo(FAKE_LINE)
        assertThat(bus.latitude).isEqualTo(FAKE_LATITUDE)
        assertThat(bus.longitude).isEqualTo(FAKE_LONGITUDE)
    }

    companion object {
        const val FAKE_PREFIX = "001H110049"
        const val FAKE_TIME = "Rua Tibagi, 386 - Centro"
        const val FAKE_LINE = "001"
        const val FAKE_LATITUDE = "-25,430387594599"
        const val FAKE_LONGITUDE = "-49,263602228112"

    }
}
