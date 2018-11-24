package com.android.testdai.application.ui.activities.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.testdai.R;

import com.android.testdai.application.ui.activities.test.model.Question;
import com.android.testdai.application.ui.activities.test.model.Answer;

import com.android.testdai.application.ui.abstractions.AbstractActivity;
import com.android.testdai.application.ui.dialogs.DialogImage;
import com.android.testdai.application.ui.dialogs.DialogResult;
import com.android.testdai.application.ui.activities.test.abstraction.ITestView;
import com.android.testdai.di.DIProvider;
import com.android.testdai.util.AnalyticUtil;
import com.android.testdai.util.ProgressUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class TestActivity extends AbstractActivity<TestPresenter> implements ITestView{


    private static final int RESTART = 1;
    private static final String DIALOG_RESULT = "DialogResult";
    private static final String DIALOG_IMAGE = "DialogImage";

    private RecyclerView questionRecycler;
    private RecyclerView answerRecycler;
    private TextView textQuestion;
    private ImageView imageQuestion;
    private LinearLayoutManager linearLayoutManager;
    private AdView adView;

    private Handler handler;
    private ProgressUtil progressUtil;


    @Override
    protected void injectDependencies() {
        DIProvider.getActivitiesComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        AnalyticUtil.getInstance(this).logScreenEvent(getClass().getSimpleName());

        progressUtil = new ProgressUtil(this);

        questionRecycler = (RecyclerView) findViewById(R.id.question_recycler_view);
        linearLayoutManager = new LinearLayoutManager(TestActivity.this, LinearLayoutManager.HORIZONTAL, false);
        questionRecycler.setLayoutManager(linearLayoutManager);

        answerRecycler = (RecyclerView) findViewById(R.id.answer_recycler_view);
        answerRecycler.setLayoutManager(new LinearLayoutManager(TestActivity.this));

        textQuestion = (TextView) findViewById(R.id.text_question);
        imageQuestion = (ImageView) findViewById(R.id.question_image);

        adView = (AdView) findViewById(R.id.adView);
        adView.setVisibility(View.VISIBLE);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("9489E98FDC7D70F02084422B7D2B18C3")
                .build();
        adView.loadAd(adRequest);

    }

    @SuppressLint("HandlerLeak")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fragment_question, menu);
        final MenuItem mCountdown = menu.findItem(R.id.countdown);
        handler = new Handler() {
            @SuppressLint("SimpleDateFormat")
            public void handleMessage(Message msg) {
                int message = msg.getData().getInt("data");
                mCountdown.setTitle(new SimpleDateFormat("mm:ss").format(new Date(message)));
            };
        };

        return true;

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        adView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        adView.destroy();
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
        }, 3000);

    }


    private class ListQuestionHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private TextView questionCount;
        private RelativeLayout questionRelativeLayout;


        public ListQuestionHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_question, parent, false));
            questionCount = (TextView) itemView.findViewById(R.id.number_question);
            questionRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_layout_question);
            itemView.setOnClickListener(this);
        }

        public void bind(Question question){
            int position = this.getAdapterPosition()+1;
            questionCount.setText(String.valueOf(position));

            List<Answer> answers = question.getAnswers();

            if(question.isSelected()){
                questionRelativeLayout.setBackgroundResource(R.drawable.selected);
                if(question.isAnswered()){
                    for(Answer answer : answers){
                        if(answer.isSelected()){
                            if(answer.isAnswerTrue()){
                                questionRelativeLayout.setBackgroundResource(R.drawable.selected_true);
                            }
                            else {
                                questionRelativeLayout.setBackgroundResource(R.drawable.selected_false);
                            }
                            questionCount.setTextColor(Color.WHITE);
                        }
                    }
                } else if (!question.isAnswered()){
                    questionCount.setTextColor(Color.BLACK);
                }
            } else {
                if(question.isAnswered()){
                    questionCount.setTextColor(Color.WHITE);
                    for(Answer answer : answers){
                        if(answer.isSelected()){
                            if(answer.isAnswerTrue()){
                                questionRelativeLayout.setBackgroundResource(R.drawable.not_selected_true);
                            }
                            else {
                                questionRelativeLayout.setBackgroundResource(R.drawable.not_selected_false);
                            }
                        }
                    }
                } else if (!question.isAnswered()){
                    questionRelativeLayout.setBackgroundResource(R.drawable.regular);
                    questionCount.setTextColor(Color.BLACK);
                }
            }
        }

        @Override
        public void onClick(View view) {
            presenter.selectQuestion(this.getAdapterPosition());
        }

    }

    private class ListQuestionAdapter extends RecyclerView.Adapter<ListQuestionHolder>{

        private List<Question> listQuestions;

        public ListQuestionAdapter(List<Question> questions){
            listQuestions = questions;
        }

        @NonNull
        @Override
        public ListQuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(TestActivity.this);
            return new ListQuestionHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ListQuestionHolder holder, int position) {
            Question question = listQuestions.get(position);
            holder.bind(question);
        }

        @Override
        public int getItemCount() {
            return listQuestions.size();
        }

    }

    protected class QuestionHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private TextView textAnswer;
        private RelativeLayout answerRelativeLayout;


        public QuestionHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_answer, parent, false));
            textAnswer = (TextView) itemView.findViewById(R.id.answer_text);
            answerRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_layout_answer);
            itemView.setOnClickListener(this);
        }

        public void bind(Question question, Answer answer){

            textAnswer.setText(answer.getTextAnswer());

            if(question.isAnswered()){
                if(answer.isSelected()) {
                    if (answer.isAnswerTrue()) {
                        answerRelativeLayout.setBackgroundResource(R.drawable.not_selected_true);
                        textAnswer.setTextColor(Color.WHITE);
                    } else if (!answer.isAnswerTrue()) {
                        answerRelativeLayout.setBackgroundResource(R.drawable.not_selected_false);
                        textAnswer.setTextColor(Color.WHITE);
                    }
                } else {
                    if(answer.isAnswerTrue()){
                        answerRelativeLayout.setBackgroundResource(R.drawable.not_selected_true);
                        textAnswer.setTextColor(Color.WHITE);
                    }
                }
            }else {
                if(answer.isChosen()){
                    answerRelativeLayout.setBackgroundResource(R.drawable.selected);
                    textAnswer.setTextColor(Color.BLACK);
                }
            }

        }

        @Override
        public void onClick(View view) {

            presenter.selectAnswer(this.getAdapterPosition());

        }
    }

    private class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder>{

        //private List<Answer> listAnswers;
        private Question question;

        public QuestionAdapter(Question question){
            this.question = question;
            //listAnswers = question.getAnswers();
        }

        @NonNull
        @Override
        public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(TestActivity.this);
            return new QuestionHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
            Answer mAnswer = question.getAnswers().get(position);
            holder.bind(question, mAnswer);
        }

        @Override
        public int getItemCount() {
            return question.getAnswers().size();
        }
    }

    @Override
    public void startLoading() {
        progressUtil.showProgress();
    }

    @Override
    public void stopLoading() {
        progressUtil.hideProgress();
    }

    @Override
    public void updateListQuestion(int position, List<Question> questions) {

        ListQuestionAdapter mAdapterQuestion = new ListQuestionAdapter(questions);
        questionRecycler.setAdapter(mAdapterQuestion);

        scrollToCenter(position);

        updateQuestion(questions.get(position));

    }

    private void scrollToCenter(int position){

        int centerOfScreen = questionRecycler.getWidth()/2;
        centerOfScreen=centerOfScreen-64;

        if (position == 0) {
            linearLayoutManager.scrollToPosition(position);
        } else {
            linearLayoutManager.scrollToPositionWithOffset(position, centerOfScreen);//52
        }

    }

    @Override
    public void updateQuestion(final Question question) {

        textQuestion.setText(question.getTextQuestion());
        imageQuestion.setImageResource(R.drawable.empty);

        imageQuestion.setVisibility(question.isImageEmpty() ? View.VISIBLE : View.GONE);

        imageQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogImage dialog = DialogImage.newInstance(question.getImageSource());
                dialog.show(getFragmentManager(), DIALOG_IMAGE);
            }
        });

        if(question.isImageEmpty()){
            Picasso.get()
                    .load(question.getImageSource())
                    .placeholder(R.drawable.empty)
                    .into(imageQuestion, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.error_img_loading, Toast.LENGTH_LONG);
                    TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                    if( v != null) v.setGravity(Gravity.CENTER);
                    toast.show();
                }
            });
        }

        QuestionAdapter mAdapterAnswer = new QuestionAdapter(question);
        answerRecycler.setAdapter(mAdapterAnswer);

    }

    @Override
    public void updateTimer(int time) {

        if(handler!=null){
            Message m = new Message();
            Bundle b = new Bundle();
            b.putInt("data", time);
            m.setData(b);
            handler.sendMessage(m);
        }

    }

    @Override
    public void showResultDialog(int result) {

        DialogResult dialog = DialogResult.newInstance(result);
        dialog.show(getFragmentManager(), DIALOG_RESULT);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESTART){
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

}