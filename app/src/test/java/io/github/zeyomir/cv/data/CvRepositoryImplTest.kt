package io.github.zeyomir.cv.data

import io.github.zeyomir.cv.TrampolineSchedulersProvider
import io.github.zeyomir.cv.data.cache.CvCache
import io.github.zeyomir.cv.data.network.CvConverter
import io.github.zeyomir.cv.data.network.CvService
import io.github.zeyomir.cv.data.network.model.ApiCvCandidateModel
import io.github.zeyomir.cv.data.network.model.ApiCvModel
import io.github.zeyomir.cv.domain.entity.Cv
import io.github.zeyomir.cv.domain.entity.CvCandidate
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CvRepositoryImplTest {
    private lateinit var sut: CvRepositoryImpl

    @MockK
    lateinit var service: CvService

    @MockK
    lateinit var converter: CvConverter

    @MockK
    lateinit var cache: CvCache

    private val testCv = Cv(
        CvCandidate(null, "John Doe"),
        "summary",
        emptyList(),
        emptyList(),
        emptyList(),
        emptyList(),
        emptyList(),
        emptyList(),
        emptyList()
    )

    private val apiCv = ApiCvModel(
        ApiCvCandidateModel(null, "John", "Doe"),
        "summary",
        emptyList(),
        emptyList(),
        emptyList(),
        emptyList(),
        emptyList(),
        emptyList(),
        emptyList()
    )

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)

        sut = CvRepositoryImpl(service, converter, cache, TrampolineSchedulersProvider())
    }

    @Test
    fun `if cache empty, returns nothing`() {
        every { cache.stream() } returns Observable.empty()

        val test = sut.getCv().test()

        test.assertNoValues()
    }

    @Test
    fun `returns value from cache`() {
        every { cache.stream() } returns Observable.just(testCv)

        val test = sut.getCv().test()

        test.assertValue(testCv)
    }

    @Test
    fun `refresh hits service, converts response and saves to cache`() {
        every { service.cv() } returns Single.just(apiCv)
        every { converter.convert(apiCv) } returns testCv
        every { cache.save(testCv) } just Runs

        val test = sut.refreshCv().test()

        test.assertComplete()
        verifySequence {
            service.cv()
            converter.convert(apiCv)
            cache.save(testCv)
        }
    }

    @Test
    fun `writes and reads using the same cache`() {
        every { service.cv() } returns Single.just(apiCv)
        every { converter.convert(apiCv) } returns testCv
        every { cache.save(testCv) } just Runs
        every { cache.stream() } returns Observable.empty()

        sut.getCv().test()
        sut.refreshCv().test()

        verify {
            cache.stream()
            cache.save(testCv)
        }
    }
}
