package com.ravn.edsonkrikey.ui.mainscreen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ravn.edsonkrikey.R
import com.ravn.edsonkrikey.core.BaseFragment
import com.ravn.edsonkrikey.core.FragmentLayout
import com.ravn.edsonkrikey.databinding.FragmentMainBinding
import com.ravn.edsonkrikey.extensions.closeKeyboard
import com.ravn.edsonkrikey.extensions.onUi
import com.ravn.edsonkrikey.ui.mainscreen.adapter.ItunesAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import com.ravn.edsonkrikey.ui.mainscreen.MainViewModel.Companion.MainState
import java.util.*
import kotlin.concurrent.timerTask

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
        searchListener()
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
        itemsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    activity?.closeKeyboard()
                }
            }
        })
    }

    private fun searchListener() = onUi {
        editSearchItems.imeOptions = EditorInfo.IME_ACTION_DONE
        editSearchItems.addTextChangedListener(object : TextWatcher {
            var timer = Timer()
            override fun afterTextChanged(searchKeyword: Editable?) { }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(searchKeyword: CharSequence?, start: Int, before: Int, count: Int) {
                timer.cancel()
                timer = Timer()
                timer.schedule(timerTask{
                    if (searchKeyword?.length != 0 || before == 1) {
                        activity?.runOnUiThread {
                            viewModel.apply {
                                subscriptions.clear()
                                activity?.closeKeyboard()
                                viewModel.search(searchKeyword.toString() )
                            }
                        }
                    }
                }, DELAY)
            }
        })
        editSearchItems.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                activity?.closeKeyboard()
                true
            } else {
                false
            }
        }
    }

    companion object {
        const val DELAY = 400L
        fun newInstance() = MainFragment()
    }
}