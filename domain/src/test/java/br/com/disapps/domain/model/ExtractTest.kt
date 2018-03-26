package br.com.disapps.domain.model

import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

class ExtractTest{

    private lateinit var extract: Extract

    @Before
    fun setUp(){
        extract = Extract(
                FAKE_BALANCE,
                FAKE_LOCAL,
                FAKE_DATE,
                FAKE_VALUE
        )
    }

    @Test
    fun testExtractConstructorHappyCase(){
        Assertions.assertThat(extract.balance).isEqualTo(FAKE_BALANCE)
        Assertions.assertThat(extract.local).isEqualTo(FAKE_LOCAL)
        Assertions.assertThat(extract.date).isEqualTo(FAKE_DATE)
        Assertions.assertThat(extract.value).isEqualTo(FAKE_VALUE)
    }

    companion object {
        const val FAKE_BALANCE = 59.60
        const val FAKE_LOCAL = "VEÍCULO GA130 - ALIMENTADOR (542 - BAIRRO NOVO B)"
        const val FAKE_DATE = "22/03/2018 08:17:51"
        const val FAKE_VALUE = -4.25
    }
}

