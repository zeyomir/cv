package io.github.zeyomir.cv.overview

import android.content.Context
import io.github.zeyomir.cv.R

class CvEducationTextProvider(val context: Context) {

    fun getDegreeAndCourseText(degree: String, course: String): String =
        context.getString(R.string.degreeAndCourse, degree, course)

    fun getYearsText(startYear: Int, endYear: Int?): String =
        when (endYear) {
            null -> context.getString(R.string.tillNow, startYear.toString())
            else -> context.getString(R.string.till, startYear.toString(), endYear.toString())
        }
}
