package br.com.disapps.data.entity.mappers

import br.com.disapps.data.entity.Cartao
import br.com.disapps.domain.model.Card
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class CardEntityMapperTest{

    private val mockCartao: Cartao = mock()
    private val mockCard: Card = mock()
    private lateinit var mockListCard : List<Card>
    private lateinit var mockListCartao : List<Cartao>

    @Before
    fun setUp(){
        mockBO()
        mockDTO()
        mockListBO()
        mockListDTO()
    }

    @Test
    fun testMapperBOtoDto(){
        assertThat(mockCard.toCardDTO()).isEqualToIgnoringNullFields(mockCartao)
    }

    @Test
    fun testMapperDtoToBo(){
        assertThat(mockCartao.toCardBO()).isEqualToIgnoringNullFields(mockCard)
    }

    @Test
    fun testMapperListDtoToBo(){
        assertThat(mockListCartao.toCardBO()).allSatisfy { t ->
            assertThat(t).isEqualToIgnoringNullFields(mockCard)
        }
    }

    @Test
    fun testMapperListBOtoDto(){
        assertThat(mockListCard.toCardDTO()).allSatisfy { t ->
            assertThat(t).isEqualToIgnoringNullFields(mockCartao)
        }
    }

    private fun mockBO(){
        whenever(mockCard.code).thenReturn(FAKE_CODE)
        whenever(mockCard.cpf).thenReturn(FAKE_CPF)
        whenever(mockCard.name).thenReturn(FAKE_NAME)
        whenever(mockCard.type).thenReturn(FAKE_TYPE)
        whenever(mockCard.status).thenReturn(FAKE_STATUS)
        whenever(mockCard.balance).thenReturn(FAKE_BALANCE)
        whenever(mockCard.balanceDate).thenReturn(FAKE_BALANCE_DATE)
    }

    private fun mockDTO(){
        whenever(mockCartao.codigo).thenReturn(FAKE_CODE)
        whenever(mockCartao.cpf).thenReturn(FAKE_CPF)
        whenever(mockCartao.nome).thenReturn(FAKE_NAME)
        whenever(mockCartao.tipo).thenReturn(FAKE_TYPE)
        whenever(mockCartao.estado).thenReturn(FAKE_STATUS)
        whenever(mockCartao.saldo).thenReturn(FAKE_BALANCE)
        whenever(mockCartao.dataSaldo).thenReturn(FAKE_BALANCE_DATE)
    }

    private fun mockListBO(){
        mockListCard = arrayListOf(mockCard, mockCard, mockCard)
    }

    private fun mockListDTO(){
        mockListCartao = arrayListOf(mockCartao, mockCartao, mockCartao)
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