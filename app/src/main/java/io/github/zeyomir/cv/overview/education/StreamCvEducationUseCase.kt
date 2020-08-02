package io.github.zeyomir.cv.overview.education

import io.github.zeyomir.cv.domain.entity.CvEducation
import io.github.zeyomir.cv.domain.repository.CvRepository
import io.reactivex.Observable

class StreamCvEducationUseCase(
    private val repository: CvRepository
) {
    fun execute(): Observable<List<CvEducation>> = repository.getCv().map { it.education }
}
