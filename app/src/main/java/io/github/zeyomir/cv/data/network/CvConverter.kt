package io.github.zeyomir.cv.data.network

import io.github.zeyomir.cv.data.network.model.*
import io.github.zeyomir.cv.domain.entity.*
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.*

class CvConverter {
    fun convert(apiModel: ApiCvModel): Cv =
        with(apiModel) {
            Cv(
                convert(candidate),
                summary,
                keywords,
                experience.map(::convert).sortedByDescending { it.dateFrom },
                education.map(::convert).sortedByDescending { it.startYear },
                certificates.map(::convert).sortedByDescending { it.year },
                languages.map(::convert).sortedByDescending { it.level.percent },
                interests,
                links.map(::convert).sortedByDescending { it.type }
            )
        }

    private fun convert(apiModel: ApiCvCandidateModel): CvCandidate =
        with(apiModel) {
            CvCandidate(photo, "$firstName $lastName")
        }

    private fun convert(apiModel: ApiCvExperienceModel): CvExperience =
        with(apiModel) {
            CvExperience(
                companyName,
                roleName,
                dateFrom.let(::convertToDate),
                dateTo?.let(::convertToDate),
                description,
                techStack,
                companyLogo
            )
        }

    private fun convertToDate(isoDate: String): LocalDate =
        ZonedDateTime.parse(isoDate).toLocalDate()

    private fun convert(apiModel: ApiCvEducationnModel): CvEducation =
        with(apiModel) {
            CvEducation(institution, course, degree, startYear.toInt(), endYear?.toInt())
        }

    private fun convert(apiModel: ApiCvCertificateModel): CvCertificate =
        with(apiModel) {
            CvCertificate(issuer, name, year.toInt())
        }

    private fun convert(apiModel: ApiCvLanguageModel): CvLanguage =
        with(apiModel) {
            CvLanguage(language, convertLanguageLevel(level))
        }

    private fun convertLanguageLevel(apiModel: String): LanguageLevel =
        when (apiModel.toLowerCase(Locale.getDefault())) {
            "native" -> LanguageLevel.NATIVE
            "c2" -> LanguageLevel.C2
            "c1" -> LanguageLevel.C1
            "b2" -> LanguageLevel.B2
            "b1" -> LanguageLevel.B1
            "a2" -> LanguageLevel.A2
            "a1" -> LanguageLevel.A1
            else -> LanguageLevel.UNKNOWN
        }

    private fun convert(apiModel: ApiCvLinkModel): CvLink =
        with(apiModel) {
            CvLink(convertLinkType(type), address, name)
        }

    private fun convertLinkType(apiModel: String): LinkType =
        when (apiModel.toLowerCase(Locale.getDefault())) {
            "email" -> LinkType.EMAIL
            "social" -> LinkType.SOCIAL
            else -> LinkType.OTHER
        }
}
