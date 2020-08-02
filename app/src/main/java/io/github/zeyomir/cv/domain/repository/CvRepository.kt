package io.github.zeyomir.cv.domain.repository

import io.github.zeyomir.cv.domain.entity.Cv
import io.reactivex.Completable
import io.reactivex.Observable

interface CvRepository {
    fun refreshCv(): Completable
    fun getCv(): Observable<Cv>
}
