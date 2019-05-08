package com.ravn.edsonkrikey.ui.mainscreen

import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.*
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ravn.edsonkrikey.R
import com.ravn.edsonkrikey.core.BaseActivity
import com.ravn.edsonkrikey.core.BaseFragment
import com.ravn.edsonkrikey.core.FragmentLayout
import com.ravn.edsonkrikey.core.match
import com.ravn.edsonkrikey.databinding.FragmentMainBinding
import com.ravn.edsonkrikey.extensions.*
import com.ravn.edsonkrikey.repository.remote.ItunesItems
import com.ravn.edsonkrikey.repository.remote.Label
import com.ravn.edsonkrikey.ui.detailsscreen.DetailsFragment
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
        itunesAdapter = ItunesAdapter { v, i ->
            val item = i as ItunesItems
            val currentView = v as View
            letsExplodeIt(currentView, item)
            //launchScreen(DetailsFragment.newInstance(item), stackAction = BaseActivity.Companion.BackStack.ADD)
        }
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
            itunesAdapter.addItems(completeListOfItems())
        }
    }

    private fun runAnimation() {
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_slide_from_bottom)
        itemsRecyclerView.apply {
            layoutAnimation = controller
            scheduleLayoutAnimation()
        }
    }

    private fun letsExplodeIt(clickedView: View, item: ItunesItems) {
        val viewRect = Rect()
        clickedView.getGlobalVisibleRect(viewRect)
        val exitTransition = Explode()
        exitTransition.epicenterCallback = object : Transition.EpicenterCallback() {
            override fun onGetEpicenter(transition: Transition): Rect {
                return viewRect
            }
        }
        val set = TransitionSet()
        set.addTransition(exitTransition).excludeTarget(clickedView, true)
        set.addTransition(Fade().addTarget(clickedView))
        set.addListener(object : Transition.TransitionListener {
            override fun onTransitionResume(transition: Transition?) { }
            override fun onTransitionPause(transition: Transition?) { }
            override fun onTransitionCancel(transition: Transition?) { }
            override fun onTransitionStart(transition: Transition?) { }
            override fun onTransitionEnd(transition: Transition?) {
                launchScreen(DetailsFragment.newInstance(item), stackAction = BaseActivity.Companion.BackStack.ADD)
            }
        })
        TransitionManager.beginDelayedTransition(itemsRecyclerView, set)
        itemsRecyclerView.adapter = null
        viewModel.listOfItems = emptyList()
    }

    private fun setupRecyclerview() {
        val childLayoutManager = GridLayoutManager(context, 3)
        childLayoutManager.spanSizeLookup = (object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (itunesAdapter.getItemViewType(position)) {
                    R.layout.view_item_itunes -> 1
                    R.layout.view_item_label -> 3
                    else -> 1
                }
            }
        })
        itunesAdapter.apply {
            match(ItunesItems::class, R.layout.view_item_itunes)
            match(Label::class, R.layout.view_item_label)
        }
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
        fun newInstance() = MainFragment().apply {
        }
    }
}