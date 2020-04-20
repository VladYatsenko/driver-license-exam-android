package com.android.testdai.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.testdai.R
import com.android.testdai.databinding.FragmentHomeBinding
import com.android.testdai.di.components.DaggerScreenComponent
import com.android.testdai.interfaces.OnCategorySelectedListener
import com.android.testdai.model.enities.CategoryEntity
import com.android.testdai.ui.activities.SettingsActivity
import com.android.testdai.ui.dialogs.CategoryDialog
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? = initBinding(DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false), this) {
        super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            }).also { it.show(childFragmentManager, it.tag) }
        }
    }

    private fun goToTest(){
        findNavController().navigate(R.id.actionToTestFragment)
    }

    private fun goToSettings(){
        startActivity(Intent(context, SettingsActivity::class.java))
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