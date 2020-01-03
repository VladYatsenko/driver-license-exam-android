package com.android.testdai.ui.adapters.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.testdai.databinding.ItemQuestionBinding
import com.android.testdai.di.scopes.ScreenScope
import com.android.testdai.interfaces.OnRecyclerItemClickListener
import com.android.testdai.model.QuestionWithAnswers
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.item_question.view.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ScreenScope
class QuestionAdapter @Inject constructor(
        private val context: Context
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    var questions: List<QuestionWithAnswers>? = null
    var listener: OnRecyclerItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = ItemQuestionBinding.inflate(LayoutInflater.from(parent.context))
        return QuestionViewHolder(binding)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions?.get(position)
        holder.bind(question)
        holder.itemView.questionLayout.clicks()
                .throttleFirst(1000L, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { listener?.onRecyclerItemClick(position) }

    }

    override fun getItemCount(): Int = questions?.size ?: 0

    inner class QuestionViewHolder constructor(private val binding: ItemQuestionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(questionsEntity: QuestionWithAnswers?) {
            binding.position = adapterPosition.plus(1).toString()
            binding.isAnswered = questionsEntity?.answers?.firstOrNull { it.isAnswered == true } != null
            binding.isCorrect = questionsEntity?.answers?.firstOrNull { it.isCorrect == true && it.isAnswered == true} != null
            binding.questionItem = questionsEntity?.questionEntity
            binding.executePendingBindings()
        }
    }

}