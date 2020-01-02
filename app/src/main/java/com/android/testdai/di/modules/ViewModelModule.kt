package com.android.testdai.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.testdai.di.scopes.ViewModelKey
import com.android.testdai.viewmodel.TestViewModel
import com.android.testdai.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TestViewModel::class)
    internal abstract fun loginViewModel(viewModel: TestViewModel): ViewModel
}