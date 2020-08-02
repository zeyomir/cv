package io.github.zeyomir.cv.overview.education

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.github.zeyomir.cv.R
import io.github.zeyomir.cv.base.di.ScopeManager
import io.github.zeyomir.cv.base.ui.BaseFragment
import io.github.zeyomir.cv.base.view.PaddingDecoration
import io.github.zeyomir.cv.databinding.FragmentCvEducationBinding
import io.github.zeyomir.cv.di.CvActivityComponent

class CvEducationFragment : BaseFragment<CvEducationViewModel>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentCvEducationBinding = DataBindingUtil
            .inflate(
                inflater,
                R.layout.fragment_cv_education,
                container,
                false
            )

        viewModel = ViewModelProvider(this)
            .get(CvEducationViewModel::class.java)
            .bindComponent(
                CvActivityComponent
                    .Initializer
                    .init(ScopeManager.getCvComponent(), activity!!, findNavController())
            )
        binding.viewModel = viewModel
        binding.education.addItemDecoration(PaddingDecoration(context!!))
        setUpToolbar(binding.toolbar, R.string.education)
        return binding.root.rootView
    }
}
