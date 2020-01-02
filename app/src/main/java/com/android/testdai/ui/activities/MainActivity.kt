package com.android.testdai.ui.activities

import android.content.Intent
import android.os.Bundle
import com.android.testdai.R
import com.android.testdai.di.components.DaggerScreenComponent
import com.android.testdai.interfaces.OnCategorySelectedListener
import com.android.testdai.managers.SharedPreferencesManager
import com.android.testdai.model.CategoryEntity
import com.android.testdai.ui.dialogs.CategoryDialog
import com.android.testdai.ui.test.TestActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MainActivity : BaseActivity() {

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateCategoryBtn()

        startTestBtn.setOnClickListener {
            goToTest()
        }

        settingsBtn.setOnClickListener {
            goToSettings()
        }

        categoryBtn.setOnClickListener {
            CategoryDialog.newInstance(sharedPreferencesManager.categories, object : OnCategorySelectedListener {
                override fun onCategorySelected(categories: ArrayList<CategoryEntity>?) {
                    sharedPreferencesManager.categories = categories
                    updateCategoryBtn()
                }
            }).also { it.show(supportFragmentManager, it.tag) }
        }
    }

    private fun goToTest(){
        startActivity(Intent(this, TestActivity::class.java))
    }

    private fun goToSettings(){
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun updateCategoryBtn() {
        var categoriesString = ""
        sharedPreferencesManager.categories?.filter { it.isSelected == true }?.forEach { category ->
            if (categoriesString.isBlank())
                categoriesString += category.name
            else categoriesString += ", ${category.name}"
        }
        categoryBtn.text = categoriesString
    }

    override fun inject() {
        DaggerScreenComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .inject(this)
    }
}