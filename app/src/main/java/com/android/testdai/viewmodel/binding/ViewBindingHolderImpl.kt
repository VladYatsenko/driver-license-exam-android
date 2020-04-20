package com.android.testdai.viewmodel.binding

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class ViewBindingHolderImpl<T : ViewDataBinding> : ViewBindingHolder<T>, LifecycleObserver {

    override var binding: T? = null

    private var lifecycle: Lifecycle? = null
    private lateinit var screenName: String

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyView() {
        lifecycle?.removeObserver(this)
        binding = null
    }

    override fun requireBinding(block: (T.() -> Unit)?) =
        binding?.apply { block?.invoke(this) } ?: throw IllegalStateException("Accessing binding outside of Fragment lifecycle: $screenName")

    override fun initBinding(binding: T, fragment: Fragment, onBound: (T.() -> Unit)?): View {
        this.binding = binding
        lifecycle = fragment.viewLifecycleOwner.lifecycle
        lifecycle?.addObserver(this)
        screenName = fragment::class.simpleName ?: "N/A"
        onBound?.invoke(binding)
        return binding.root
    }

}