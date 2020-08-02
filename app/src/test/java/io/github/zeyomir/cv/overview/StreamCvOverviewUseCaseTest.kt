package io.github.zeyomir.cv.overview

import io.github.zeyomir.cv.domain.repository.CvRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.reactivex.Observable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class StreamCvOverviewUseCaseTest {

    @RelaxedMockK
    lateinit var repository: CvRepository

    private lateinit var sut: StreamCvOverviewUseCase

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        sut = StreamCvOverviewUseCase(repository)
    }

    @Test
    fun `gets cv data from repository`() {
        every { repository.getCv() } returns Observable.empty()

        sut.execute().test()

        verify {
            repository.getCv()
        }
    }
}
