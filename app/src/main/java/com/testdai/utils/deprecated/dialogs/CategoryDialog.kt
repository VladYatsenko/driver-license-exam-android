package com.testdai.utils.deprecated.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.testdai.R
import com.testdai.core.database.models.CategoryEntity

class CategoryDialog : DialogFragment() {

    companion object {
        fun newInstance(categories: ArrayList<CategoryEntity>?): CategoryDialog {
            val fragment = CategoryDialog()
            fragment.categories = categories
            return fragment
        }
    }

    var categories: ArrayList<CategoryEntity>? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

//        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_category, null)
//        setupRV(view.findViewById(R.id.categoryRV))
//
//        context?.let { context ->
//            return AlertDialog.Builder(context)
//                    .setView(view)
//                    .setTitle(R.string.choose_category)
//                    .setPositiveButton(android.R.string.ok) { dialog, i ->
//                        categories?.firstOrNull { it.isSelected == true }?.let {
//                            dialog.dismiss()
//                        }
//                    }
//                    .create()
//        }

        return super.onCreateDialog(savedInstanceState)

    }

    private fun setupRV(recycler: RecyclerView) {
//        categoryAdapter = CategoryAdapter()
//        categoryAdapter?.categories = categories
//        categoryAdapter?.listener = object : OnRecyclerItemClickListener {
//            override fun onRecyclerItemClick(position: Int) {
//                categories?.get(position)?.let { item ->
//                    if (item.group?.isEnabled == true) {
//                        item.isSelected = item.isSelected != true
//                        categories?.set(position, item)
//                        categories?.firstOrNull { it.isSelected == true }.let { category ->
//                            if (category == null) {
//                                categories?.forEach {
//                                    it.group?.isEnabled = true
//                                }
//                            } else {
//                                categories?.forEach {
//                                    it.group?.isEnabled = it.group?.groupName == category.group?.groupName
//                                }
//                            }
//                        }
//                    }
//                }
//                categoryAdapter?.notifyDataSetChanged()
//            }
//        }
//        recycler.adapter = categoryAdapter
//        recycler.layoutManager = GridLayoutManager(activity, 5)
    }

}