package com.android.testdai.ui.activities

import android.os.Bundle
import android.view.MenuItem
import com.android.testdai.R
import com.android.testdai.di.components.DaggerScreenComponent
import com.android.testdai.ui.fragments.SettingsFragment


class SettingsActivity: BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, SettingsFragment())
                .commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun inject() {
        DaggerScreenComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .inject(this)
    }
}