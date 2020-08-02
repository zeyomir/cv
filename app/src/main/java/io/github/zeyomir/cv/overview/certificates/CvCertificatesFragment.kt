package io.github.zeyomir.cv.overview.certificates

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
import io.github.zeyomir.cv.databinding.FragmentCvCertificatesBinding
import io.github.zeyomir.cv.di.CvActivityComponent

class CvCertificatesFragment : BaseFragment<CvCertificatesViewModel>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentCvCertificatesBinding = DataBindingUtil
            .inflate(
                inflater,
                R.layout.fragment_cv_certificates,
                container,
                false
            )

        viewModel = ViewModelProvider(this)
            .get(CvCertificatesViewModel::class.java)
            .bindComponent(
                CvActivityComponent
                    .Initializer
                    .init(ScopeManager.getCvComponent(), activity!!, findNavController())
            )
        binding.viewModel = viewModel
        binding.certificates.addItemDecoration(PaddingDecoration(context!!))
        setUpToolbar(binding.toolbar, R.string.certificates)
        return binding.root.rootView
    }
}
