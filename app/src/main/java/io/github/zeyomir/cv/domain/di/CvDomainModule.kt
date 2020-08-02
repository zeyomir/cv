package io.github.zeyomir.cv.domain.di

import dagger.Module
import dagger.Provides
import io.github.zeyomir.cv.base.di.CvScope
import io.github.zeyomir.cv.domain.repository.CvRepository
import io.github.zeyomir.cv.domain.usecase.RefreshCvDataUseCase
import io.github.zeyomir.cv.overview.StreamCvOverviewUseCase
import io.github.zeyomir.cv.overview.certificates.StreamCvCertificatesUseCase
import io.github.zeyomir.cv.overview.education.StreamCvEducationUseCase
import io.github.zeyomir.cv.overview.experience.StreamCvExperienceUseCase

@Module
class CvDomainModule {

    @Provides
    @CvScope
    fun refreshDataUseCase(repository: CvRepository) = RefreshCvDataUseCase(repository)

    @Provides
    @CvScope
    fun streamCvOverviewUseCase(repository: CvRepository) = StreamCvOverviewUseCase(repository)

    @Provides
    @CvScope
    fun streamCvCertificatesUseCase(repository: CvRepository) = StreamCvCertificatesUseCase(repository)

    @Provides
    @CvScope
    fun streamCvEducationUseCase(repository: CvRepository) = StreamCvEducationUseCase(repository)

    @Provides
    @CvScope
    fun streamCvExperienceUseCase(repository: CvRepository) = StreamCvExperienceUseCase(repository)
}
