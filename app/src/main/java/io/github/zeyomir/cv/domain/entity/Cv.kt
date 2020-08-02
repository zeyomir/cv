package io.github.zeyomir.cv.domain.entity

import java.time.LocalDate

data class Cv(
    val candidate: CvCandidate,
    val summary: String,
    val keywords: List<String>,
    val experience: List<CvExperience>,
    val education: List<CvEducation>,
    val certificates: List<CvCertificate>,
    val languages: List<CvLanguage>,
    val interests: List<String>,
    val links: List<CvLink>
)

data class CvCandidate(
    val photo: String?,
    val name: String
)

data class CvExperience(
    val companyName: String,
    val roleName: String,
    val dateFrom: LocalDate,
    val dateTo: LocalDate?,
    val description: String,
    val techStack: List<String>,
    val companyLogo: String?
)

data class CvEducation(
    val institution: String,
    val course: String,
    val degree: String,
    val startYear: Int,
    val endYear: Int?
)

data class CvCertificate(
    val issuer: String,
    val name: String,
    val year: Int
)

data class CvLanguage(
    val language: String,
    val level: LanguageLevel
)

enum class LanguageLevel(val percent: Int) {
    NATIVE(100), C2(90), C1(80), B2(60), B1(40), A2(20), A1(10), UNKNOWN(0)
}

data class CvLink(
    val type: LinkType,
    val address: String,
    val name: String
)

enum class LinkType {
    EMAIL, SOCIAL, OTHER
}
