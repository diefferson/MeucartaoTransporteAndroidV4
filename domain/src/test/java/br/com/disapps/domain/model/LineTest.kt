package br.com.disapps.domain.model

import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

class LineTest{

    private lateinit var line: Line

    @Before
    fun setUp(){
        line = Line(
                FAKE_CODE,
                FAKE_NAME,
                FAKE_CATEGORY,
                FAKE_COLOR,
                FAKE_FAVORITE
        )
    }

    @Test
    fun testLineConstructorHappyCase(){
        Assertions.assertThat(line.code).isEqualTo(FAKE_CODE)
        Assertions.assertThat(line.name).isEqualTo(FAKE_NAME)
        Assertions.assertThat(line.category).isEqualTo(FAKE_CATEGORY)
        Assertions.assertThat(line.color).isEqualTo(FAKE_COLOR)
        Assertions.assertThat(line.favorite).isEqualTo(FAKE_FAVORITE)
    }

    companion object {
        const val FAKE_CODE = "040"
        const val FAKE_NAME = "Interbairros IV"
        const val FAKE_CATEGORY = "Interbairros"
        const val FAKE_COLOR = "green"
        const val FAKE_FAVORITE = 0
    }
}
