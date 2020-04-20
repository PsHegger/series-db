package io.github.pshegger.seriesdb.ui.base

import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo

open class BaseViewModel {

    private var aliveDisposable = CompositeDisposable()
    private var foregroundDisposable = CompositeDisposable()

    @CallSuper
    open fun onViewCreated() {
        aliveDisposable = CompositeDisposable()
    }

    @CallSuper
    open fun onDestroyView() {
        aliveDisposable.clear()
    }

    @CallSuper
    open fun onViewResumed() {
        foregroundDisposable = CompositeDisposable()
    }

    @CallSuper
    open fun onViewPaused() {
        foregroundDisposable.clear()
    }

    protected fun Disposable.disposeOnPause() = addTo(foregroundDisposable)
    protected fun Disposable.disposeOnDestroy() = addTo(aliveDisposable)
}
