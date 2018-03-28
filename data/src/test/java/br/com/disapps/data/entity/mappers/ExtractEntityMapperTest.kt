package br.com.disapps.data.entity.mappers

import br.com.disapps.data.entity.Extrato
import br.com.disapps.domain.model.Extract
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

class ExtractEntityMapperTest{

    private val mockExtrato: Extrato = mock()
    private val mockExtract: Extract = mock()
    private lateinit var mockListExtract : List<Extract>
    private lateinit var mockListExtrato : List<Extrato>

    @Before
    fun setUp(){
        mockBO()
        mockDTO()
        mockListBO()
        mockListDTO()
    }

    @Test
    fun testMapperBOtoDto(){
        Assertions.assertThat(mockExtract.toExtractDTO()).isEqualToIgnoringNullFields(mockExtrato)
    }

    @Test
    fun testMapperDtoToBo(){
        Assertions.assertThat(mockExtrato.toExtractBO()).isEqualToIgnoringNullFields(mockExtract)
    }

    @Test
    fun testMapperListDtoToBo(){
        Assertions.assertThat(mockListExtrato.toExtractBO()).allSatisfy { t ->
            Assertions.assertThat(t).isEqualToIgnoringNullFields(mockExtract)
        }
    }

    @Test
    fun testMapperListBOtoDto(){
        Assertions.assertThat(mockListExtract.toExtractDTO()).allSatisfy { t ->
            Assertions.assertThat(t).isEqualToIgnoringNullFields(mockExtrato)
        }
    }

    private fun mockBO(){
        whenever(mockExtract.balance).thenReturn(FAKE_BALANCE)
        whenever(mockExtract.local).thenReturn(FAKE_LOCAL)
        whenever(mockExtract.date).thenReturn(FAKE_DATE)
        whenever(mockExtract.value).thenReturn(FAKE_VALUE)
    }

    private fun mockDTO(){
        whenever(mockExtrato.saldo).thenReturn(FAKE_BALANCE)
        whenever(mockExtrato.local).thenReturn(FAKE_LOCAL)
        whenever(mockExtrato.data).thenReturn(FAKE_DATE)
        whenever(mockExtrato.valor).thenReturn(FAKE_VALUE)
    }

    private fun mockListBO(){
        mockListExtract = arrayListOf(mockExtract, mockExtract, mockExtract)
    }

    private fun mockListDTO(){
        mockListExtrato = arrayListOf(mockExtrato, mockExtrato, mockExtrato)
    }

    companion object {
        const val FAKE_BALANCE = 59.60
        const val FAKE_LOCAL = "VEÍCULO GA130 - ALIMENTADOR (542 - BAIRRO NOVO B)"
        const val FAKE_DATE = "22/03/2018 08:17:51"
        const val FAKE_VALUE = -4.25
    }
}