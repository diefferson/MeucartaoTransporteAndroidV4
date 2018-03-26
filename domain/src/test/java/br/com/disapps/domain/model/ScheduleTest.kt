package br.com.disapps.domain.model

import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

class ScheduleTest{

    private lateinit var schedule: Schedule

    @Before
    fun setUp(){
        schedule = Schedule(
                FAKE_SCHEDULES_TABLE,
                FAKE_ADAPT,
                FAKE_TIME
        )
    }

    @Test
    fun testScheduleConstructorHappyCase(){
        Assertions.assertThat(schedule.schedulesTable).isEqualTo(FAKE_SCHEDULES_TABLE)
        Assertions.assertThat(schedule.adapt).isEqualTo(FAKE_ADAPT)
        Assertions.assertThat(schedule.time).isEqualTo(FAKE_TIME)
    }

    companion object {
        const val FAKE_SCHEDULES_TABLE = "1-2"
        const val FAKE_ADAPT = "ELEVADOR"
        const val FAKE_TIME = "19:20"
    }
}
