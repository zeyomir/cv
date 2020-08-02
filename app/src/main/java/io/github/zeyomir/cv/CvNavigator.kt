package io.github.zeyomir.cv

import androidx.navigation.NavController
import io.github.zeyomir.cv.base.AppNavigator

class CvNavigator(
    private val navController: NavController,
    private val appNavigator: AppNavigator
) {
    fun openExperienceHistory() {
        navController.navigate(R.id.action_cvOverview_to_cvExperience)
    }

    fun openEducationHistory() {
        navController.navigate(R.id.action_cvOverview_to_cvEducation)
    }

    fun openCertificatesList() {
        navController.navigate(R.id.action_cvOverview_to_cvCertificates)
    }

    fun openLink(link: String) {
        appNavigator.openBrowserWithUrl(link)
    }

    fun openEmailLink(link: String) {
        appNavigator.openEmailApp(link)
    }
}
