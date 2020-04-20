package com.android.testdai.ui.fragments

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.testdai.R
import com.android.testdai.databinding.FragmentTestBinding
import com.android.testdai.di.components.DaggerScreenComponent
import com.android.testdai.interfaces.OnRecyclerItemClickListener
import com.android.testdai.interfaces.OnResultClickListener
import com.android.testdai.ui.adapters.recyclerview.AnswerAdapter
import com.android.testdai.ui.adapters.recyclerview.QuestionAdapter
import com.android.testdai.ui.dialogs.ResultDialog
import com.android.testdai.utils.CenterLayoutManager
import com.android.testdai.utils.extensions.getViewModel
import com.android.testdai.utils.glide.GlideApp
import com.android.testdai.viewmodel.TestViewModel
import com.android.testdai.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import com.leochuan.CenterSnapHelper
import com.stfalcon.imageviewer.StfalconImageViewer
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_test.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList

class TestFragment : BaseFragment<FragmentTestBinding>() {

    @Inject
    lateinit var questionAdapter: QuestionAdapter
    @Inject
    lateinit var answerAdapter: AnswerAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var countDownTimerMenu: MenuItem? = null

    public var backButtonPressedOnce = false
    private var snackBar: Snackbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? = initBinding(DataBindingUtil.inflate(inflater, R.layout.fragment_test, container, false), this) {
        super.onCreateView(inflater, container, savedInstanceState)
        binding?.viewmodel = getViewModel(viewModelFactory, TestViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdMob()
        setupToolbar()
        setupRV()
        setupViewModelCallbacks()

        snackBar = Snackbar.make(content, "Натисніть ще раз для виходу!", Snackbar.LENGTH_SHORT)
    }

    override fun onResume() {
        super.onResume()
        adView.resume()
    }

    override fun onPause() {
        super.onPause()
        adView.pause()
    }

    private fun initAdMob() {
        MobileAds.initialize(context)
        adView.visibility = View.VISIBLE
        adView.loadAd(AdRequest.Builder().build())
    }

    private fun setupToolbar() {
        context?.let {
            toolbar?.navigationIcon = ContextCompat.getDrawable(it, R.drawable.ic_arrow_back)
            toolbar?.setNavigationOnClickListener {
                activity?.onBackPressed()
            }
            toolbar?.inflateMenu(R.menu.menu_countdown)
            countDownTimerMenu = toolbar?.menu?.findItem(R.id.countdown)
        }
    }

    private fun setupRV() {
        questionAdapter.questions = ArrayList()
        questionAdapter.listener = object : OnRecyclerItemClickListener {
            override fun onRecyclerItemClick(position: Int) {
                onQuestionSelected(position, true)
            }
        }
        questionRV.layoutManager = CenterLayoutManager(context, RecyclerView.HORIZONTAL, false)
        questionRV.adapter = questionAdapter
        CenterSnapHelper().attachToRecyclerView(questionRV)

        answerAdapter.answers = ArrayList()
        answerAdapter.listener = object : OnRecyclerItemClickListener {
            override fun onRecyclerItemClick(position: Int) {
                onAnswerSelected(position)
            }
        }
        answerRV.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        answerRV.adapter = answerAdapter
    }

    private fun setupViewModelCallbacks() {
        binding?.viewmodel?.apply {

            inProgress.observe(viewLifecycleOwner, Observer {
                progressLayout.visibility = if (it) View.VISIBLE else View.GONE
            })

            timerValue.observe(viewLifecycleOwner, Observer {
                it?.let {
                    countDownTimerMenu?.title = formatCountDownTime(it)
                }
            })

            questions.observe(viewLifecycleOwner, Observer { it ->
                it?.let { list ->
                    questionAdapter.questions = list
                    onQuestionSelected(this@TestFragment.questionAdapter.questions?.indexOfFirst { it.questionEntity?.isSelected == true } ?: 0)
                }
            })

            test.observe(viewLifecycleOwner, Observer {
                it?.let {
                    if (!it.isTestAvailable && it.isNeedToShowResultDialog)
                        showResultDialog()
                }
            })
        }
    }

    private fun onQuestionSelected(position: Int, needScroll: Boolean = false) {
        if (position == -1) {
            onQuestionSelected(0)
            return
        }

        questionAdapter.questions?.forEachIndexed { index, questionWithAnswers ->
            questionWithAnswers.questionEntity?.isSelected = position == index
        }
        questionAdapter.notifyDataSetChanged()

        questionAdapter.questions?.firstOrNull { it.questionEntity?.isSelected == true }?.let { questionWithAnswers ->

            questionWithAnswers.questionEntity?.let {
                questionTxt.text = it.text
                questionImg.visibility =
                        if (it.image.isNullOrBlank())
                            (if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                                View.INVISIBLE
                            else View.GONE)
                        else View.VISIBLE

                questionImg.setOnClickListener { view ->
                    StfalconImageViewer.Builder<String>(context, listOf(it.image)) { view, image ->
                        Glide.with(this)
                                .load(image)
                                .into(view)
                    }
                            .withHiddenStatusBar(false)
                            .allowZooming(true)
                            .allowSwipeToDismiss(true)
                            .withTransitionFrom(questionImg)
                            .show(true)
                }

                GlideApp.with(this)
                        .load(it.image)
                        .placeholder(R.drawable.empty)
                        .error(R.drawable.empty)
                        .into(questionImg)
            }
            questionWithAnswers.answers?.let {
                answerAdapter.answers?.clear()
                answerAdapter.answers?.addAll(it)
                answerAdapter.notifyDataSetChanged()
            }
        }

        if (needScroll)
            (questionRV.layoutManager as CenterLayoutManager).smoothScrollToPosition(questionRV, RecyclerView.State(), position)

    }

    private fun onAnswerSelected(position: Int) {
        if (answerAdapter.answers?.firstOrNull { it.isAnswered == true } != null
                || binding?.viewmodel?.test?.value?.isTestAvailable != true)
            return

        questionAdapter.questions?.firstOrNull { it.questionEntity?.isSelected == true }?.let {
            it.questionEntity?.isAnswered = true
            it.answers?.get(position).let { answer ->
                if (sharedPreferencesManager.isDoubleClick) {
                    answerAdapter.answers?.forEachIndexed { index, answerEntity ->
                        if (index == position) {
                            if (answerEntity.isSelected != true) {
                                answerEntity.isSelected = true
                            } else answerEntity.isAnswered = true
                        } else {
                            answerEntity.isSelected = false
                        }
                    }
                } else {
                    answerAdapter.answers?.get(position)?.isAnswered = true
                }
            }

            questionAdapter.notifyDataSetChanged()
            answerAdapter.notifyDataSetChanged()

            goToNextQuestion()

        }
    }

    private fun goToNextQuestion() {
        if (questionAdapter.questions?.firstOrNull { it.answers?.firstOrNull { it.isAnswered == true } == null } == null
                || (sharedPreferencesManager.isErrorLimit && isErrorLimit())) {

            binding?.viewmodel?.endTest()
            return
        }

        var current = questionAdapter.questions?.indexOfFirst { it.questionEntity?.isSelected == true } ?: 0
        for (i in 0 until (questionAdapter.questions?.size ?: 0) - 1) {

            if (current >= (questionAdapter.questions?.size ?: 0) - 1)
                current = 0
            else
                current++

            if (questionAdapter.questions?.get(current)?.questionEntity?.isAnswered != true) {
                onQuestionSelected(current, true)
                break
            }
        }
    }

    private fun showResultDialog() {

        var result = 0
        questionAdapter.questions?.forEach {
            it.answers?.forEach { answer ->
                if (answer.isCorrect == true && answer.isAnswered == true) {
                    result++
                }
            }
        }

        ResultDialog.newInstance(result, object : OnResultClickListener {
            override fun onRestartTest() {
                findNavController().navigate(R.id.actionTestFragmentSelf)
            }
        }).also { it.show(childFragmentManager, it.tag) }

        binding?.viewmodel?.endTest(false)

    }

    private fun isErrorLimit(): Boolean {
        var result = 0
        questionAdapter.questions?.forEach {
            it.answers?.forEach { answer ->
                if (answer.isCorrect != true && answer.isAnswered == true) {
                    result++
                }
            }
        }

        return result > 2
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatCountDownTime(time: Long): String {
        return SimpleDateFormat("mm:ss").format(Date(time))
    }

    fun onBackPressed() {
        if (backButtonPressedOnce) {
            activity?.onBackPressed()
        } else {
            if (backButtonPressedOnce) {
                activity?.onBackPressed()
            } else {
                snackBar?.show()
                backButtonPressedOnce = true
                io.reactivex.Observable.just(true)
                        .delay(2000L, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete { backButtonPressedOnce = false }
                        .subscribe()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adView.destroy()
        if (snackBar?.isShown == true)
            snackBar?.dismiss()
    }

    override fun inject() {
        DaggerScreenComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .inject(this)
    }

}