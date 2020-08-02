package io.github.zeyomir.cv.overview

import androidx.annotation.DrawableRes
import io.github.zeyomir.cv.R
import io.github.zeyomir.cv.domain.entity.LinkType

class CvLinkIconProvider {
    @DrawableRes
    fun from(type: LinkType): Int =
        when (type) {
            LinkType.EMAIL -> R.drawable.ic_email
            LinkType.SOCIAL -> R.drawable.ic_person
            LinkType.OTHER -> R.drawable.ic_link
        }
}
