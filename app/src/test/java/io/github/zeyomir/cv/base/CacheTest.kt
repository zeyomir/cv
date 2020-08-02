package io.github.zeyomir.cv.base

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CacheTest {

    private val testString1 = "value1"
    private val testString2 = "value2"
    private lateinit var sut: Cache<String>

    @BeforeEach
    fun setup() {
        sut = object : Cache<String>() {}
    }

    @Test
    fun `is empty at first`() {
        val test = sut.stream().test()
        test.assertNoValues()
    }

    @Test
    fun `can save and retrieve a value`() {
        sut.save(testString1)
        val test = sut.stream().test()
        test.assertValue(testString1)
    }

    @Test
    fun `retrieves most recent value`() {
        sut.save(testString1)
        sut.save(testString2)
        val test = sut.stream().test()
        test.assertValue(testString2)
    }

    @Test
    fun `can be cleared`() {
        sut.save(testString1)
        sut.clear()
        val test = sut.stream().test()
        test.assertNoValues()
    }

    @Test
    fun `updates with new value`() {
        sut.save(testString1)

        val test = sut.stream().test()
        test.assertValueCount(1)
        test.assertValueAt(0, testString1)

        sut.save(testString2)
        test.assertValueCount(2)
        test.assertValueAt(1, testString2)
    }
}
