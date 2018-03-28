package br.com.disapps.data.entity.mappers

import br.com.disapps.data.entity.Linha
import br.com.disapps.domain.model.Line
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

class LineEntityMapperTest{

    private val mockLinha: Linha = mock()
    private val mockLine: Line = mock()
    private lateinit var mockListLine : List<Line>
    private lateinit var mockListLinha : List<Linha>

    @Before
    fun setUp(){
        mockBO()
        mockDTO()
        mockListBO()
        mockListDTO()
    }

    @Test
    fun testMapperBOtoDto(){
        Assertions.assertThat(mockLine.toLineDTO()).isEqualToIgnoringNullFields(mockLinha)
    }

    @Test
    fun testMapperDtoToBo(){
        Assertions.assertThat(mockLinha.toLineBO()).isEqualToIgnoringNullFields(mockLine)
    }

    @Test
    fun testMapperListDtoToBo(){
        Assertions.assertThat(mockListLinha.toLineBO()).allSatisfy { t ->
            Assertions.assertThat(t).isEqualToIgnoringNullFields(mockLine)
        }
    }

    @Test
    fun testMapperListBOtoDto(){
        Assertions.assertThat(mockListLine.toLineDTO()).allSatisfy { t ->
            Assertions.assertThat(t).isEqualToIgnoringNullFields(mockLinha)
        }
    }

    private fun mockBO(){
        whenever(mockLine.code).thenReturn(FAKE_CODE)
        whenever(mockLine.name).thenReturn(FAKE_NAME)
        whenever(mockLine.category).thenReturn(FAKE_CATEGORY)
        whenever(mockLine.color).thenReturn(FAKE_COLOR)
        whenever(mockLine.favorite).thenReturn(FAKE_FAVORITE)
    }

    private fun mockDTO(){
        whenever(mockLinha.codigo).thenReturn(FAKE_CODE)
        whenever(mockLinha.nome).thenReturn(FAKE_NAME)
        whenever(mockLinha.categoria).thenReturn(FAKE_CATEGORY)
        whenever(mockLinha.cor).thenReturn(FAKE_COLOR)
        whenever(mockLinha.favorito).thenReturn(FAKE_FAVORITE)
    }

    private fun mockListBO(){
        mockListLine = arrayListOf(mockLine, mockLine, mockLine)
    }

    private fun mockListDTO(){
        mockListLinha = arrayListOf(mockLinha, mockLinha, mockLinha)
    }

    companion object {
        const val FAKE_CODE = "040"
        const val FAKE_NAME = "Interbairros IV"
        const val FAKE_CATEGORY = "Interbairros"
        const val FAKE_COLOR = "green"
        const val FAKE_FAVORITE = 0
    }
}