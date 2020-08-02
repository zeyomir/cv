package io.github.zeyomir.cv.data

import io.github.zeyomir.cv.base.SchedulersProvider
import io.github.zeyomir.cv.data.cache.CvCache
import io.github.zeyomir.cv.data.network.CvConverter
import io.github.zeyomir.cv.data.network.CvService
import io.github.zeyomir.cv.domain.entity.Cv
import io.github.zeyomir.cv.domain.repository.CvRepository
import io.reactivex.Completable
import io.reactivex.Observable

class CvRepositoryImpl(
    private val service: CvService,
    private val converter: CvConverter,
    private val cache: CvCache,
    private val schedulersProvider: SchedulersProvider
) : CvRepository {
    override fun refreshCv(): Completable =
        service
            .cv()
            .map(converter::convert)
            .doOnSuccess(cache::save)
            .subscribeOn(schedulersProvider.internetOperation())
            .ignoreElement()

    override fun getCv(): Observable<Cv> =
        cache
            .stream()
            .subscribeOn(schedulersProvider.internetOperation())
}
