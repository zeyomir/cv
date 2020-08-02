package io.github.zeyomir.cv.overview.items

import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CvLinkItemViewModelTest {
    private lateinit var sut: CvLinkItemViewModel

    private val action: () -> Unit = mockk()

    @BeforeEach
    fun setup() {
        every { action() } just Runs
        sut = CvLinkItemViewModel("", "", 0, action)
    }

    @Test
    fun `calls action in command`() {
        sut.command()

        verify { action() }
    }
}
