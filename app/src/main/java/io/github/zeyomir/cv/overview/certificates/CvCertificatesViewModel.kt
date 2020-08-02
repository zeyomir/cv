package io.github.zeyomir.cv.overview.certificates

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import io.github.zeyomir.cv.BR
import io.github.zeyomir.cv.R
import io.github.zeyomir.cv.base.SchedulersProvider
import io.github.zeyomir.cv.base.ui.BaseViewModel
import io.github.zeyomir.cv.di.CvActivityComponent
import io.github.zeyomir.cv.domain.entity.CvCertificate
import io.github.zeyomir.cv.overview.CvCertificateTextProvider
import io.github.zeyomir.cv.overview.items.CvCertificateItemViewModel
import me.tatarka.bindingcollectionadapter2.ItemBinding
import timber.log.Timber
import javax.inject.Inject

class CvCertificatesViewModel(
    val items: ObservableList<CvCertificateItemViewModel> = ObservableArrayList()
) : BaseViewModel() {

    @Inject
    lateinit var certificateTextProvider: CvCertificateTextProvider

    @Inject
    lateinit var streamCertificates: StreamCvCertificatesUseCase

    @Inject
    lateinit var schedulersProvider: SchedulersProvider

    val itemsBinding: ItemBinding<CvCertificateItemViewModel> = ItemBinding.of(BR.viewModel, R.layout.item_certificate)

    fun bindComponent(component: CvActivityComponent): CvCertificatesViewModel {
        component.inject(this)
        return this
    }

    override fun beforeStart() {
        super.beforeStart()
        disposeOnStop(
            streamCertificates.execute()
                .observeOn(schedulersProvider.mainThread())
                .subscribe(
                    ::handleData,
                    ::handleError
                )
        )
    }

    private fun handleData(data: List<CvCertificate>) {
        items.clear()
        items.addAll(data.map {
            CvCertificateItemViewModel(
                certificateTextProvider.getIssuedText(
                    it.issuer,
                    it.year
                ),
                it.name
            )
        })
    }

    private fun handleError(throwable: Throwable) {
        Timber.w(throwable)
    }
}
