package io.github.zeyomir.cv.base

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DateFormatter(private val locale: Locale) {
    private val fullYearAndMonth: DateTimeFormatter by lazy {
        DateTimeFormatter.ofPattern("yyyy.MM", locale)
    }

    fun formatFullYearAndMonth(date: LocalDate): String = fullYearAndMonth.format(date)
}
