package io.github.zeyomir.cv.overview.items

import androidx.annotation.DrawableRes

class CvLinkItemViewModel(
    val name: String,
    val address: String,
    @DrawableRes val iconResource: Int,
    private val action: () -> Unit
) {
    fun command() {
        action()
    }
}
