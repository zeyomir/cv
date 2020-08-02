package io.github.zeyomir.cv.overview.experience

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import io.github.zeyomir.cv.BR
import io.github.zeyomir.cv.R
import io.github.zeyomir.cv.base.SchedulersProvider
import io.github.zeyomir.cv.base.ui.BaseViewModel
import io.github.zeyomir.cv.di.CvActivityComponent
import io.github.zeyomir.cv.domain.entity.CvExperience
import io.github.zeyomir.cv.overview.CvExperienceTextProvider
import io.github.zeyomir.cv.overview.items.CvExperienceItemViewModel
import me.tatarka.bindingcollectionadapter2.ItemBinding
import timber.log.Timber
import javax.inject.Inject

class CvExperienceViewModel(
    val items: ObservableList<CvExperienceItemViewModel> = ObservableArrayList()
) : BaseViewModel() {

    @Inject
    lateinit var experienceTextProvider: CvExperienceTextProvider

    @Inject
    lateinit var streamExperience: StreamCvExperienceUseCase

    @Inject
    lateinit var schedulersProvider: SchedulersProvider

    val itemsBinding: ItemBinding<CvExperienceItemViewModel> = ItemBinding.of(BR.viewModel, R.layout.item_experience)

    fun bindComponent(component: CvActivityComponent): CvExperienceViewModel {
        component.inject(this)
        return this
    }

    override fun beforeStart() {
        super.beforeStart()
        disposeOnStop(
            streamExperience.execute()
                .observeOn(schedulersProvider.mainThread())
                .subscribe(
                    ::handleData,
                    ::handleError
                )
        )
    }

    private fun handleData(data: List<CvExperience>) {
        items.clear()
        items.addAll(data.map {
            CvExperienceItemViewModel(
                it.companyName,
                it.roleName,
                experienceTextProvider.getYearsText(it.dateFrom, it.dateTo),
                it.description,
                experienceTextProvider.getTechText(it.techStack),
                it.companyLogo
            )
        })
    }

    private fun handleError(throwable: Throwable) {
        Timber.w(throwable)
    }
}
