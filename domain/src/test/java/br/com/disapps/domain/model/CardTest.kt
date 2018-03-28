package br.com.disapps.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class CardTest{
    private lateinit var card : Card

    @Before
    fun setUp() {
        card = Card(
                FAKE_CODE,
                FAKE_CPF,
                FAKE_NAME,
                FAKE_TYPE,
                FAKE_STATUS,
                FAKE_BALANCE,
                FAKE_BALANCE_DATE
        )
    }

    @Test
    fun testCardConstructorHappyCase() {
        assertThat(card.code).isEqualTo(FAKE_CODE)
        assertThat(card.cpf).isEqualTo(FAKE_CPF)
        assertThat(card.name).isEqualTo(FAKE_NAME)
        assertThat(card.type).isEqualTo(FAKE_TYPE)
        assertThat(card.status).isEqualTo(FAKE_STATUS)
        assertThat(card.balance).isEqualTo(FAKE_BALANCE)
        assertThat(card.balanceDate).isEqualTo(FAKE_BALANCE_DATE)
    }

    companion object {
        const val FAKE_CODE = "2909840"
        const val FAKE_CPF = "0969591980"
        const val FAKE_NAME = "Cartão Teste"
        const val FAKE_TYPE = "Usuário"
        const val FAKE_STATUS = "Ativo"
        const val FAKE_BALANCE = 59.05
        const val FAKE_BALANCE_DATE = "22/03/2018 08:17:51"
    }
}

