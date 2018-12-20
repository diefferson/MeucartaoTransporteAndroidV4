package br.com.disapps.data.entity.mappers

import br.com.disapps.data.entity.Cartao
import br.com.disapps.data.entity.mock.MockData
import br.com.disapps.domain.model.Card
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class CardEntityMapperTest{

    private lateinit var mockCartao: Cartao
    private lateinit var mockCard: Card
    private lateinit var mockListCard : List<Card>
    private lateinit var mockListCartao : List<Cartao>

    @Before
    fun setUp(){
        mockCartao = MockData.cardDTO()
        mockCard = MockData.cardBO()
        mockListCard = MockData.listCardBO()
        mockListCartao = MockData.listCardDTO()
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
}