//package com.testdai.ui.adapters.recyclerview
//
//import android.annotation.SuppressLint
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.testdai.databinding.ItemCategoryBinding
//import com.testdai.interfaces.OnRecyclerItemClickListener
//import com.testdai.core.database.models.CategoryEntity
//import com.testdai.model.enities.GroupEnum
//import com.jakewharton.rxbinding3.view.clicks
//import io.reactivex.android.schedulers.AndroidSchedulers
//import kotlinx.android.synthetic.main.item_category.view.*
//import java.util.concurrent.TimeUnit
//
//class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){
//
//    var categories: List<CategoryEntity>? = null
//    var listener: OnRecyclerItemClickListener? = null
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
//        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context))
//        return CategoryViewHolder(binding)
//    }
//
//    @SuppressLint("CheckResult")
//    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
//        val categoryEntity = categories?.get(position)
//        holder.bind(categoryEntity)
//        if (categoryEntity?.group?.groupName == GroupEnum.EMPTY)
//            return
//        holder.itemView.categoryLayout.clicks()
//                .throttleFirst(1000L, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe { listener?.onRecyclerItemClick(position) }
//
//    }
//
//    override fun getItemCount(): Int = categories?.size ?: 0
//
//    inner class CategoryViewHolder constructor(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(categoryEntity: CategoryEntity?) {
//            binding.categoryItem = categoryEntity
//            binding.isSelected = categoryEntity?.isSelected
//            binding.isEnabled = categoryEntity?.group?.isEnabled
//            binding.executePendingBindings()
//        }
//    }
//}