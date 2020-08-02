package io.github.zeyomir.cv.overview

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.databinding.ObservableList
import io.github.zeyomir.cv.CvNavigator
import io.github.zeyomir.cv.TrampolineSchedulersProvider
import io.github.zeyomir.cv.base.extension.ObservableString
import io.github.zeyomir.cv.domain.entity.*
import io.github.zeyomir.cv.domain.usecase.RefreshCvDataUseCase
import io.github.zeyomir.cv.overview.items.*
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.mockk.verifySequence
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import java.time.LocalDate

internal class CvOverviewViewModelTest {
    @RelaxedMockK
    lateinit var showError: ObservableBoolean

    @RelaxedMockK
    lateinit var showLoading: ObservableBoolean

    @RelaxedMockK
    lateinit var photoUrl: ObservableString

    @RelaxedMockK
    lateinit var name: ObservableString

    @RelaxedMockK
    lateinit var summary: ObservableString

    @RelaxedMockK
    lateinit var keywords: ObservableList<String>

    @RelaxedMockK
    lateinit var interests: ObservableList<String>

    @RelaxedMockK
    lateinit var languages: ObservableList<CvLanguageItemViewModel>

    @RelaxedMockK
    lateinit var certificates: ObservableInt

    @RelaxedMockK
    lateinit var links: ObservableList<CvLinkItemViewModel>

    @RelaxedMockK
    lateinit var experience: ObservableList<BaseCvExperienceItemViewModel>

    @RelaxedMockK
    lateinit var moreExperienceAvailable: ObservableBoolean

    @RelaxedMockK
    lateinit var education: ObservableList<BaseCvEducationItemViewModel>

    @RelaxedMockK
    lateinit var moreEducationAvailable: ObservableBoolean

    @RelaxedMockK
    lateinit var navigator: CvNavigator

    @RelaxedMockK
    lateinit var overviewResourcesProvider: CvOverviewResourcesProvider

    @MockK
    lateinit var refreshData: RefreshCvDataUseCase

    @MockK
    lateinit var streamData: StreamCvOverviewUseCase

