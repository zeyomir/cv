package io.github.zeyomir.cv.base

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulersProvider {
    fun internetOperation(): Scheduler
    fun mainThread(): Scheduler
}

class SchedulersProviderImpl : SchedulersProvider {
    override fun internetOperation(): Scheduler = Schedulers.io()

    override fun mainThread(): Scheduler = AndroidSchedulers.mainThread()
}
