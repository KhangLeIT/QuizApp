package com.example.doancs2nhom7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doancs2nhom7.Mylistener.MyCompleteListener;
import com.example.doancs2nhom7.fragment.LeaderBoardFragment;
import com.example.doancs2nhom7.model.QuestionModel;
import com.example.doancs2nhom7.query.DbQuery;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class ScoreActivity extends AppCompatActivity {

    private TextView scoreTV, timeTV, totalQTV, correctQTV, wrongQTV, unattemptedTV;
    private Button reAttemptB, viewAnsB;
    private long timeTaken;
    private Toolbar toolbar;
    private ProgressDialog progressDailog;
    private TextView dialogText;
    private int finalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Result");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // progressDailog show in LoginActivity
        progressDailog = new ProgressDialog(ScoreActivity.this);
        progressDailog.setContentView(R.layout.dailog_layout);
        progressDailog.setCancelable(false);
        progressDailog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogText = progressDailog.findViewById(R.id.dialog_text);
        progressDailog.setMessage("Loading...");


        init();
        loadData();
        setBookMarks();

//        DocumentReference scoreList = FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid())
//                .collection("USER_DATA").document("MY_SCORES");



        viewAnsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreActivity.this, AnswersActivity.class);
                startActivity(intent);

            }
        });

        reAttemptB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reAttempt();
            }
        });

        saveResult();


    }
    private void init()
    {
        scoreTV = findViewById(R.id.score);
        timeTV = findViewById(R.id.time);
        totalQTV = findViewById(R.id.totalQ);
        correctQTV = findViewById(R.id.correctQ);
        wrongQTV = findViewById(R.id.wrongQ);
        unattemptedTV = findViewById(R.id.un_attemptedQ);
        reAttemptB = findViewById(R.id.reattemptB);
        viewAnsB = findViewById(R.id.view_answerB);

    }
    private void loadData()
    {
        int correctQ = 0, wrongQ = 0, unattemptQ = 0;
        for (int i=0;i< DbQuery.g_quesList.size();i++)
        {
            if (DbQuery.g_quesList.get(i).getSelectAns()==-1)
            {
                unattemptQ ++;
            }
            else
            {
                if (DbQuery.g_quesList.get(i).getSelectAns()== DbQuery.g_quesList.get(i).getCorrectAns())
                {
                    correctQ++;
                }
                else
                {
                    wrongQ++;
                }
            }
        }

        correctQTV.setText(String.valueOf(correctQ));
        wrongQTV.setText(String.valueOf(wrongQ));
        unattemptedTV.setText(String.valueOf(unattemptQ));

        totalQTV.setText(String.valueOf(DbQuery.g_quesList.size()));

        // score rank
        finalScore = (correctQ*100)/DbQuery.g_quesList.size();

        scoreTV.setText(String.valueOf(finalScore));

        timeTaken = getIntent().getLongExtra("TIME_TAKEN", 0);

        String time = String.format("%02d:%02d min",
                TimeUnit.MILLISECONDS.toMinutes(timeTaken),
                TimeUnit.MILLISECONDS.toSeconds(timeTaken) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeTaken))
        );
        timeTV.setText(time);

    }

    private void reAttempt()
    {
        for (int i=0; i<DbQuery.g_quesList.size();i++)
        {
            DbQuery.g_quesList.get(i).setSelectAns(-1);
            DbQuery.g_quesList.get(i).setStatus(DbQuery.NOT_VISITED);

        }
        Intent intent = new Intent(ScoreActivity.this, StarTestActivity.class);
        startActivity(intent);
        finish();
    }
    private void saveResult()
    {
        DbQuery.saveResult(finalScore, new MyCompleteListener() {
            @Override
            public void onSuccess() {
                progressDailog.dismiss();
            }

            @Override
            public void onFailure() {
                Toast.makeText(ScoreActivity.this, "Something went wrong! Please try again later !", Toast.LENGTH_SHORT).show();
                progressDailog.dismiss();
            }
        });
    }

    private void setBookMarks(){

        for (int i = 0; i< DbQuery.g_quesList.size(); i++)
        {
            QuestionModel question = DbQuery.g_quesList.get(i);

            if (question.isBookmarked())
            {
                if (!DbQuery.g_bmIdList.contains(question.getqID()))
                {
                    DbQuery.g_bmIdList.add(question.getqID());
                    DbQuery.myProfile.setBookmarksCount(DbQuery.g_bmIdList.size());
                }

            }
            else
            {
                if (DbQuery.g_bmIdList.contains(question.getqID()))
                {
                    DbQuery.g_bmIdList.remove(question.getqID());
                    DbQuery.myProfile.setBookmarksCount(DbQuery.g_bmIdList.size());
                }

            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            ScoreActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}