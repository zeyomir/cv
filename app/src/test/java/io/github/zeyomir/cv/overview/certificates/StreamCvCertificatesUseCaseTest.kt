package io.github.zeyomir.cv.overview.certificates

import io.github.zeyomir.cv.domain.entity.Cv
import io.github.zeyomir.cv.domain.entity.CvCandidate
import io.github.zeyomir.cv.domain.entity.CvCertificate
import io.github.zeyomir.cv.domain.repository.CvRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.reactivex.Observable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class StreamCvCertificatesUseCaseTest {

    @RelaxedMockK
    lateinit var repository: CvRepository

    private lateinit var sut: StreamCvCertificatesUseCase

    private val certificates = listOf(CvCertificate("issuer", "name", 2000))
    private val cv = Cv(CvCandidate(null, ""), "", emptyList(), emptyList(), emptyList(), certificates, emptyList(), emptyList(), emptyList())

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        sut = StreamCvCertificatesUseCase(repository)
    }

    @Test
    fun `returns certificates data of cv from repository`() {
        every { repository.getCv() } returns Observable.just(cv)

        val test = sut.execute().test()

        verify {
            repository.getCv()
        }
        test.assertValue(certificates)
    }
}
