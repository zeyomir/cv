package io.github.zeyomir.cv.overview.experience

import androidx.databinding.ObservableList
import io.github.zeyomir.cv.TrampolineSchedulersProvider
import io.github.zeyomir.cv.domain.entity.CvExperience
import io.github.zeyomir.cv.overview.CvExperienceTextProvider
import io.github.zeyomir.cv.overview.items.CvExperienceItemViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verifySequence
import io.reactivex.Observable
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.time.LocalDate

internal class CvExperienceViewModelTest {

    @RelaxedMockK
    lateinit var items: ObservableList<CvExperienceItemViewModel>

    @MockK
    lateinit var experienceTextProvider: CvExperienceTextProvider

    @MockK
    lateinit var streamExperience: StreamCvExperienceUseCase

    private lateinit var sut: CvExperienceViewModel

    private val experience = listOf(
        CvExperience("company1", "role1", LocalDate.now(), null, "desc1", emptyList(), null),
        CvExperience("company2", "role2", LocalDate.now(), null, "desc2", emptyList(), null)
    )

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        sut = CvExperienceViewModel(items)
        sut.experienceTextProvider = experienceTextProvider
        every { experienceTextProvider.getTechText(any()) } returns ""
        every { experienceTextProvider.getYearsText(any(), any()) } returns ""
        sut.streamExperience = streamExperience
        sut.schedulersProvider = TrampolineSchedulersProvider()
    }

    @Test
    fun `updates items with retrieved data`() {
        every { streamExperience.execute() } returns Observable.just(experience)

        sut.beforeStart()

        verifySequence {
            items.clear()
            items.addAll(withArg {
                assertAll(
                    { assertEquals(2, it.size) },
                    { assertEquals(experience[0].companyName, (it as List)[0].companyName) },
                    { assertEquals(experience[0].roleName, (it as List)[0].role) },
                    { assertEquals(experience[1].companyName, (it as List)[1].companyName) },
                    { assertEquals(experience[1].roleName, (it as List)[1].role) }
                )
            })
        }
    }
}


