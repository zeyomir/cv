package io.github.zeyomir.cv.overview.education

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import io.github.zeyomir.cv.BR
import io.github.zeyomir.cv.R
import io.github.zeyomir.cv.base.SchedulersProvider
import io.github.zeyomir.cv.base.ui.BaseViewModel
import io.github.zeyomir.cv.di.CvActivityComponent
import io.github.zeyomir.cv.domain.entity.CvEducation
import io.github.zeyomir.cv.overview.CvEducationTextProvider
import io.github.zeyomir.cv.overview.items.CvEducationItemViewModel
import me.tatarka.bindingcollectionadapter2.ItemBinding
import timber.log.Timber
import javax.inject.Inject

class CvEducationViewModel(
    val items: ObservableList<CvEducationItemViewModel> = ObservableArrayList()
) : BaseViewModel() {

    @Inject
    lateinit var educationTextProvider: CvEducationTextProvider

    @Inject
    lateinit var streamEducation: StreamCvEducationUseCase

    @Inject
    lateinit var schedulersProvider: SchedulersProvider

    val itemsBinding: ItemBinding<CvEducationItemViewModel> = ItemBinding.of(BR.viewModel, R.layout.item_education)

    fun bindComponent(component: CvActivityComponent): CvEducationViewModel {
        component.inject(this)
        return this
    }

    override fun beforeStart() {
        super.beforeStart()
        disposeOnStop(
            streamEducation.execute()
                .observeOn(schedulersProvider.mainThread())
                .subscribe(
                    ::handleData,
                    ::handleError
                )
        )
    }

    private fun handleData(data: List<CvEducation>) {
        items.clear()
        val map = data.map {
            CvEducationItemViewModel(
                it.institution,
                educationTextProvider.getDegreeAndCourseText(it.degree, it.course),
                educationTextProvider.getYearsText(it.startYear, it.endYear)
            )
        }
        items.addAll(map)
    }

    private fun handleError(throwable: Throwable) {
        Timber.w(throwable)
    }
}
