package com.android.testdai.di.components

import com.android.testdai.di.scopes.ScreenScope
import com.android.testdai.ui.activities.MainActivity
import com.android.testdai.ui.activities.SettingsActivity
import com.android.testdai.ui.activities.TestActivity
import com.android.testdai.ui.fragments.SettingsFragment
import dagger.Component

@ScreenScope
@Component(dependencies = [ApplicationComponent::class], modules = [])
interface ScreenComponent {


    // activities, where dependencies injected:
    fun inject(mainActivity: MainActivity)
    fun inject(settingsActivity: SettingsActivity)
    fun inject(testActivity: TestActivity)


    // fragments, where dependencies injected:
    fun inject(settingsFragment: SettingsFragment)

}