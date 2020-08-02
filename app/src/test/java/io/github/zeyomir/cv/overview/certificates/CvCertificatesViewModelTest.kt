package io.github.zeyomir.cv.overview.certificates

import androidx.databinding.ObservableList
import io.github.zeyomir.cv.TrampolineSchedulersProvider
import io.github.zeyomir.cv.domain.entity.CvCertificate
import io.github.zeyomir.cv.overview.CvCertificateTextProvider
import io.github.zeyomir.cv.overview.items.CvCertificateItemViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verifySequence
import io.reactivex.Observable
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class CvCertificatesViewModelTest {

    @RelaxedMockK
    lateinit var items: ObservableList<CvCertificateItemViewModel>

    @MockK
    lateinit var certificatesTextProvider: CvCertificateTextProvider

    @MockK
    lateinit var streamCertificates: StreamCvCertificatesUseCase

    private lateinit var sut: CvCertificatesViewModel

    private val certificates = listOf(
        CvCertificate("issuer1", "name1", 2001),
        CvCertificate("issuer2", "name2", 2002)
    )

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        sut = CvCertificatesViewModel(items)
        sut.certificateTextProvider = certificatesTextProvider
        every { certificatesTextProvider.getIssuedText(any(), any()) } returns mockk()
        sut.streamCertificates = streamCertificates
        sut.schedulersProvider = TrampolineSchedulersProvider()
    }

    @Test
    fun `updates items with retrieved data`() {
        every { streamCertificates.execute() } returns Observable.just(certificates)

        sut.beforeStart()

        verifySequence {
            items.clear()
            items.addAll(withArg {
                assertAll(
                    { assertEquals(2, it.size) },
                    { assertEquals(certificates[0].name, (it as List)[0].name) },
                    { assertEquals(certificates[1].name, (it as List)[1].name) }
                )
            })
        }
    }
}
