package com.android.testdai.ui.activities

import android.os.Bundle
import com.android.testdai.R
import com.android.testdai.di.components.DaggerScreenComponent
import com.android.testdai.ui.fragments.TestFragment
import com.android.testdai.utils.extensions.currentNavigationFragment


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.currentNavigationFragment
        if (fragment is TestFragment) {
            if (!fragment.backButtonPressedOnce) {
                fragment.onBackPressed()
                return
            }
        }
        super.onBackPressed()

    }

    override fun inject() {
        DaggerScreenComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .inject(this)
    }
}