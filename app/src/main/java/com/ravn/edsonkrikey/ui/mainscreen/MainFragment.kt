package com.ravn.edsonkrikey.ui.mainscreen

import android.os.Bundle
import android.view.View
import com.ravn.edsonkrikey.R
import com.ravn.edsonkrikey.core.BaseFragment
import com.ravn.edsonkrikey.core.FragmentLayout
import com.ravn.edsonkrikey.databinding.FragmentMainBinding

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

@FragmentLayout(R.layout.fragment_main)
class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {

    override fun initViewModel(viewModel: MainViewModel) {
        viewDataBinding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}