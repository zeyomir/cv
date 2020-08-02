package io.github.zeyomir.cv.data.network

import io.github.zeyomir.cv.data.network.model.*
import io.reactivex.Single
import retrofit2.mock.BehaviorDelegate

class MockCvService(
    private val behavior: BehaviorDelegate<CvService>
) : CvService {
    override fun cv(): Single<ApiCvModel> =
        behavior.returningResponse(
            ApiCvModel(
                ApiCvCandidateModel(
                    listOf(
                        "https://vignette.wikia.nocookie.net/half-life/images/f/f3/Gordon_Freeman_%28Template%29.jpg/revision/latest?cb=20191221105246&path-prefix=en",
                        null
                    ).random(),
                    "Gordon",
                    "Freeman"
                ),
                listOf(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse facilisis, lorem a convallis aliquam, nisi elit gravida magna, sit amet fringilla",
                    "Short description",
                    """Very very very long description Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse facilisis, lorem a convallis aliquam, nisi elit gravida magna, sit amet fringilla Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse facilisis, lorem a convallis aliquam, nisi elit gravida magna, sit amet fringilla Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse facilisis, lorem a convallis aliquam, nisi elit gravida magna, sit amet fringilla."""
                ).random(),
                listOf(
                    listOf("crowbars", "EPR entanglement", "xens", "teleportation"),
                    listOf("teleportation"),
                    emptyList()
                ).random(),
                listOf(
                    listOf(
                        ApiCvExperienceModel(
                            "Black Mesa",
                            "Theoretical physicist",
                            "1999-07-01T00:00:00Z",
                            "2001-06-15T00:00:00Z",
                            "Fighting Headcrabs with a crowbar",
                            listOf("crowbars", "Gravity Gun"),
                            null
                        ),
                        ApiCvExperienceModel(
                            "Aperture Science",
                            "Test subject",
                            "2001-07-01T00:00:00Z",
                            null,
                            "Outsmarting an evil AI",
                            listOf("Handheld Portal Device"),
                            "http://borealis.net.pl/images/thumb/f/f7/Aperture.png/180px-Aperture.png"
                        )
                    ),
                    listOf(
                        ApiCvExperienceModel(
                            "Aperture Science",
                            "Test subject",
                            "2001-07-01T00:00:00Z",
                            null,
                            "Outsmarting an evil AI",
                            listOf("Handheld Portal Device"),
                            null
                        )
                    ),
                    emptyList()
                ).random(),
                listOf(
                    listOf(
                        ApiCvEducationnModel(
                            "MIT",
                            "Theoretical physics",
                            "Ph.D.",
                            "1995",
                            "1999"
                        ),
                        ApiCvEducationnModel(
                            "MIT",
                            "Theoretical physics",
                            "Master's degree",
                            "1990",
                            "1995"
                        )
                    ),
                    listOf(
                        ApiCvEducationnModel(
                            "MIT",
                            "Theoretical physics",
                            "Master's degree",
                            "1990",
                            "1995"
                        )
                    ),
                    emptyList()
                ).random(),
                listOf(
                    listOf(
                        ApiCvCertificateModel("Black Mesa", "Certified Gravity Gun Operator", "2000"),
                        ApiCvCertificateModel("Aperture Science", "Certified Handheld Portal Device Operator", "2002")
                    ),
                    emptyList()
                ).random(),
                listOf(
                    listOf(
                        ApiCvLanguageModel("English", "native"),
                        ApiCvLanguageModel("Spanish", "b2")
                    ),
                    listOf(
                        ApiCvLanguageModel("English", "native")
                    ),
                    emptyList()
                ).random(),
                listOf(
                    listOf("resistance", "saving the earth", "alien technology"),
                    emptyList()
                ).random(),
                listOf(
                    listOf(
                        ApiCvLinkModel("email", "gordon.freeman@blackmesa.com", "Email"),
                        ApiCvLinkModel("social", "https://www.linkedin.com/in/gordon-freeman", "LinkedIn")
                    ),
                    emptyList()
                ).random()
            )
        ).cv()
}
