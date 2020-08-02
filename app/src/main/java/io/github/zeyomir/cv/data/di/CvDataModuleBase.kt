package io.github.zeyomir.cv.data.di

import dagger.Module
import dagger.Provides
import io.github.zeyomir.cv.base.SchedulersProvider
import io.github.zeyomir.cv.base.di.CvScope
import io.github.zeyomir.cv.data.CvRepositoryImpl
import io.github.zeyomir.cv.data.cache.CvCache
import io.github.zeyomir.cv.data.network.CvConverter
import io.github.zeyomir.cv.data.network.CvService
import io.github.zeyomir.cv.domain.repository.CvRepository
import retrofit2.Retrofit

@Module
open class CvDataModuleBase {

    @Provides
    @CvScope
    fun cvService(retrofit: Retrofit) = createCvService(retrofit)

    protected open fun createCvService(retrofit: Retrofit): CvService = retrofit.create(CvService::class.java)

    @Provides
    @CvScope
    fun cvConverter() = CvConverter()

    @Provides
    @CvScope
    fun cvCache() = CvCache()

    @Provides
    @CvScope
    fun cvRepository(
        service: CvService,
        converter: CvConverter,
        cache: CvCache,
        schedulersProvider: SchedulersProvider
    ): CvRepository =
        CvRepositoryImpl(service, converter, cache, schedulersProvider)
}
