package br.com.disapps.data.entity.mappers

import br.com.disapps.data.entity.Linha
import br.com.disapps.data.entity.mock.MockData
import br.com.disapps.domain.model.Line
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

class LineEntityMapperTest{

    private lateinit var mockLinha: Linha
    private lateinit var mockLine: Line
    private lateinit var mockListLine : List<Line>
    private lateinit var mockListLinha : List<Linha>

    @Before
    fun setUp(){
        mockLine = MockData.lineBO()
        mockLinha = MockData.lineDTO()
        mockListLine = MockData.listLineBO()
        mockListLinha = MockData.listLineDTO()
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
}