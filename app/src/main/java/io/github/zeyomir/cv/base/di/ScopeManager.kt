package io.github.zeyomir.cv.base.di

import io.github.zeyomir.cv.CvApplication
import io.github.zeyomir.cv.di.CvComponent
import kotlin.reflect.KClass

object ScopeManager {
    private var appComponent: AppComponent? = null
    private var cvComponent: CvComponent? = null

    fun init(app: CvApplication) {
        if (appComponent != null) {
            throw IllegalStateException("Must not initialize AppComponent twice")
        }
        appComponent = AppComponent.Initializer.init(app)
    }

    fun getAppComponent(): AppComponent = appComponent!!

    fun getCvComponent(components: MutableList<KClass<out Any>> = mutableListOf()): CvComponent {
        if (components.isEmpty()) {
            components.addAll(listOf(CvComponent::class))
        }
        clearScopesExcept(components)
        if (cvComponent == null) {
            cvComponent = CvComponent.Initializer.init(getAppComponent())
        }
        return cvComponent!!
    }

    private fun clearScopesExcept(except: List<KClass<out Any>> = emptyList()) {
        if (!except.contains(CvComponent::class)) {
            cvComponent = null
        }
    }
}
