package com.android.testdai.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.testdai.R
import com.android.testdai.databinding.ActivityTestBinding
import com.android.testdai.di.components.DaggerScreenComponent
import com.android.testdai.interfaces.OnRecyclerItemClickListener
import com.android.testdai.managers.SharedPreferencesManager
import com.android.testdai.model.AnswerEntity
import com.android.testdai.model.QuestionWithAnswers
import com.android.testdai.ui.adapters.recyclerview.AnswerAdapter
import com.android.testdai.ui.adapters.recyclerview.QuestionAdapter
import com.android.testdai.utils.CenterLayoutManager
import com.android.testdai.utils.glide.GlideApp
import com.android.testdai.viewmodel.TestViewModel
import com.android.testdai.viewmodel.ViewModelFactory
import com.bumptech.glide.GenericTransitionOptions
import com.google.android.material.snackbar.Snackbar
import com.leochuan.CenterSnapHelper
import com.leochuan.ScrollHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_test.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList

class TestActivity : BaseActivity() {


    @Inject
    lateinit var questionAdapter: QuestionAdapter
    @Inject
    lateinit var answerAdapter: AnswerAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    private lateinit var binding: ActivityTestBinding
    private lateinit var viewModel: TestViewModel

    private var questions: ArrayList<QuestionWithAnswers>? = null
    private var answers: ArrayList<AnswerEntity>? = null
    private var backButtonPressedOnce = false
    private var countDownTimerMenu: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_test)
        binding.lifecycleOwner = this
        viewModel = ViewModelProviders.of(this, viewModelFactory)[TestViewModel::class.java]
        binding.viewmodel = viewModel

        initAdMob()
        setupRV()

        setupViewModelCallbacks()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setupViewModelCallbacks() {
        viewModel.apply {

            inProgress.observe(this@TestActivity, Observer {
                progressLayout.visibility = if (it) View.VISIBLE else View.GONE
            })

            timerValue.observe(this@TestActivity, Observer {
                it?.let {
                    countDownTimerMenu?.setTitle(SimpleDateFormat("mm:ss").format(Date(it)))
                }
            })

            questions.observe(this@TestActivity, Observer { it ->
                it?.let { list ->
                    this@TestActivity.questions?.clear()
                    this@TestActivity.questions?.addAll(list)
                    onQuestionSelected(this@TestActivity.questions?.indexOfFirst { it.questionEntity?.isSelected == true } ?: 0, false)
                }
            })

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_countdown, menu)
        countDownTimerMenu = menu.findItem(R.id.countdown)
        return true

    }

    private fun initAdMob() {
        //todo admob
//        MobileAds.initialize(this, getString(R.string.app_id));
//        mAdView = (AdView) findViewById(R.id.adView);
//        mAdView.setVisibility(View.VISIBLE);
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice("9489E98FDC7D70F02084422B7D2B18C3")
//                .build();
//        mAdView.loadAd(adRequest);
    }


    private fun setupRV() {
        questions = ArrayList()
        questionAdapter.questions = questions
        questionAdapter.listener = object : OnRecyclerItemClickListener {
            override fun onRecyclerItemClick(position: Int) {
                onQuestionSelected(position)
            }
        }
        questionRV.layoutManager = CenterLayoutManager(this, RecyclerView.HORIZONTAL, false)
        questionRV.adapter = questionAdapter
        CenterSnapHelper().attachToRecyclerView(questionRV)

        answers = ArrayList()
        answerAdapter.answers = answers
        answerAdapter.listener = object : OnRecyclerItemClickListener {
            override fun onRecyclerItemClick(position: Int) {
                onAnswerSelected(position)
            }
        }
        answerRV.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        answerRV.adapter = answerAdapter
    }

    private fun onQuestionSelected(position: Int, needScroll: Boolean = false) {
        if (position == -1) {
            onQuestionSelected(0, false)
            return
        }

        this.questions?.forEachIndexed { index, questionWithAnswers ->
            questionWithAnswers.questionEntity?.isSelected = position == index
        }
        questionAdapter.notifyDataSetChanged()

        this.questions?.firstOrNull { it.questionEntity?.isSelected == true }?.let { questionWithAnswers ->

            questionWithAnswers.questionEntity?.let {
                questionTxt.text = it.text
                questionImg.visibility = if (it.image.isNullOrBlank()) View.GONE else View.VISIBLE
                GlideApp.with(this)
                        .load(it.image)
                        .placeholder(R.drawable.empty)
                        .error(R.drawable.empty)
                        .into(questionImg)
            }
            questionWithAnswers.answers?.let {
                this.answers?.clear()
                this.answers?.addAll(it)
                answerAdapter.notifyDataSetChanged()
            }
        }

        if (needScroll)
            (questionRV.layoutManager as CenterLayoutManager).smoothScrollToPosition(questionRV, RecyclerView.State(), position)

    }


    private fun onAnswerSelected(position: Int) {
        if (answers?.firstOrNull { it.isAnswered == true } != null)
            return

        this.questions?.firstOrNull { it.questionEntity?.isSelected == true }?.let {
            it.questionEntity?.isAnswered = true
            it.answers?.get(position).let { answer ->
                if (sharedPreferencesManager.isDoubleClick) {
                    answers?.forEachIndexed { index, answerEntity ->
                        if (index == position) {
                            if (answerEntity.isSelected != true) {
                                answerEntity.isSelected = true
                            } else answerEntity.isAnswered = true
                        } else {
                            answerEntity.isSelected = false
                        }
                    }
                } else {
                    answers?.get(position)?.isAnswered = true
                }
            }

            questionAdapter.notifyDataSetChanged()
            answerAdapter.notifyDataSetChanged()

            goToNextQuestion()

        }
    }

    private fun goToNextQuestion() {
        if (questions?.firstOrNull { it.answers?.firstOrNull { it.isAnswered == true } == null } == null) {
            showResultDialog()
            return
        }

        var current = questions?.indexOfFirst { it.questionEntity?.isSelected == true } ?: 0
        for (i in 0 until (questions?.size ?: 0) - 1) {

            if (current >= (questions?.size ?: 0) - 1)
                current = 0
            else
                current++

            if (questions?.get(current)?.questionEntity?.isAnswered != true) {
                onQuestionSelected(current)
                break
            }
        }
    }

    private fun showResultDialog() {
        //todo result dialog
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onBackPressed() {
        if (backButtonPressedOnce) {
            finish()
        } else {
            if (backButtonPressedOnce) {
                finish()
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Натисніть ще раз для виходу!", Snackbar.LENGTH_LONG).show()
                backButtonPressedOnce = true
                Observable.empty<Any>()
                        .delay(2000L, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete { backButtonPressedOnce = false }
                        .subscribe()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        mAdView.destroy()
    }

    override fun inject() {
        DaggerScreenComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .inject(this)
    }
}