    private lateinit var sut: CvOverviewViewModel

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        sut = CvOverviewViewModel(
            showError,
            showLoading,
            photoUrl,
            name,
            summary,
            keywords,
            interests,
            languages,
            certificates,
            links,
            experience,
            moreExperienceAvailable,
            education,
            moreEducationAvailable
        )
        sut.navigator = navigator
        sut.overviewResourcesProvider = overviewResourcesProvider
        sut.refreshData = refreshData
        sut.streamData = streamData
        sut.schedulersProvider = TrampolineSchedulersProvider()
    }

    @Test
    fun `shows error when data cannot be retrieved`() {
        every { refreshData.execute() } returns Completable.error(RuntimeException())
        every { streamData.execute() } returns Observable.empty<Cv>()

        sut.beforeCreate()

        verify {
            showError.set(true)
        }
    }

    @Test
    fun `when refreshing, hides error and shows and hides loader`() {
        every { refreshData.execute() } returns Completable.complete()
        every { streamData.execute() } returns Observable.empty<Cv>()

        sut.beforeCreate()

        verifySequence {
            showError.set(false)
            showLoading.set(true)
            showLoading.set(false)
        }
    }

    @Nested
    @DisplayName("displaying data")
    inner class CvOverviewViewModelDataDisplayTest {
        @Test
        fun `sets basic info correctly`() {
            val candidate = CvCandidate("photo url", "First Last")
            val summaryText = "description"
            val cv = Cv(candidate, summaryText, emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList())
            every { refreshData.execute() } returns Completable.complete()
            every { streamData.execute() } returns Observable.just(cv)

            sut.beforeCreate()

            verify {
                photoUrl.set(candidate.photo)
                name.set(candidate.name)
                summary.set(summaryText)
            }
        }

        @Test
        fun `sets keywords correctly`() {
            val cvKeywords = listOf("k1", "k2", "k3")
            val cv = Cv(CvCandidate("", ""), "", cvKeywords, emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList())
            every { refreshData.execute() } returns Completable.complete()
            every { streamData.execute() } returns Observable.just(cv)

            sut.beforeCreate()

            verifySequence {
                keywords.clear()
                keywords.addAll(cvKeywords)
            }
        }

        @Nested
        @DisplayName("sets experience correctly")
        inner class CvOverviewViewModelExperienceDisplayTest {
            @Test
            fun `shows 'empty' if empty experience, hides 'more' cta`() {
                val cvExperience = emptyList<CvExperience>()
                val cv = Cv(CvCandidate("", ""), "", emptyList(), cvExperience, emptyList(), emptyList(), emptyList(), emptyList(), emptyList())
                every { refreshData.execute() } returns Completable.complete()
                every { streamData.execute() } returns Observable.just(cv)

                sut.beforeCreate()

                verifySequence {
                    experience.clear()
                    experience.add(withArg {
                        assertTrue(it is EmptyCvExperienceItemViewModel)
                    })
                    moreExperienceAvailable.set(false)
                }
            }

            @Test
            fun `if only one element available, shows it and hides 'more' cta`() {
                val cvExperience = listOf(CvExperience("company1", "role1", LocalDate.now(), null, "desc1", emptyList(), null))
                val cv = Cv(CvCandidate("", ""), "", emptyList(), cvExperience, emptyList(), emptyList(), emptyList(), emptyList(), emptyList())
                every { refreshData.execute() } returns Completable.complete()
                every { streamData.execute() } returns Observable.just(cv)

                sut.beforeCreate()

                verifySequence {
                    experience.clear()
                    experience.add(withArg {
                        assertAll(
                            { assertTrue(it is CvExperienceItemViewModel) },
                            { assertEquals(cvExperience[0].companyName, (it as CvExperienceItemViewModel).companyName) }
                        )
                    })
                    moreExperienceAvailable.set(false)
                }
            }

            @Test
            fun `if more elements available, shows first and 'more' cta`() {
                val cvExperience = listOf(
                    CvExperience("company1", "role1", LocalDate.now(), null, "desc1", emptyList(), null),
                    CvExperience("company2", "role2", LocalDate.now(), null, "desc2", emptyList(), null)
                )
                val cv = Cv(CvCandidate("", ""), "", emptyList(), cvExperience, emptyList(), emptyList(), emptyList(), emptyList(), emptyList())
                every { refreshData.execute() } returns Completable.complete()
                every { streamData.execute() } returns Observable.just(cv)

                sut.beforeCreate()

                verifySequence {
                    experience.clear()
                    experience.add(withArg {
                        assertAll(
                            { assertTrue(it is CvExperienceItemViewModel) },
                            { assertEquals(cvExperience[0].companyName, (it as CvExperienceItemViewModel).companyName) }
                        )
                    })
                    moreExperienceAvailable.set(true)
                }
            }
        }

        @Nested
        @DisplayName("sets education correctly")
        inner class CvOverviewViewModelEducationDisplayTest {
            @Test
            fun `shows 'empty' if empty education, hides 'more' cta`() {
                val cvEducation = emptyList<CvEducation>()
                val cv = Cv(CvCandidate("", ""), "", emptyList(), emptyList(), cvEducation, emptyList(), emptyList(), emptyList(), emptyList())
                every { refreshData.execute() } returns Completable.complete()
                every { streamData.execute() } returns Observable.just(cv)

                sut.beforeCreate()

                verifySequence {
                    education.clear()
                    education.add(withArg {
                        assertTrue(it is EmptyCvEducationItemViewModel)
                    })
                    moreEducationAvailable.set(false)
                }
            }

            @Test
            fun `if only one element available, shows it and hides 'more' cta`() {
                val cvEducation = listOf(CvEducation("institution1", "course1", "degree1", 2000, null))
                val cv = Cv(CvCandidate("", ""), "", emptyList(), emptyList(), cvEducation, emptyList(), emptyList(), emptyList(), emptyList())
                every { refreshData.execute() } returns Completable.complete()
                every { streamData.execute() } returns Observable.just(cv)

                sut.beforeCreate()

                verifySequence {
                    education.clear()
                    education.add(withArg {
                        assertAll(
                            { assertTrue(it is CvEducationItemViewModel) },
                            { assertEquals(cvEducation[0].institution, (it as CvEducationItemViewModel).institution) }
                        )
                    })
                    moreEducationAvailable.set(false)
                }
            }

            @Test
            fun `if more elements available, shows first and 'more' cta`() {
                val cvEducation = listOf(
                    CvEducation("institution1", "course1", "degree1", 2000, null),
                    CvEducation("institution2", "course2", "degree2", 1901, 1902)
                )
                val cv = Cv(CvCandidate("", ""), "", emptyList(), emptyList(), cvEducation, emptyList(), emptyList(), emptyList(), emptyList())
                every { refreshData.execute() } returns Completable.complete()
                every { streamData.execute() } returns Observable.just(cv)

                sut.beforeCreate()

                verifySequence {
                    education.clear()
                    education.add(withArg {
                        assertAll(
                            { assertTrue(it is CvEducationItemViewModel) },
                            { assertEquals(cvEducation[0].institution, (it as CvEducationItemViewModel).institution) }
                        )
                    })
                    moreEducationAvailable.set(true)
                }
            }
        }

        @Nested
        @DisplayName("shows certificates number correctly")
        inner class CvOverviewViewModelCertificatesDisplayTest {

            @Test
            fun `when no certificates, sets it to 0`() {
                val cvCertificates = emptyList<CvCertificate>()
                val cv = Cv(CvCandidate("", ""), "", emptyList(), emptyList(), emptyList(), cvCertificates, emptyList(), emptyList(), emptyList())
                every { refreshData.execute() } returns Completable.complete()
                every { streamData.execute() } returns Observable.just(cv)

                sut.beforeCreate()

                verify {
                    certificates.set(0)
                }
            }

            @Test
            fun `when there are certificates, sets it to certificates count`() {
                val cvCertificates = listOf(
                    CvCertificate("issuer1", "name1", 2000),
                    CvCertificate("issuer2", "name2", 2001),
                    CvCertificate("issuer3", "name3", 2002)
                )
                val cv = Cv(CvCandidate("", ""), "", emptyList(), emptyList(), emptyList(), cvCertificates, emptyList(), emptyList(), emptyList())
                every { refreshData.execute() } returns Completable.complete()
                every { streamData.execute() } returns Observable.just(cv)

                sut.beforeCreate()

                verify {
                    certificates.set(3)
                }
            }
        }

        @Test
        fun `sets languages correctly`() {
            val cvLanguages = listOf(
                CvLanguage("language1", LanguageLevel.NATIVE),
                CvLanguage("language2", LanguageLevel.C1),
                CvLanguage("language3", LanguageLevel.A2)
            )
            val cv = Cv(CvCandidate("", ""), "", emptyList(), emptyList(), emptyList(), emptyList(), cvLanguages, emptyList(), emptyList())
            every { refreshData.execute() } returns Completable.complete()
            every { streamData.execute() } returns Observable.just(cv)

            sut.beforeCreate()

            verifySequence {
                languages.clear()
                languages.addAll(withArg {
                    val list = it as List
                    assertAll(
                        { assertEquals(3, list.size) },
                        { assertEquals(cvLanguages[0].language, list[0].language) },
                        { assertEquals(cvLanguages[0].level.percent, list[0].level) },
                        { assertEquals(cvLanguages[1].language, list[1].language) },
                        { assertEquals(cvLanguages[1].level.percent, list[1].level) },
                        { assertEquals(cvLanguages[2].language, list[2].language) },
                        { assertEquals(cvLanguages[2].level.percent, list[2].level) }
                    )
                })
            }
        }

        @Test
        fun `sets interests correctly`() {
            val cvInterests = listOf("i1", "i2", "i3")
            val cv = Cv(CvCandidate("", ""), "", emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), cvInterests, emptyList())
            every { refreshData.execute() } returns Completable.complete()
            every { streamData.execute() } returns Observable.just(cv)

            sut.beforeCreate()

            verifySequence {
                interests.clear()
                interests.addAll(cvInterests)
            }
        }

        @Test
        fun `sets links correctly`() {
            val cvLinks = listOf(
                CvLink(LinkType.EMAIL, "a@b.cc", "email"),
                CvLink(LinkType.SOCIAL, "https://l.in", "linkedin"),
                CvLink(LinkType.OTHER, "http://b.com", "blog")
            )
            val cv = Cv(CvCandidate("", ""), "", emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), cvLinks)
            every { refreshData.execute() } returns Completable.complete()
            every { streamData.execute() } returns Observable.just(cv)

            sut.beforeCreate()

            verifySequence {
                links.clear()
                links.addAll(withArg {
                    val list = it as List
                    assertAll(
                        { assertEquals(3, list.size) },
                        { assertEquals(cvLinks[0].name, list[0].name) },
                        { assertEquals(cvLinks[0].address, list[0].address) },
                        { assertEquals(cvLinks[1].name, list[1].name) },
                        { assertEquals(cvLinks[1].address, list[1].address) },
                        { assertEquals(cvLinks[2].name, list[2].name) },
                        { assertEquals(cvLinks[2].address, list[2].address) }
                    )
                })
            }
        }
    }

    @Nested
    @DisplayName("navigation to other fragments")
    inner class CvOverviewViewModelNavigationTest {
        @Test
        fun `navigates to certificates`() {
            sut.showCertificatesCommand()

            verify {
                navigator.openCertificatesList()
            }
        }

        @Test
        fun `navigates to education history`() {
            sut.showEducationCommand()

            verify {
                navigator.openEducationHistory()
            }
        }

        @Test
        fun `navigates to experience history`() {
            sut.showExperienceCommand()

            verify {
                navigator.openExperienceHistory()
            }
        }
    }
}
