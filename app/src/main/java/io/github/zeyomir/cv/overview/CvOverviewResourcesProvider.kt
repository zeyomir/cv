package io.github.zeyomir.cv.overview

import io.github.zeyomir.cv.domain.entity.LinkType
import java.time.LocalDate

class CvOverviewResourcesProvider(
    private val educationTextProvider: CvEducationTextProvider,
    private val experienceTextProvider: CvExperienceTextProvider,
    private val linkIconProvider: CvLinkIconProvider
) {
    fun getLinkIconFrom(type: LinkType): Int = linkIconProvider.from(type)
    fun getExperienceTechText(techStack: List<String>): String = experienceTextProvider.getTechText(techStack)
    fun getExperienceYearsText(dateFrom: LocalDate, dateTo: LocalDate?): String = experienceTextProvider.getYearsText(dateFrom, dateTo)
    fun getEducationDegreeAndCourseText(degree: String, course: String): String = educationTextProvider.getDegreeAndCourseText(degree, course)
    fun getEducationYearsText(startYear: Int, endYear: Int?): String = educationTextProvider.getYearsText(startYear, endYear)
}
