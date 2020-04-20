package io.github.pshegger.seriesdb.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo

abstract class BaseFragment : Fragment() {

    protected abstract val viewModel: BaseViewModel
    protected open val componentViewModels: List<BaseViewModel> = emptyList()

    private var aliveDisposable = CompositeDisposable()
    private var foregroundDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated()
        aliveDisposable = CompositeDisposable()
        componentViewModels.forEach(BaseViewModel::onViewCreated)
    }

    override fun onDestroy() {
        viewModel.onDestroyView()
        aliveDisposable.clear()
        componentViewModels.forEach(BaseViewModel::onDestroyView)
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewResumed()
        foregroundDisposable = CompositeDisposable()
        componentViewModels.forEach(BaseViewModel::onViewResumed)
    }

    override fun onPause() {
        viewModel.onViewPaused()
        foregroundDisposable.clear()
        componentViewModels.forEach(BaseViewModel::onViewPaused)
        super.onPause()
    }

    open fun onBackPressed(): Boolean = false

    protected fun Disposable.disposeOnPause() = addTo(foregroundDisposable)
    protected fun Disposable.disposeOnDestroy() = addTo(aliveDisposable)
}
