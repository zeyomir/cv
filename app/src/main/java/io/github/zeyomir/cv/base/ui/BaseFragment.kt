package io.github.zeyomir.cv.base.ui

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<VIEW_MODEL : BaseViewModel> : Fragment() {
    lateinit var viewModel: VIEW_MODEL

    override fun onStart() {
        super.onStart()
        lifecycle.addObserver(viewModel)
    }

    private fun navigateUp() {
        if (!findNavController().navigateUp()) {
            activity?.finish()
        }
    }

    protected open fun setUpToolbar(toolbar: Toolbar, @StringRes title: Int) {
        with(activity as AppCompatActivity) {
            setSupportActionBar(toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
                setDisplayShowTitleEnabled(true)
                setTitle(title)
            }
            toolbar.setNavigationOnClickListener { navigateUp() }
        }
    }
}
