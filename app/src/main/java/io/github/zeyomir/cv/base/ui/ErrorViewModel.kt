package io.github.zeyomir.cv.base.ui

import androidx.databinding.ObservableBoolean

class ErrorViewModel(val show: ObservableBoolean, private val action: () -> Unit) {
    fun command() {
        action()
    }
}
