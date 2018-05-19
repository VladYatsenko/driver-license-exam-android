package com.android.testdai;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.testdai.Model.Question;
import com.android.testdai.Model.Question.Answer;
import com.android.testdai.Model.QuestionLab;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class TestActivity extends AppCompatActivity{

    private static final int SEND_RESULT = 0;
    private static final int RESTART = 1;
    private static final String DIALOG_RESULT = "DialogResult";
    private static final String DIALOG_IMAGE = "DialogImage";

    int mPosition;

    private List<Question> mQuestions;
    private List<Answer> mAnswers;

    private Question mQuestion;

    private RecyclerView mQuestionRecycler;
    private RecyclerView mAnswerRecycler;
    private QuestionAdapter mAdapterQuestion;
    private AnswerAdapter mAdapterAnswer;

    private LinearLayoutManager mLinearLayoutManager;

    private TextView mQuestionTextView;
    private ImageView mQuestionImage;

    private AdView mAdView;

    private CountDownTimer mCountDownTimer;
    private boolean mIsResume = false;
    private boolean mTime = false;
    private Handler mHandler;
    private int mTimerLast = 1200000;
    private static final String TIMER = "timer";
    private static final String QUESTION_LIST = "questions";
    private static final String EXTRA_CATEGORY = "category";

    public static Intent newIntent (Context packageContext, String category){
        Intent intent = new Intent(packageContext, TestActivity.class);
        intent.putExtra(EXTRA_CATEGORY, category);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String mCategory = (String) getIntent().getSerializableExtra(EXTRA_CATEGORY);
        mQuestionRecycler = (RecyclerView) findViewById(R.id.question_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(TestActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mQuestionRecycler.setLayoutManager(mLinearLayoutManager);

        mAnswerRecycler = (RecyclerView) findViewById(R.id.answer_recycler_view);
        mAnswerRecycler.setLayoutManager(new LinearLayoutManager(TestActivity.this));
        mQuestionTextView = (TextView) findViewById(R.id.text_question);
        mQuestionImage = (ImageView) findViewById(R.id.question_image);

        if (savedInstanceState != null) {
            mTimerLast = savedInstanceState.getInt(TIMER);
            mQuestions = (List<Question>) savedInstanceState.getSerializable(QUESTION_LIST);
            Question mQuestion = mQuestions.get(0);
            for(Question question : mQuestions){
                if(question.isWasSelected()){
                    mQuestion = question;
                    updateQuestionUI(mQuestions.indexOf(question));
                }
            }
            mAdapterQuestion.selectQuestion();
        } else {
            mQuestions = QuestionLab.get(TestActivity.this).getQuestions(mCategory);
            updateQuestionUI(0);
            mAdapterQuestion.selectDefault();
        }

        MobileAds.initialize(this,
                "ca-app-pub-5104532168407959~6750661013");

        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setVisibility(View.VISIBLE);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("5265E0E103CEDC51B6E5157D84578C60")
                .build();
        mAdView.loadAd(adRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fragment_question, menu);
        final MenuItem mCountdown = menu.findItem(R.id.countdown);
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                int message = msg.getData().getInt("data");
                mCountdown.setTitle(new SimpleDateFormat("mm:ss").format(new Date(message)));
            };
        };
        countdownTimer();
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(TIMER, mTimerLast);
        savedInstanceState.putSerializable(QUESTION_LIST, (Serializable) mQuestions);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mTimerLast = savedInstanceState.getInt(TIMER);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCountDownTimer.cancel();
        mAdView.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(!mIsResume){
            countdownTimer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCountDownTimer.cancel();
        mAdView.destroy();
    }

    private boolean twice = false;
    @Override
    public void onBackPressed() {

        if(twice){
            finish();
        }
        twice = true;

        Snackbar mSnackbar = Snackbar.make(findViewById(android.R.id.content), "Натисніть ще раз для виходу!", Snackbar.LENGTH_LONG);
        mSnackbar.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
            }
        },3000);

    }


    private class QuestionHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private Question mQuestion;
        private List<Answer> mAnswers;
        private TextView mQuestionCount;
        private RelativeLayout mRelativeLayoutQuestion;
        int position = 0;

        public QuestionHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_question, parent, false));
            mQuestionCount = (TextView) itemView.findViewById(R.id.number_question);
            mRelativeLayoutQuestion = (RelativeLayout) itemView.findViewById(R.id.relative_layout_question);
            itemView.setOnClickListener(this);
        }

        public void bind(Question question, int pos){
            position=pos;
            mQuestion = question;
            mAnswers = question.getAnswer();
            mQuestionCount.setText(String.valueOf(pos+1));
            if(question.isWasSelected()){
                mRelativeLayoutQuestion.setBackgroundResource(R.drawable.selected);
                if(question.isWasAnswered()){
                    for(Answer answer : mAnswers){
                        if(answer.isWasChoosen()){
                            if(answer.isAnswerTrue()){
                                mRelativeLayoutQuestion.setBackgroundResource(R.drawable.selected_true);
                            }
                            else {
                                mRelativeLayoutQuestion.setBackgroundResource(R.drawable.selected_false);
                            }
                            mQuestionCount.setTextColor(Color.WHITE);
                        }
                    }
                } else if (!question.isWasAnswered()){
                    mQuestionCount.setTextColor(Color.BLACK);
                }
            } else {
                if(question.isWasAnswered()){
                    mQuestionCount.setTextColor(Color.WHITE);
                    for(Answer answer : mAnswers){
                        if(answer.isWasChoosen()){
                            if(answer.isAnswerTrue()){
                                mRelativeLayoutQuestion.setBackgroundResource(R.drawable.not_selected_true);
                            }
                            else {
                                mRelativeLayoutQuestion.setBackgroundResource(R.drawable.not_selected_false);
                            }
                        }
                    }
                } else if (!question.isWasAnswered()){
                    mRelativeLayoutQuestion.setBackgroundResource(R.drawable.regular);
                    mQuestionCount.setTextColor(Color.BLACK);
                }
            }
        }

        @Override
        public void onClick(View view) {
            for(Question question : mQuestions){
                question.setWasSelected(false);
            }
            mQuestion.setWasSelected(true);
            updateAnswerUI(mQuestion);
            updateQuestionUI(position);
        }
    }

    private class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder>{

        private List<Question> mQuestions;

        public QuestionAdapter(List<Question> questions){
            mQuestions = questions;
        }

        @Override
        public QuestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(TestActivity.this);
            return new QuestionHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(QuestionHolder holder, int position) {
            Question question = mQuestions.get(position);
            holder.bind(question, position);
        }

        @Override
        public int getItemCount() {
            return mQuestions.size();
        }

        public void selectQuestion(){
            Question mQuestion = mQuestions.get(0);
            for(Question question : mQuestions){
                if(question.isWasSelected()){
                    mQuestion = question;
                }
            }
            updateAnswerUI(mQuestion);

        }

        public void selectDefault(){
            for(Question question : mQuestions){
                question.setWasSelected(false);
            }
            Question mQuestion = mQuestions.get(0);
            mQuestion.setWasSelected(true);
            updateAnswerUI(mQuestion);
        }

        public void selectNext(Question question){
            question.setWasSelected(true);
            updateQuestionUI(mQuestions.indexOf(question));
            updateAnswerUI(question);
        }

    }

    protected class AnswerHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private TextView mAnswerTextView;
        private Answer mAnswer;
        private Question mQuestion;
        private RelativeLayout mRelativeLayoutAnswer;


        public AnswerHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_answer, parent, false));
            mAnswerTextView = (TextView) itemView.findViewById(R.id.answer_text);
            mRelativeLayoutAnswer = (RelativeLayout) itemView.findViewById(R.id.relative_layout_answer);
            itemView.setOnClickListener(this);
        }

        public void bind(Question question, Answer answer){
            mAnswer = answer;
            mQuestion = question;
            mAnswerTextView.setText(mAnswer.getTextAnswer());
            if(mQuestion.isWasAnswered()){
                if(mAnswer.isWasChoosen()) {
                    if (mAnswer.isAnswerTrue()) {
                        mRelativeLayoutAnswer.setBackgroundResource(R.drawable.not_selected_true);
                        mAnswerTextView.setTextColor(Color.WHITE);//Color.rgb(0, 102, 0)
                    } else if (!mAnswer.isAnswerTrue()) {
                        mRelativeLayoutAnswer.setBackgroundResource(R.drawable.not_selected_false);
                        mAnswerTextView.setTextColor(Color.WHITE);//rgb(230, 0, 0)
                    }
                } else {
                    if(mAnswer.isAnswerTrue()){
                        mRelativeLayoutAnswer.setBackgroundResource(R.drawable.not_selected_true);
                        mAnswerTextView.setTextColor(Color.WHITE);//Color.rgb(0, 102, 0)
                    }
                }
            }
        }

        @Override
        public void onClick(View view) {
            if(!mIsResume) {
                if (!mQuestion.isWasAnswered()) {
                    mQuestion.setWasAnswered(true);
                    mAnswer.setWasChoosen(true);
                    if(result()){
                        nextQuestion(mQuestion);
                    }else{
                        updateQuestionUI(mPosition);
                        updateAnswerUI(mQuestion);
                    }
                }
            }
        }
    }

    public void nextQuestion(Question question){

        int position = 0;
        int i = 0;
        for (Question questions: mQuestions){
            if(questions.isWasAnswered()){
                i++;
            }
            if(i==mQuestions.size()){
                updateAnswerUI(questions);
                updateQuestionUI(mPosition+1);
                result();
                return;
            }
        }
        question.setWasSelected(false);

        for (Question questions: mQuestions){
            if(question.getId()==questions.getId()){
                position = mQuestions.indexOf(questions);
                if(position<mQuestions.size()-1){
                    position++;
                }else{
                    position=0;
                }
            }
        }

        i = 0;
        while (i<mQuestions.size()) {
            for(Question questions: mQuestions){
                if(mQuestions.indexOf(questions) == position && questions.isWasAnswered()){
                    if(position<mQuestions.size()-1){
                        position++;
                    }else{
                        position=0;
                    }
                }
                else if (position == mQuestions.indexOf(questions) && !questions.isWasAnswered()){
                    mAdapterQuestion.selectNext(questions);
                    return;
                }
            }
            i++;
        }

    }

    private class AnswerAdapter extends RecyclerView.Adapter<AnswerHolder>{

        private List<Answer> mAnswers;
        private Question mQuestion;

        public AnswerAdapter(Question question, List<Answer> answers){
            mAnswers = answers;
            mQuestion = question;
        }

        @Override
        public AnswerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(TestActivity.this);
            return new AnswerHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(AnswerHolder holder, int position) {
            Answer mAnswer = mAnswers.get(position);
            holder.bind(mQuestion, mAnswer);
        }

        @Override
        public int getItemCount() {
            return mAnswers.size();
        }
    }

    private void countdownTimer(){
        mCountDownTimer = new CountDownTimer(mTimerLast, 1000) {

            public void onTick(long millisUntilFinished) {
                if(mTime){
                    mCountDownTimer.cancel();
                    message(mTimerLast);
                } else {
                    mTimerLast = (int) millisUntilFinished;
                    message((int) millisUntilFinished);
                }
            }

            public void onFinish() {
                message(0);
                mTime=true;
                result();
            }

            void message(int time){

                Message m = new Message();
                Bundle b = new Bundle();
                b.putInt("data", time);
                m.setData(b);
                mHandler.sendMessage(m);
            }

        }.start();
    }

    public boolean result(){
        int questionCount = 0;
        int answerIsTrueCount = 0;
        int answerIsFalseCount = 0;
        for (Question question:mQuestions){
            if(question.isWasAnswered()){
                questionCount++;
            }
            mAnswers = question.getAnswer();
            for(Answer answer : mAnswers) {
                if (answer.isAnswerTrue() && answer.isWasChoosen()) {
                    answerIsTrueCount++;
                }
                if (!answer.isAnswerTrue() && answer.isWasChoosen()){
                    answerIsFalseCount++;
                }
            }
        }
        if (questionCount==mQuestions.size()||mTime||answerIsFalseCount>2) {
            mIsResume = true;
            mCountDownTimer.cancel();

            ResultFragment dialog = ResultFragment.newInstance(answerIsTrueCount);
            dialog.show(getFragmentManager(), DIALOG_RESULT);
            return false;
        }
        return true;
    }

    public void updateQuestionUI(int position){
        mAdapterQuestion = new QuestionAdapter(mQuestions);
        mQuestionRecycler.setAdapter(mAdapterQuestion);

        int centerOfScreen = mQuestionRecycler.getWidth()/2;
        centerOfScreen=centerOfScreen-52;

        if (position == 0) {
            mLinearLayoutManager.scrollToPosition(position);
        } else {
            mLinearLayoutManager.scrollToPositionWithOffset(position, centerOfScreen);//52
        }

        mPosition=position;
    }

    public void updateAnswerUI(final Question question){
        List<Answer> answers =  question.getAnswer();
        mQuestion = question;
        mQuestionTextView.setText(question.getTextQuestion());
        mQuestionImage.setImageResource(R.drawable.empty);

        mQuestionImage.setVisibility(!question.isImageEmpty() ? View.VISIBLE : View.GONE);

        mQuestionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogImage dialog = DialogImage.newInstance(question.getImageSource());
                dialog.show(getFragmentManager(), DIALOG_IMAGE);
            }
        });

        if(!question.isImageEmpty()){
            Picasso.get()
                    .load(question.getImageSource())
                    .placeholder(R.drawable.empty)
                    .into(mQuestionImage);
        }

        mAdapterAnswer = new AnswerAdapter(question, answers);
        mAnswerRecycler.setAdapter(mAdapterAnswer);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESTART){
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

}