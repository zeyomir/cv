package io.github.zeyomir.cv.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiCvModel(
    val candidate: ApiCvCandidateModel,
    val summary: String,
    val keywords: List<String>,
    val experience: List<ApiCvExperienceModel>,
    val education: List<ApiCvEducationnModel>,
    val certificates: List<ApiCvCertificateModel>,
    val languages: List<ApiCvLanguageModel>,
    val interests: List<String>,
    val links: List<ApiCvLinkModel>
)

@JsonClass(generateAdapter = true)
data class ApiCvCandidateModel(
    val photo: String?,
    val firstName: String,
    val lastName: String
)

@JsonClass(generateAdapter = true)
data class ApiCvExperienceModel(
    val companyName: String,
    val roleName: String,
    val dateFrom: String,
    val dateTo: String?,
    val description: String,
    val techStack: List<String>,
    val companyLogo: String?
)

@JsonClass(generateAdapter = true)
data class ApiCvEducationnModel(
    val institution: String,
    val course: String,
    val degree: String,
    val startYear: String,
    val endYear: String?
)

@JsonClass(generateAdapter = true)
data class ApiCvCertificateModel(
    val issuer: String,
    val name: String,
    val year: String
)

@JsonClass(generateAdapter = true)
data class ApiCvLanguageModel(
    val language: String,
    val level: String
)

@JsonClass(generateAdapter = true)
data class ApiCvLinkModel(
    val type: String,
    val address: String,
    val name: String
)
