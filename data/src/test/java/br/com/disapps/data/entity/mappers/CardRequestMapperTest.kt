package br.com.disapps.data.entity.mappers

import br.com.disapps.data.entity.RequestCartao
import br.com.disapps.domain.model.Card
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class CardRequestMapperTest{

    private val mockRequestCartao: RequestCartao = mock()
    private val mockCard: Card = mock()

    @Before
    fun setUp(){
        mockCard()
        mockRequestCartao()
    }

    @Test
    fun testMapperBOtoResquestCartao(){
        whenever(mockRequestCartao.tipoConsulta).thenReturn("saldo")
        assertThat( mockCard.toRequestCardDTO()).isEqualToIgnoringNullFields(mockRequestCartao)
    }

    @Test
    fun testMapperBOtoRequestExtrato(){
        whenever(mockRequestCartao.tipoConsulta).thenReturn("extrato")
        assertThat( mockCard.toRequestExtractDTO()).isEqualToIgnoringNullFields(mockRequestCartao)
    }

    @Test
    fun testMapperDtoToBo(){
        assertThat(mockRequestCartao.toCardBO()).isEqualToIgnoringNullFields(mockCard)
    }

    private fun mockCard(){
        whenever(mockCard.code).thenReturn(FAKE_CODE)
        whenever(mockCard.cpf).thenReturn(FAKE_CPF)
    }

    private fun mockRequestCartao(){
        whenever(mockRequestCartao.codigo).thenReturn(FAKE_CODE)
        whenever(mockRequestCartao.cpf).thenReturn(FAKE_CPF)

    }

    companion object {
        const val FAKE_CODE = "2909840"
        const val FAKE_CPF = "0969591980"
    }
}