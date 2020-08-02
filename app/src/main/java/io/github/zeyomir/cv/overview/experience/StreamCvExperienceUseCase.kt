package io.github.zeyomir.cv.overview.experience

import io.github.zeyomir.cv.domain.entity.CvExperience
import io.github.zeyomir.cv.domain.repository.CvRepository
import io.reactivex.Observable

class StreamCvExperienceUseCase(
    private val repository: CvRepository
) {
    fun execute(): Observable<List<CvExperience>> = repository.getCv().map { it.experience }
}
