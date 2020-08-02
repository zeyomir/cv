package io.github.zeyomir.cv.overview

import io.github.zeyomir.cv.domain.entity.Cv
import io.github.zeyomir.cv.domain.repository.CvRepository
import io.reactivex.Observable

class StreamCvOverviewUseCase(
    private val repository: CvRepository
) {
    fun execute(): Observable<Cv> = repository.getCv()
}
