package io.github.zeyomir.cv.overview.education

import io.github.zeyomir.cv.domain.entity.Cv
import io.github.zeyomir.cv.domain.entity.CvCandidate
import io.github.zeyomir.cv.domain.entity.CvEducation
import io.github.zeyomir.cv.domain.repository.CvRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.reactivex.Observable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class StreamCvEducationUseCaseTest {

    @RelaxedMockK
    lateinit var repository: CvRepository

    private lateinit var sut: StreamCvEducationUseCase

    private val education = listOf(CvEducation("institution", "course", "degree", 2000, null))
    private val cv = Cv(CvCandidate(null, ""), "", emptyList(), emptyList(), education, emptyList(), emptyList(), emptyList(), emptyList())

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        sut = StreamCvEducationUseCase(repository)
    }

    @Test
    fun `returns education data of cv from repository`() {
        every { repository.getCv() } returns Observable.just(cv)

        val test = sut.execute().test()

        verify {
            repository.getCv()
        }
        test.assertValue(education)
    }
}
