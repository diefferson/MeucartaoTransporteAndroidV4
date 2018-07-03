package br.com.disapps.domain.model

import br.com.disapps.domain.mock.MockData
import org.junit.Test
import kotlin.test.assertEquals

class CityTest {

    lateinit var city: City

    @Test
    fun testCityCwb(){
        city = City.CWB
        assertEquals(MockData.CITY_CWB, city.toString())
    }

    @Test
    fun testCityMetropolitan(){
        city = City.MET
        assertEquals(MockData.CITY_METROPOLITAN, city.toString())
    }
}