//package com.testdai.ui.fragments
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.navigation.fragment.findNavController
//import com.testdai.R
//import com.testdai.databinding.FragmentHomeBinding
//import com.testdai.di.components.DaggerScreenComponent
//import com.testdai.interfaces.OnCategorySelectedListener
//import com.testdai.core.database.models.CategoryEntity
//import com.testdai.ui.activities.SettingsActivity
//import com.testdai.ui.dialogs.CategoryDialog
//import kotlinx.android.synthetic.main.fragment_home.*
//
//class HomeFragment : BaseFragment<FragmentHomeBinding>() {
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
//            : View? = initBinding(DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false), this) {
//        super.onCreateView(inflater, container, savedInstanceState)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        updateCategoryBtn()
//
//        startTestBtn.setOnClickListener {
//            goToTest()
//        }
//
//        settingsBtn.setOnClickListener {
//            goToSettings()
//        }
//
//        categoryBtn.setOnClickListener {
//            CategoryDialog.newInstance(sharedPreferencesManager.categories, object : OnCategorySelectedListener {
//                override fun onCategorySelected(categories: ArrayList<CategoryEntity>?) {
//                    sharedPreferencesManager.categories = categories
//                    updateCategoryBtn()
//                }
//            }).also { it.show(childFragmentManager, it.tag) }
//        }
//    }
//
//    private fun goToTest(){
//        findNavController().navigate(R.id.actionToTestFragment)
//    }
//
//    private fun goToSettings(){
//        startActivity(Intent(context, SettingsActivity::class.java))
//    }
//
//    private fun updateCategoryBtn() {
//        var categoriesString = ""
//        sharedPreferencesManager.categories?.filter { it.isSelected == true }?.forEach { category ->
//            if (categoriesString.isBlank())
//                categoriesString += category.name
//            else categoriesString += ", ${category.name}"
//        }
//        categoryBtn.text = categoriesString
//    }
//
//    override fun inject() {
//        DaggerScreenComponent.builder()
//                .applicationComponent(applicationComponent)
//                .build()
//                .inject(this)
//    }
//}