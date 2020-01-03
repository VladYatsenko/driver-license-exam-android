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
import com.android.testdai.model.AnswerEntity
import com.android.testdai.model.QuestionEntity
import com.android.testdai.model.QuestionWithAnswers
import com.android.testdai.ui.adapters.recyclerview.AnswerAdapter
import com.android.testdai.ui.adapters.recyclerview.QuestionAdapter
import com.android.testdai.viewmodel.TestViewModel
import com.android.testdai.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
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

            questions.observe(this@TestActivity, Observer {
                it?.let {
                    this@TestActivity.questions?.addAll(it)
                    questionAdapter.notifyDataSetChanged()
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
        questionAdapter.listener = object : OnRecyclerItemClickListener{
            override fun onRecyclerItemClick(position: Int) {
                questions?.forEachIndexed { index, questionWithAnswers ->
                    questionWithAnswers.questionEntity?.isSelected = position == index
                }
                questionAdapter.notifyDataSetChanged()
            }
        }
        questionRV.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        questionRV.adapter = questionAdapter

        answers = ArrayList()
        answerAdapter.answers = answers
        answerAdapter.listener = object : OnRecyclerItemClickListener{
            override fun onRecyclerItemClick(position: Int) {

            }
        }
        answerRV.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        answerRV.adapter = answerAdapter
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