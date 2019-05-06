package com.ravn.edsonkrikey.core

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {

    @Inject
    lateinit var viewModel: VM
    lateinit var viewDataBinding: DB
    private val subscriptions by lazy { CompositeDisposable() }
    open val preferredOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    /**
     * If you want to inject Dependency Injection
     * on your activity, you can override this.
     */
    open fun onInject() {}

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        viewModel.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInject()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, javaClass.getAnnotation(FragmentLayout::class.java).layout, container, false)
        return viewDataBinding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.requestedOrientation = preferredOrientation
        initViewModel(viewModel)
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated()
    }

    override fun onDetach() {
        viewModel.onStop()
        super.onDetach()
    }

    @CallSuper
    override fun onDestroyView() {
        subscriptions.clear()
        viewModel.onViewDestroyed()
        super.onDestroyView()
    }

    abstract fun initViewModel(viewModel: VM)
    open fun onBackPressed(): Boolean {
        return false
    }
}