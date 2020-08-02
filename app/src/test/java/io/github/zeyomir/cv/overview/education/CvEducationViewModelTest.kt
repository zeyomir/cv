package io.github.zeyomir.cv.overview.education

import androidx.databinding.ObservableList
import io.github.zeyomir.cv.TrampolineSchedulersProvider
import io.github.zeyomir.cv.domain.entity.CvEducation
import io.github.zeyomir.cv.overview.CvEducationTextProvider
import io.github.zeyomir.cv.overview.items.CvEducationItemViewModel
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

internal class CvEducationViewModelTest {

    @RelaxedMockK
    lateinit var items: ObservableList<CvEducationItemViewModel>

    @MockK
    lateinit var educationTextProvider: CvEducationTextProvider

    @MockK
    lateinit var streamEducation: StreamCvEducationUseCase

    private lateinit var sut: CvEducationViewModel

    private val education = listOf(
        CvEducation("institution1", "course1", "degree1", 2000, null),
        CvEducation("institution2", "course2", "degree2", 1990, 2000)
    )

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        sut = CvEducationViewModel(items)
        sut.educationTextProvider = educationTextProvider
        every { educationTextProvider.getDegreeAndCourseText(any(), any()) } returns ""
        every { educationTextProvider.getYearsText(any(), any()) } returns ""
        sut.streamEducation = streamEducation
        sut.schedulersProvider = TrampolineSchedulersProvider()
    }

    @Test
    fun `updates items with retrieved data`() {
        every { streamEducation.execute() } returns Observable.just(education)

        sut.beforeStart()

        verifySequence {
            items.clear()
            items.addAll(withArg {
                assertAll(
                    { assertEquals(2, it.size) },
                    { assertEquals(education[0].institution, (it as List)[0].institution) },
                    { assertEquals(education[1].institution, (it as List)[1].institution) }
                )
            })
        }
    }
}
