package io.github.zeyomir.cv.overview.experience

import io.github.zeyomir.cv.domain.entity.Cv
import io.github.zeyomir.cv.domain.entity.CvCandidate
import io.github.zeyomir.cv.domain.entity.CvExperience
import io.github.zeyomir.cv.domain.repository.CvRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.reactivex.Observable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class StreamCvExperienceUseCaseTest {

    @RelaxedMockK
    lateinit var repository: CvRepository

    private lateinit var sut: StreamCvExperienceUseCase

    private val experience = listOf(CvExperience("company1", "role1", LocalDate.now(), null, "desc1", emptyList(), null))
    private val cv = Cv(CvCandidate(null, ""), "", emptyList(), experience, emptyList(), emptyList(), emptyList(), emptyList(), emptyList())

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        sut = StreamCvExperienceUseCase(repository)
    }

    @Test
    fun `returns experience data of cv from repository`() {
        every { repository.getCv() } returns Observable.just(cv)

        val test = sut.execute().test()

        verify {
            repository.getCv()
        }
        test.assertValue(experience)
    }
}
