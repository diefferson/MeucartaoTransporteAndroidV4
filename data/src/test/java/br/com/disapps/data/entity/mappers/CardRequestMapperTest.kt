package br.com.disapps.data.entity.mappers

import br.com.disapps.data.entity.RequestCartao
import br.com.disapps.data.entity.mock.MockData
import br.com.disapps.domain.model.Card
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class CardRequestMapperTest{

    private lateinit var mockRequestCartao: RequestCartao
    private lateinit var mockCard: Card

    @Before
    fun setUp(){
        mockCard = MockData.cardBO()
        mockRequestCartao = MockData.requestCard()
    }

    @Test
    fun testMapperBOtoResquestCartao(){
        mockRequestCartao.tipoConsulta = MockData.BALANCE
        assertThat( mockCard.toRequestCardDTO()).isEqualToIgnoringNullFields(mockRequestCartao)
    }

    @Test
    fun testMapperBOtoRequestExtrato(){
        mockRequestCartao.tipoConsulta = MockData.EXTRACT
        assertThat( mockCard.toRequestExtractDTO()).isEqualToIgnoringNullFields(mockRequestCartao)
    }

    @Test
    fun testMapperDtoToBo(){
        assertThat(mockRequestCartao.toCardBO().code).isEqualTo(mockCard.code)
        assertThat(mockRequestCartao.toCardBO().cpf).isEqualTo(mockCard.cpf)
    }


}