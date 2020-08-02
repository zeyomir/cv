package io.github.zeyomir.cv.base

import io.reactivex.Observable
import io.reactivex.processors.BehaviorProcessor

abstract class Cache<T> {
    private var behaviorProcessor: BehaviorProcessor<T> = BehaviorProcessor.create()

    fun stream(): Observable<T> = behaviorProcessor.toObservable()

    fun save(model: T) = behaviorProcessor.onNext(model)

    fun clear() {
        behaviorProcessor = BehaviorProcessor.create()
    }
}
