package com.android.testdai.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.android.testdai.di.components.ApplicationComponent
import com.android.testdai.managers.SharedPreferencesManager
import com.android.testdai.ui.activities.BaseActivity
import com.android.testdai.viewmodel.binding.ViewBindingHolder
import com.android.testdai.viewmodel.binding.ViewBindingHolderImpl
import kotlinx.android.synthetic.*
import javax.inject.Inject

abstract class BaseFragment <T : ViewDataBinding> : Fragment(), ViewBindingHolder<T> by ViewBindingHolderImpl() {

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    val applicationComponent: ApplicationComponent
        get() = (activity as BaseActivity).applicationComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        inject()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun showToast(text: String?){
        Toast.makeText(context, text ?: "", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearFindViewByIdCache()
    }

    abstract fun inject()

}