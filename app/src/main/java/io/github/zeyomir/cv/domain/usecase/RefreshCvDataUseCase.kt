package io.github.zeyomir.cv.domain.usecase

import io.github.zeyomir.cv.domain.repository.CvRepository
import io.reactivex.Completable

class RefreshCvDataUseCase(
    private val repository: CvRepository
) {
    fun execute(): Completable = repository.refreshCv()
}
