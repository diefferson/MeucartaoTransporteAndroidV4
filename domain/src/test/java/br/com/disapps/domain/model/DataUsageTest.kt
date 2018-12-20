package br.com.disapps.domain.model

import br.com.disapps.domain.mock.MockData
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

class DataUsageTest{

    private  lateinit var dataUsage: DataUsage

    @Before
    fun setup(){
        dataUsage = DataUsage(
                MockData.DATE_LONG_1,
                MockData.DATE_LONG_2,
                MockData.DATE_LONG_3,
                MockData.DATE_LONG_4,
                MockData.DATE_LONG_3,
                MockData.DATE_LONG_2
        )
    }


    @Test
    fun testDataUsageConstructorHappyCase(){
        Assertions.assertThat(dataUsage.dateUpdateLines).isEqualTo(MockData.DATE_LONG_1)
        Assertions.assertThat(dataUsage.dateUpdateSchedules).isEqualTo(MockData.DATE_LONG_2)
        Assertions.assertThat(dataUsage.dateUpdateCwbItineraries).isEqualTo(MockData.DATE_LONG_3)
        Assertions.assertThat(dataUsage.dateUpdateMetItineraries).isEqualTo(MockData.DATE_LONG_4)
        Assertions.assertThat(dataUsage.dateUpdateCwbShapes).isEqualTo(MockData.DATE_LONG_3)
        Assertions.assertThat(dataUsage.dateUpdateMetShapes).isEqualTo(MockData.DATE_LONG_2)
    }

}