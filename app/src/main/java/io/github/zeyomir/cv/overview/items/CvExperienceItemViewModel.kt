package io.github.zeyomir.cv.overview.items

sealed class BaseCvExperienceItemViewModel

class CvExperienceItemViewModel(
    val companyName: String,
    val role: String,
    val years: String,
    val description: String,
    val usedTech: String,
    val companyLogo: String?
) : BaseCvExperienceItemViewModel()

class EmptyCvExperienceItemViewModel : BaseCvExperienceItemViewModel()
