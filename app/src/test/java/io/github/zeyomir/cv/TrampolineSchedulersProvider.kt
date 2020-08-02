package io.github.zeyomir.cv

import io.github.zeyomir.cv.base.SchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TrampolineSchedulersProvider : SchedulersProvider {
    override fun internetOperation(): Scheduler = Schedulers.trampoline()
    override fun mainThread(): Scheduler = Schedulers.trampoline()
}
