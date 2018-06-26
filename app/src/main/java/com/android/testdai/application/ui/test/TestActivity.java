package com.android.testdai.application.ui.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

import com.android.testdai.application.model.Question;
import com.android.testdai.application.model.Question.Answer;

import com.android.testdai.application.ui.dialog.DialogImage;
import com.android.testdai.application.ui.dialog.DialogResult;
import com.android.testdai.application.ui.test.abstraction.ITestView;
import com.android.testdai.util.ProgressUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class TestActivity extends AppCompatActivity implements ITestView{

    public static final String TAG = "TestActivity";

    private static final int SEND_RESULT = 0;
    private static final int RESTART = 1;
    private static final String DIALOG_RESULT = "DialogResult";
    private static final String DIALOG_IMAGE = "DialogImage";

    private RecyclerView mQuestionRecycler;
    private RecyclerView mAnswerRecycler;
    private TextView mQuestionTextView;
    private ImageView mQuestionImage;
    private LinearLayoutManager mLinearLayoutManager;
    private AdView mAdView;

    private TestPresenter presenter;
    private Handler handler;
    private ProgressUtil progressUtil;

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

        progressUtil = new ProgressUtil(this);

        String category = (String) getIntent().getSerializableExtra(EXTRA_CATEGORY);
        presenter = new TestPresenter(this, category);

        mQuestionRecycler = (RecyclerView) findViewById(R.id.question_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(TestActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mQuestionRecycler.setLayoutManager(mLinearLayoutManager);

        mAnswerRecycler = (RecyclerView) findViewById(R.id.answer_recycler_view);
        mAnswerRecycler.setLayoutManager(new LinearLayoutManager(TestActivity.this));

        mQuestionTextView = (TextView) findViewById(R.id.text_question);
        mQuestionImage = (ImageView) findViewById(R.id.question_image);

        MobileAds.initialize(this,
                "ca-app-pub-5104532168407959~6750661013");

        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setVisibility(View.VISIBLE);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("5265E0E103CEDC51B6E5157D84578C60")
                .build();
        mAdView.loadAd(adRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fragment_question, menu);
        final MenuItem mCountdown = menu.findItem(R.id.countdown);
        handler = new Handler() {
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
        presenter.attachView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
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
        }, 3000);

    }


    private class ListQuestionHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private TextView mQuestionCount;
        private RelativeLayout mRelativeLayoutQuestion;


        public ListQuestionHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_question, parent, false));
            mQuestionCount = (TextView) itemView.findViewById(R.id.number_question);
            mRelativeLayoutQuestion = (RelativeLayout) itemView.findViewById(R.id.relative_layout_question);
            itemView.setOnClickListener(this);
        }

        public void bind(Question question){
            int position = this.getAdapterPosition()+1;
            mQuestionCount.setText(String.valueOf(position));

            List<Answer> answers = question.getAnswers();

            if(question.isSelected()){
                mRelativeLayoutQuestion.setBackgroundResource(R.drawable.selected);
                if(question.isAnswered()){
                    for(Answer answer : answers){
                        if(answer.isSelected()){
                            if(answer.isAnswerTrue()){
                                mRelativeLayoutQuestion.setBackgroundResource(R.drawable.selected_true);
                            }
                            else {
                                mRelativeLayoutQuestion.setBackgroundResource(R.drawable.selected_false);
                            }
                            mQuestionCount.setTextColor(Color.WHITE);
                        }
                    }
                } else if (!question.isAnswered()){
                    mQuestionCount.setTextColor(Color.BLACK);
                }
            } else {
                if(question.isAnswered()){
                    mQuestionCount.setTextColor(Color.WHITE);
                    for(Answer answer : answers){
                        if(answer.isSelected()){
                            if(answer.isAnswerTrue()){
                                mRelativeLayoutQuestion.setBackgroundResource(R.drawable.not_selected_true);
                            }
                            else {
                                mRelativeLayoutQuestion.setBackgroundResource(R.drawable.not_selected_false);
                            }
                        }
                    }
                } else if (!question.isAnswered()){
                    mRelativeLayoutQuestion.setBackgroundResource(R.drawable.regular);
                    mQuestionCount.setTextColor(Color.BLACK);
                }
            }
        }

        @Override
        public void onClick(View view) {
            presenter.selectQuestion(this.getAdapterPosition());
        }

    }

    private class ListQuestionAdapter extends RecyclerView.Adapter<ListQuestionHolder>{

        private List<Question> mQuestions;

        public ListQuestionAdapter(List<Question> questions){
            mQuestions = questions;
        }

        @Override
        public ListQuestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(TestActivity.this);
            return new ListQuestionHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ListQuestionHolder holder, int position) {
            Question question = mQuestions.get(position);
            holder.bind(question);
        }

        @Override
        public int getItemCount() {
            return mQuestions.size();
        }

    }

    protected class QuestionHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private TextView mAnswerTextView;
        private RelativeLayout mRelativeLayoutAnswer;


        public QuestionHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_answer, parent, false));
            mAnswerTextView = (TextView) itemView.findViewById(R.id.answer_text);
            mRelativeLayoutAnswer = (RelativeLayout) itemView.findViewById(R.id.relative_layout_answer);
            itemView.setOnClickListener(this);
        }

        public void bind(Question question, Answer answer){

            mAnswerTextView.setText(answer.getTextAnswer());

            if(question.isAnswered()){
                if(answer.isSelected()) {
                    if (answer.isAnswerTrue()) {
                        mRelativeLayoutAnswer.setBackgroundResource(R.drawable.not_selected_true);
                        mAnswerTextView.setTextColor(Color.WHITE);
                    } else if (!answer.isAnswerTrue()) {
                        mRelativeLayoutAnswer.setBackgroundResource(R.drawable.not_selected_false);
                        mAnswerTextView.setTextColor(Color.WHITE);
                    }
                } else {
                    if(answer.isAnswerTrue()){
                        mRelativeLayoutAnswer.setBackgroundResource(R.drawable.not_selected_true);
                        mAnswerTextView.setTextColor(Color.WHITE);
                    }
                }
            }

        }

        @Override
        public void onClick(View view) {

            presenter.selectAnswer(this.getAdapterPosition());

        }
    }

    private class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder>{

        private List<Answer> mAnswers;
        private Question mQuestion;

        public QuestionAdapter(Question question){
            mQuestion = question;
            mAnswers = question.getAnswers();
        }

        @NonNull
        @Override
        public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(TestActivity.this);
            return new QuestionHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
            Answer mAnswer = mAnswers.get(position);
            holder.bind(mQuestion, mAnswer);
        }

        @Override
        public int getItemCount() {
            return mAnswers.size();
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
        mQuestionRecycler.setAdapter(mAdapterQuestion);

        scrollToCenter(position);

        updateQuestion(questions.get(position));

    }

    private void scrollToCenter(int position){

        int centerOfScreen = mQuestionRecycler.getWidth()/2;
        centerOfScreen=centerOfScreen-64;

        if (position == 0) {
            mLinearLayoutManager.scrollToPosition(position);
        } else {
            mLinearLayoutManager.scrollToPositionWithOffset(position, centerOfScreen);//52
        }

    }

    @Override
    public void updateQuestion(final Question question) {

        mQuestionTextView.setText(question.getTextQuestion());
        mQuestionImage.setImageResource(R.drawable.empty);

        mQuestionImage.setVisibility(question.isImageEmpty() ? View.VISIBLE : View.GONE);

        mQuestionImage.setOnClickListener(new View.OnClickListener() {
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
                    .into(mQuestionImage, new Callback() {
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
        mAnswerRecycler.setAdapter(mAdapterAnswer);

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