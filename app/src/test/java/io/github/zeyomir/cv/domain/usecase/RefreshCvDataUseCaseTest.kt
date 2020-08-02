package io.github.zeyomir.cv.domain.usecase

import io.github.zeyomir.cv.domain.repository.CvRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.reactivex.Completable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class RefreshCvDataUseCaseTest {

    @RelaxedMockK
    lateinit var repository: CvRepository

    lateinit var sut: RefreshCvDataUseCase

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        sut = RefreshCvDataUseCase(repository)
    }

    @Test
    fun `calls refresh in repository`() {
        every { repository.refreshCv() } returns Completable.complete()

        sut.execute().test()

        verify {
            repository.refreshCv()
        }
    }
}
