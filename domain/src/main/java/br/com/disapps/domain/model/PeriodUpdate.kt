package br.com.disapps.domain.model

enum class PeriodUpdate(val period : String){
    WEEKLY("semanal"),
    MONTHLY("mensal"),
    YEARLY("anual"),
    MANUAL("manual");

    override fun toString(): String {
        return period
    }

    companion object {
        fun getPeriodUpdate(position: Int): PeriodUpdate {
            return when (position) {
                0 -> PeriodUpdate.WEEKLY
                1 -> PeriodUpdate.MONTHLY
                2 -> PeriodUpdate.YEARLY
                3 -> PeriodUpdate.MANUAL
                else -> PeriodUpdate.WEEKLY
            }
        }
    }
}