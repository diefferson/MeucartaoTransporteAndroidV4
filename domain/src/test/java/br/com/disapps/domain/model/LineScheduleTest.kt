package br.com.disapps.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class LineScheduleTest{

    private lateinit var lineSchedule: LineSchedule

    @Before
    fun setUp(){
        lineSchedule = LineSchedule(
                FAKE_LINE_CODE,
                FAKE_DAY,
                FAKE_BUS_STOP_NAME,
                FAKE_BUS_STOP_CODE,
                FAKE_SCHEDULES,
                FAKE_NEXT_SCHEDULES
        )
    }

    @Test
    fun testLineScheduleConstructorHappyCase(){
        assertThat(lineSchedule.lineCode).isEqualTo(FAKE_LINE_CODE)
        assertThat(lineSchedule.day).isEqualTo(FAKE_DAY)
        assertThat(lineSchedule.busStopName).isEqualTo(FAKE_BUS_STOP_NAME)
        assertThat(lineSchedule.busStopCode).isEqualTo(FAKE_BUS_STOP_CODE)
        assertThat(lineSchedule.schedules).isEqualTo(FAKE_SCHEDULES)
        assertThat(lineSchedule.nextSchedules).isEqualTo(FAKE_NEXT_SCHEDULES)
    }

    companion object {
        const val FAKE_LINE_CODE = "525"
        const val FAKE_DAY = 2
        const val FAKE_BUS_STOP_NAME = "BAIRRO XAXIM"
        const val FAKE_BUS_STOP_CODE = "150482"
        val FAKE_SCHEDULES = ArrayList<Schedule>()
        val FAKE_NEXT_SCHEDULES = ArrayList<Schedule>()
    }

}

