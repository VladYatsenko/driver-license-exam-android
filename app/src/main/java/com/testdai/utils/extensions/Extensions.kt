package com.testdai.utils.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <T : ViewModel> Fragment.getViewModel(factory: ViewModelProvider.Factory, modelClass: Class<T>): T = ViewModelProvider(this, factory).get(modelClass)

val FragmentManager.currentNavigationFragment: Fragment?
    get() = primaryNavigationFragment?.childFragmentManager?.fragments?.first()