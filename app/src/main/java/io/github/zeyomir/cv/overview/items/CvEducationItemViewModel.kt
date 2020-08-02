package io.github.zeyomir.cv.overview.items

sealed class BaseCvEducationItemViewModel

class CvEducationItemViewModel(
    val institution: String,
    val degreeAndCourse: String,
    val years: String
) : BaseCvEducationItemViewModel()

class EmptyCvEducationItemViewModel : BaseCvEducationItemViewModel()
