package com.android.testdai.viewmodel.binding

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

interface ViewBindingHolder<T : ViewDataBinding> {

    val binding: T?

    fun initBinding(binding: T, fragment: Fragment, onBound: (T.() -> Unit)?): View

    fun requireBinding(block: (T.() -> Unit)? = null): T
}