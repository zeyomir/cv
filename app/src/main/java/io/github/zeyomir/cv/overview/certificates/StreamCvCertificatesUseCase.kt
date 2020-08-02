package io.github.zeyomir.cv.overview.certificates

import io.github.zeyomir.cv.domain.entity.CvCertificate
import io.github.zeyomir.cv.domain.repository.CvRepository
import io.reactivex.Observable

class StreamCvCertificatesUseCase(
    private val repository: CvRepository
) {
    fun execute(): Observable<List<CvCertificate>> = repository.getCv().map { it.certificates }
}
