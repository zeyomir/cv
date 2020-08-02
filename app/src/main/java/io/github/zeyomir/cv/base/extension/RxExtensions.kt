package io.github.zeyomir.cv.base.extension

import androidx.databinding.ObservableBoolean
import io.reactivex.Completable

fun Completable.addLoading(showLoading: ObservableBoolean): Completable =
    doOnSubscribe { showLoading.set(true) }
        .doOnEvent { showLoading.set(false) }
        .doOnDispose { showLoading.set(false) }
