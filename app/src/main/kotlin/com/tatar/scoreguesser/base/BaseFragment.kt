package com.tatar.scoreguesser.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tatar.presentation.resource.navigation.Route
import com.tatar.presentation.util.EventObserver
import com.tatar.presentation.viewmodel.base.BaseViewModel
import com.tatar.presentation.viewmodel.factory.ViewModelFactory
import com.tatar.scoreguesser.resource.navigation.mapToNavigationResId
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    abstract val viewModel: BaseViewModel

    protected var activityActions: FragmentActivityActions? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentActivityActions) {
            this.activityActions = context
        }

        provideDependencies()
    }

    abstract fun provideDependencies()

    override fun onDetach() {
        activityActions = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNavigationEvents()
    }

    private fun observeNavigationEvents() {
        viewModel.navigationEvent.observe(viewLifecycleOwner, EventObserver { navigateTo(it) })
        viewModel.navigationBackEvent.observe(viewLifecycleOwner, EventObserver { navigateBack() })
    }

    private fun navigateTo(route: Route) {
        findNavController().navigate(route.mapToNavigationResId())

        if (route.shouldFinishHostActivity) {
            finishActivity()
        }
    }

    private fun navigateBack() {
        findNavController().let {
            if (it.graph.startDestination == it.currentDestination?.id) {
                finishActivity()
            } else {
                it.popBackStack()
            }
        }
    }

    private fun finishActivity() {
        activityActions?.onRequestFinish()
    }
}
