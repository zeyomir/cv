package io.github.zeyomir.cv.base

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.*

internal class DateFormatterTest {
    lateinit var sut: DateFormatter

    @BeforeEach
    fun setup() {
        sut = DateFormatter(Locale.US)
    }

    @Test
    fun `formats full year then month with leading zero, with dot as separator`() {
        val date = LocalDate.of(2020, 8, 1)

        val formattedDate = sut.formatFullYearAndMonth(date)

        assertEquals("2020.08", formattedDate)
    }
}
