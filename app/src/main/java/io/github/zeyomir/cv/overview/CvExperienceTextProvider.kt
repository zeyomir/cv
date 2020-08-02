package io.github.zeyomir.cv.overview

import android.content.Context
import io.github.zeyomir.cv.R
import io.github.zeyomir.cv.base.DateFormatter
import java.time.LocalDate

class CvExperienceTextProvider(
    private val context: Context,
    private val dateFormatter: DateFormatter
) {
    fun getYearsText(startDate: LocalDate, endDate: LocalDate?): String =
        when (endDate) {
            null -> context.getString(R.string.tillNow, dateFormatter.formatFullYearAndMonth(startDate))
            else -> context.getString(R.string.till, dateFormatter.formatFullYearAndMonth(startDate), dateFormatter.formatFullYearAndMonth(endDate))
        }

    fun getTechText(tech: List<String>): String =
        context.getString(R.string.technologyUsed, tech.joinToString(", "))
}
