package io.github.zeyomir.cv.data.network

import io.github.zeyomir.cv.data.network.model.*
import io.github.zeyomir.cv.domain.entity.LanguageLevel
import io.github.zeyomir.cv.domain.entity.LinkType
import io.mockk.MockKAnnotations
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalDate
import java.util.stream.Stream

internal class CvConverterTest {
    private lateinit var sut: CvConverter

    private val date = LocalDate.of(1987, 6, 5)
    private val dateString = "1987-06-05T00:00:00.0Z"
    private val year = 2020
    private val yearString = "2020"

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        sut = CvConverter()
    }

    @Test
    fun `properly converts candidates info`() {
        val photo = "photo"
        val firstName = "first"
        val lastName = "last"
        val apiModel = ApiCvModel(ApiCvCandidateModel(photo, firstName, lastName), "", emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList())

        val model = sut.convert(apiModel)

        assertAll(
            { assertEquals(photo, model.candidate.photo) },
            { assertTrue(model.candidate.name.contains(firstName), "converted name contains first name") },
            { assertTrue(model.candidate.name.contains(lastName), "converted name contains last name") }
        )
    }

    @Test
    fun `keeps summary intact`() {
        val summary = "summary"
        val apiModel = ApiCvModel(ApiCvCandidateModel(null, "", ""), summary, emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList())

        val model = sut.convert(apiModel)

        assertEquals(summary, model.summary)
    }

    @Test
    fun `keeps keywords intact`() {
        val keywords = listOf("keyword1", "keyword2")
        val apiModel = ApiCvModel(ApiCvCandidateModel(null, "", ""), "", keywords, emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList())

        val model = sut.convert(apiModel)

        assertEquals(keywords, model.keywords)
    }

    @Nested
    @DisplayName("experience converting")
    inner class CvExperienceConverterTest {
        private val experienceModel1 = ApiCvExperienceModel(
            "companyName",
            "roleName",
            dateString,
            dateString,
            "description",
            listOf("tech1", "tech2"),
            "companyLogo"
        )

        private val experienceModel2 = ApiCvExperienceModel(
            "companyName2",
            "roleName2",
            dateString,
            null,
            "description2",
            listOf("tech12", "tech22"),
            null
        )

        @Test
        fun `properly converts candidates experience`() {
            val experience = listOf(experienceModel1, experienceModel2)
            val apiModel = ApiCvModel(ApiCvCandidateModel(null, "", ""), "", emptyList(), experience, emptyList(), emptyList(), emptyList(), emptyList(), emptyList())

            val converted = sut.convert(apiModel)
            val model = converted.experience[0]

            assertAll(
                { assertEquals(2, converted.experience.size) },
                { assertEquals(experienceModel1.companyName, model.companyName) },
                { assertEquals(experienceModel1.roleName, model.roleName) },
                { assertEquals(date, model.dateFrom) },
                { assertEquals(date, model.dateTo) },
                { assertEquals(experienceModel1.description, model.description) },
                { assertEquals(experienceModel1.techStack, model.techStack) },
                { assertEquals(experienceModel1.companyLogo, model.companyLogo) }
            )
        }

        @Test
        fun `handles nullable values in experience`() {
            val experience = listOf(experienceModel1, experienceModel2)
            val apiModel = ApiCvModel(ApiCvCandidateModel(null, "", ""), "", emptyList(), experience, emptyList(), emptyList(), emptyList(), emptyList(), emptyList())

            val converted = sut.convert(apiModel)
            val model = converted.experience[1]

            assertAll(
                { assertEquals(2, converted.experience.size) },
                { assertEquals(experienceModel2.companyName, model.companyName) },
                { assertEquals(experienceModel2.roleName, model.roleName) },
                { assertEquals(date, model.dateFrom) },
                { assertEquals(null, model.dateTo) },
                { assertEquals(experienceModel2.description, model.description) },
                { assertEquals(experienceModel2.techStack, model.techStack) },
                { assertEquals(experienceModel2.companyLogo, model.companyLogo) }
            )
        }
    }

    @Nested
    @DisplayName("education converting")
    inner class CvEducationConverterTest {
        private val educationModel1 = ApiCvEducationnModel(
            "institution1",
            "course1",
            "degree1",
            yearString,
            yearString
        )

        private val educationModel2 = ApiCvEducationnModel(
            "institution2",
            "course2",
            "degree2",
            yearString,
            null
        )

        @Test
        fun `properly converts candidates education`() {
            val education = listOf(educationModel1, educationModel2)
            val apiModel = ApiCvModel(ApiCvCandidateModel(null, "", ""), "", emptyList(), emptyList(), education, emptyList(), emptyList(), emptyList(), emptyList())

            val converted = sut.convert(apiModel)
            val model = converted.education[0]

            assertAll(
                { assertEquals(2, converted.education.size) },
                { assertEquals(educationModel1.institution, model.institution) },
                { assertEquals(educationModel1.course, model.course) },
                { assertEquals(educationModel1.degree, model.degree) },
                { assertEquals(year, model.startYear) },
                { assertEquals(year, model.endYear) }
            )
        }

        @Test
        fun `handles nullable values in education`() {
            val education = listOf(educationModel1, educationModel2)
            val apiModel = ApiCvModel(ApiCvCandidateModel(null, "", ""), "", emptyList(), emptyList(), education, emptyList(), emptyList(), emptyList(), emptyList())

            val converted = sut.convert(apiModel)
            val model = converted.education[1]

            assertAll(
                { assertEquals(2, converted.education.size) },
                { assertEquals(educationModel2.institution, model.institution) },
                { assertEquals(educationModel2.course, model.course) },
                { assertEquals(educationModel2.degree, model.degree) },
                { assertEquals(year, model.startYear) },
                { assertEquals(null, model.endYear) }
            )
        }
    }

    private val certificateModel = ApiCvCertificateModel(
        "issuer",
        "name",
        yearString
    )

    @Test
    fun `properly converts candidates certificates`() {
        val certificates = listOf(certificateModel, certificateModel)
        val apiModel = ApiCvModel(ApiCvCandidateModel(null, "", ""), "", emptyList(), emptyList(), emptyList(), certificates, emptyList(), emptyList(), emptyList())

        val converted = sut.convert(apiModel)
        val model = converted.certificates[0]

        assertAll(
            { assertEquals(2, converted.certificates.size) },
            { assertEquals(certificateModel.issuer, model.issuer) },
            { assertEquals(certificateModel.name, model.name) },
            { assertEquals(year, model.year) }
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("languages converting")
    inner class CvLanguagesConverterTest {
        private val languageModel1 = ApiCvLanguageModel("language1", "native")
        private val languageModel2 = ApiCvLanguageModel("language2", "b2")

        @Test
        fun `properly converts candidates languages`() {
            val languages = listOf(languageModel1, languageModel2)
            val apiModel = ApiCvModel(ApiCvCandidateModel(null, "", ""), "", emptyList(), emptyList(), emptyList(), emptyList(), languages, emptyList(), emptyList())

            val converted = sut.convert(apiModel)
            val model = converted.languages[0]

            assertAll(
                { assertEquals(2, converted.languages.size) },
                { assertEquals(languageModel1.language, model.language) },
                { assertEquals(LanguageLevel.NATIVE, model.level) }
            )
        }

        @ParameterizedTest(name = "mapping of level {0} to {1}")
        @MethodSource()
        fun languagesTypeMapping(levelString: String, level: LanguageLevel) {
            val languages = listOf(ApiCvLanguageModel("", levelString))
            val apiModel = ApiCvModel(ApiCvCandidateModel(null, "", ""), "", emptyList(), emptyList(), emptyList(), emptyList(), languages, emptyList(), emptyList())

            val converted = sut.convert(apiModel)
            val model = converted.languages[0]

            assertEquals(level, model.level)
        }

        private fun languagesTypeMapping(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("native", LanguageLevel.NATIVE),
                Arguments.of("c2", LanguageLevel.C2),
                Arguments.of("c1", LanguageLevel.C1),
                Arguments.of("b2", LanguageLevel.B2),
                Arguments.of("b1", LanguageLevel.B1),
                Arguments.of("a2", LanguageLevel.A2),
                Arguments.of("a1", LanguageLevel.A1),
                Arguments.of("zdsafdsa", LanguageLevel.UNKNOWN)
            )
        }
    }

    @Test
    fun `keeps interests intact`() {
        val interests = listOf("interest1", "interest2")
        val apiModel = ApiCvModel(ApiCvCandidateModel(null, "", ""), "", emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), interests, emptyList())

        val model = sut.convert(apiModel)

        assertEquals(interests, model.interests)
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("links converting")
    inner class CvLinksConverterTest {

        private val linkModel = ApiCvLinkModel(
            "email",
            "email@add.res",
            "name"
        )

        @Test
        fun `properly converts candidates links`() {
            val links = listOf(linkModel, linkModel)
            val apiModel = ApiCvModel(ApiCvCandidateModel(null, "", ""), "", emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), links)

            val converted = sut.convert(apiModel)
            val model = converted.links[0]

            assertAll(
                { assertEquals(2, converted.links.size) },
                { assertEquals(linkModel.address, model.address) },
                { assertEquals(linkModel.name, model.name) },
                { assertEquals(LinkType.EMAIL, model.type) }
            )
        }

        @ParameterizedTest(name = "mapping of type {0} to {1}")
        @MethodSource()
        fun linksTypeMapping(typeString: String, type: LinkType) {
            val links = listOf(ApiCvLinkModel(typeString, "", ""))
            val apiModel = ApiCvModel(ApiCvCandidateModel(null, "", ""), "", emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), links)

            val converted = sut.convert(apiModel)
            val model = converted.links[0]

            assertEquals(type, model.type)
        }

        private fun linksTypeMapping(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("email", LinkType.EMAIL),
                Arguments.of("social", LinkType.SOCIAL),
                Arguments.of("asddasfasdfa", LinkType.OTHER)
            )
        }
    }
}
