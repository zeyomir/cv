package io.github.zeyomir.cv.overview

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.databinding.ObservableList
import io.github.zeyomir.cv.BR
import io.github.zeyomir.cv.CvNavigator
import io.github.zeyomir.cv.R
import io.github.zeyomir.cv.base.SchedulersProvider
import io.github.zeyomir.cv.base.extension.ObservableString
import io.github.zeyomir.cv.base.extension.addLoading
import io.github.zeyomir.cv.base.ui.BaseViewModel
import io.github.zeyomir.cv.base.ui.ErrorViewModel
import io.github.zeyomir.cv.di.CvActivityComponent
import io.github.zeyomir.cv.domain.entity.*
import io.github.zeyomir.cv.domain.usecase.RefreshCvDataUseCase
import io.github.zeyomir.cv.overview.items.*
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass
import timber.log.Timber
import javax.inject.Inject

class CvOverviewViewModel(
    val showError: ObservableBoolean = ObservableBoolean(),
    val showLoading: ObservableBoolean = ObservableBoolean(),
    val photoUrl: ObservableString = ObservableString(),
    val name: ObservableString = ObservableString(),
    val summary: ObservableString = ObservableString(),
    val keywords: ObservableList<String> = ObservableArrayList(),
    val interests: ObservableList<String> = ObservableArrayList(),
    val languages: ObservableList<CvLanguageItemViewModel> = ObservableArrayList(),
    val certificates: ObservableInt = ObservableInt(),
    val links: ObservableList<CvLinkItemViewModel> = ObservableArrayList(),
    val experience: ObservableList<BaseCvExperienceItemViewModel> = ObservableArrayList(),
    val moreExperienceAvailable: ObservableBoolean = ObservableBoolean(),
    val education: ObservableList<BaseCvEducationItemViewModel> = ObservableArrayList(),
    val moreEducationAvailable: ObservableBoolean = ObservableBoolean()
) : BaseViewModel() {

    @Inject
    lateinit var navigator: CvNavigator

    @Inject
    lateinit var overviewResourcesProvider: CvOverviewResourcesProvider

    @Inject
    lateinit var refreshData: RefreshCvDataUseCase

    @Inject
    lateinit var streamData: StreamCvOverviewUseCase

    @Inject
    lateinit var schedulersProvider: SchedulersProvider

    val chipsBinding: ItemBinding<String> = ItemBinding.of(BR.item, R.layout.item_chip)
    val languagesBinding: ItemBinding<CvLanguageItemViewModel> = ItemBinding.of(BR.viewModel, R.layout.item_language)
    val linksBinding: ItemBinding<CvLinkItemViewModel> = ItemBinding.of(BR.viewModel, R.layout.item_link)
    val educationBinding: OnItemBindClass<BaseCvEducationItemViewModel> = OnItemBindClass<BaseCvEducationItemViewModel>()
        .map(CvEducationItemViewModel::class.java, BR.viewModel, R.layout.item_education)
        .map(EmptyCvEducationItemViewModel::class.java, ItemBinding.VAR_NONE, R.layout.item_empty)
    val experienceBinding: OnItemBindClass<BaseCvExperienceItemViewModel> = OnItemBindClass<BaseCvExperienceItemViewModel>()
        .map(CvExperienceItemViewModel::class.java, BR.viewModel, R.layout.item_experience)
        .map(EmptyCvExperienceItemViewModel::class.java, ItemBinding.VAR_NONE, R.layout.item_empty)

    val errorViewModel = ErrorViewModel(showError) { refresh() }

    fun bindComponent(component: CvActivityComponent): CvOverviewViewModel {
        component.inject(this)
        return this
    }

    override fun beforeCreate() {
        super.beforeCreate()
        refresh()
        stream()
    }

    private fun stream() {
        disposeOnClear(
            streamData.execute()
                .observeOn(schedulersProvider.mainThread())
                .subscribe(
                    ::handleData,
                    ::handleError
                )
        )
    }

    fun refresh() {
        disposeOnClear(
            refreshData.execute()
                .doOnSubscribe { showError.set(false) }
                .addLoading(showLoading)
                .observeOn(schedulersProvider.mainThread())
                .subscribe(
                    { /* do nothing */ },
                    ::handleError
                )
        )
    }

    private fun handleData(cv: Cv) {
        handleCandidateBasicInfo(cv.candidate)
        summary.set(cv.summary)
        handleKeywords(cv.keywords)
        handleExperience(cv.experience)
        handleEducation(cv.education)
        certificates.set(cv.certificates.size)
        handleLanguages(cv.languages)
        handleInterests(cv.interests)
        handleLinks(cv.links)
    }

    private fun handleCandidateBasicInfo(cvCandidate: CvCandidate) {
        photoUrl.set(cvCandidate.photo)
        name.set(cvCandidate.name)
    }

    private fun handleKeywords(cvKeywords: List<String>) {
        keywords.clear()
        keywords.addAll(cvKeywords)
    }

    private fun handleExperience(cvExperience: List<CvExperience>) {
        experience.clear()
        if (cvExperience.isEmpty()) {
            experience.add(EmptyCvExperienceItemViewModel())
        } else {
            val latestExperience = cvExperience[0]
            experience.add(
                CvExperienceItemViewModel(
                    latestExperience.companyName,
                    latestExperience.roleName,
                    overviewResourcesProvider.getExperienceYearsText(latestExperience.dateFrom, latestExperience.dateTo),
                    latestExperience.description,
                    overviewResourcesProvider.getExperienceTechText(latestExperience.techStack),
                    latestExperience.companyLogo
                )
            )
        }
        moreExperienceAvailable.set(cvExperience.size > 1)
    }

    private fun handleEducation(cvEducation: List<CvEducation>) {
        education.clear()
        if (cvEducation.isEmpty()) {
            education.add(EmptyCvEducationItemViewModel())
        } else {
            val latestEducation = cvEducation[0]
            education.add(
                CvEducationItemViewModel(
                    latestEducation.institution,
                    overviewResourcesProvider.getEducationDegreeAndCourseText(latestEducation.degree, latestEducation.course),
                    overviewResourcesProvider.getEducationYearsText(latestEducation.startYear, latestEducation.endYear)
                )
            )
        }
        moreEducationAvailable.set(cvEducation.size > 1)
    }

    private fun handleLanguages(cvLanguages: List<CvLanguage>) {
        languages.clear()
        languages.addAll(cvLanguages.map { CvLanguageItemViewModel(it.language, it.level.percent) })
    }

    private fun handleInterests(cvInterests: List<String>) {
        interests.clear()
        interests.addAll(cvInterests)
    }

    private fun handleLinks(cvLinks: List<CvLink>) {
        links.clear()
        links.addAll(cvLinks.map {
            CvLinkItemViewModel(it.name, it.address, overviewResourcesProvider.getLinkIconFrom(it.type)) {
                if (it.type == LinkType.EMAIL) {
                    navigator.openEmailLink(it.address)
                } else {
                    navigator.openLink(it.address)
                }
            }
        })
    }

    private fun handleError(throwable: Throwable) {
        Timber.w(throwable)
        showError.set(true)
    }

    fun showExperienceCommand() {
        navigator.openExperienceHistory()
    }

    fun showEducationCommand() {
        navigator.openEducationHistory()
    }

    fun showCertificatesCommand() {
        navigator.openCertificatesList()
    }
}
