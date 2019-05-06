package com.ravn.edsonkrikey.ui.mainscreen

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ravn.edsonkrikey.R
import com.ravn.edsonkrikey.core.BaseFragment
import com.ravn.edsonkrikey.core.FragmentLayout
import com.ravn.edsonkrikey.databinding.FragmentMainBinding
import com.ravn.edsonkrikey.ui.mainscreen.adapter.ItunesAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import com.ravn.edsonkrikey.ui.mainscreen.MainViewModel.Companion.MainState

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

@FragmentLayout(R.layout.fragment_main)
class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {
    lateinit var itunesAdapter: ItunesAdapter

    override fun initViewModel(viewModel: MainViewModel) {
        viewDataBinding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itunesAdapter = ItunesAdapter {  }
        setupRecyclerview()
        viewModel.search()
        viewModel.apply {
            viewModelState.observe(this@MainFragment, Observer { state ->
                when (state) {
                    MainState.SUCCESS -> { updateView() }
                    MainState.ERROR -> { }
                }
            })
        }
    }

    private fun updateView() {
        viewModel.apply {
            runAnimation()
            itunesAdapter.addItems(listOfItems)
        }
    }

    private fun runAnimation() {
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_slide_from_bottom)
        itemsRecyclerView.apply {
            layoutAnimation = controller
            scheduleLayoutAnimation()
        }
    }

    private fun setupRecyclerview() {
        val childLayoutManager = LinearLayoutManager(context)
        itemsRecyclerView.apply {
            layoutManager = childLayoutManager
            hasFixedSize()
            adapter = itunesAdapter
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}