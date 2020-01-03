package com.android.testdai.ui.adapters.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.testdai.databinding.ItemAnswerBinding
import com.android.testdai.di.scopes.ScreenScope
import com.android.testdai.interfaces.OnRecyclerItemClickListener
import com.android.testdai.model.AnswerEntity
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.item_answer.view.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ScreenScope
class AnswerAdapter @Inject constructor(
        private val context: Context
) : RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>() {

    var answers: List<AnswerEntity>? = null
    var listener: OnRecyclerItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val binding = ItemAnswerBinding.inflate(LayoutInflater.from(parent.context))
        return AnswerViewHolder(binding)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        val answer = answers?.get(position)
        holder.bind(answer)
        holder.itemView.answerLayout.clicks()
                .throttleFirst(1000L, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { listener?.onRecyclerItemClick(position) }

    }

    override fun getItemCount(): Int = answers?.size ?: 0

    inner class AnswerViewHolder constructor(private val binding: ItemAnswerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(answerEntity: AnswerEntity?) {
            binding.isQuestionAnswered = answers?.firstOrNull { it.isAnswered == true } != null
            binding.answerItem = answerEntity
            binding.executePendingBindings()
        }
    }

}