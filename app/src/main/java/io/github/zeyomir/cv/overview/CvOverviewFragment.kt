package io.github.zeyomir.cv.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import io.github.zeyomir.cv.R
import io.github.zeyomir.cv.base.di.ScopeManager
import io.github.zeyomir.cv.base.ui.BaseFragment
import io.github.zeyomir.cv.databinding.FragmentCvOverviewBinding
import io.github.zeyomir.cv.di.CvActivityComponent

class CvOverviewFragment : BaseFragment<CvOverviewViewModel>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentCvOverviewBinding = DataBindingUtil
            .inflate(
                inflater,
                R.layout.fragment_cv_overview,
                container,
                false
            )

        viewModel = ViewModelProvider(this)
            .get(CvOverviewViewModel::class.java)
            .bindComponent(
                CvActivityComponent
                    .Initializer
                    .init(ScopeManager.getCvComponent(), activity!!, findNavController())
            )
        binding.viewModel = viewModel
        binding.swipeToRefresh.setOnRefreshListener { viewModel.refresh() }
        setupChips(binding)
        return binding.root.rootView
    }

    private fun setupChips(binding: FragmentCvOverviewBinding) {
        binding.keywords.layoutManager = chipsLayoutManager()
        binding.interests.layoutManager = chipsLayoutManager()
    }

    private fun chipsLayoutManager(): FlexboxLayoutManager {
        val layoutManager = FlexboxLayoutManager(context!!)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.flexWrap = FlexWrap.WRAP
        return layoutManager
    }
}
