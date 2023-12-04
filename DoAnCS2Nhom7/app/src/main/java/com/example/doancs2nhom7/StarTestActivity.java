package com.example.doancs2nhom7;

import static com.example.doancs2nhom7.query.DbQuery.g_catList;
import static com.example.doancs2nhom7.query.DbQuery.g_quesList;
import static com.example.doancs2nhom7.query.DbQuery.loadQuestions;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doancs2nhom7.Mylistener.MyCompleteListener;
import com.example.doancs2nhom7.query.DbQuery;

public class StarTestActivity extends AppCompatActivity {
    private TextView catName, testNo, totalQ, bestScore, time;
    private Button startTestB;
    private ImageView backB;

    private ProgressDialog progressDailog;
    private TextView dialogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_test);

        // progressDailog show in LoginActivity
        progressDailog = new ProgressDialog(StarTestActivity.this);
        progressDailog.setContentView(R.layout.dailog_layout);
        progressDailog.setCancelable(false);
        progressDailog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogText = progressDailog.findViewById(R.id.dialog_text);
        progressDailog.setMessage("Loading...");

        init();
        loadQuestions(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                setData();
                progressDailog.dismiss();
            }

            @Override
            public void onFailure() {
                progressDailog.dismiss();
                Toast.makeText(StarTestActivity.this, "Something went wrong! Please Try Again Later !", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void init()
    {
        catName = findViewById(R.id.st_cat_name);
        testNo = findViewById(R.id.st_test_no);
        totalQ = findViewById(R.id.st_total_ques);
        bestScore = findViewById(R.id.st_best_score);
        time = findViewById(R.id.st_time);
        startTestB = findViewById(R.id.start_testB);
        backB = findViewById(R.id.st_backB);

        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StarTestActivity.this.finish();
            }
        });

        startTestB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(g_quesList.size() <= 0){
                    Toast.makeText(StarTestActivity.this, "This quiz has no questions yet!, Please come back later !", Toast.LENGTH_SHORT).show();
                } else{
                Intent i = new Intent(StarTestActivity.this, QuestionsActivity.class);
                startActivity(i);
                finish();
                }
            }
        });
    }

    private void setData()
    {
        catName.setText(g_catList.get(DbQuery.g_select_cat_index).getName());
        testNo.setText("Test No. " + String.valueOf(DbQuery.g_select_test_index + 1));
        totalQ.setText(String.valueOf(DbQuery.g_quesList.size()));
        bestScore.setText(String.valueOf(DbQuery.g_testList.get(DbQuery.g_select_test_index).getTopScore()));
        time.setText(String.valueOf(DbQuery.g_testList.get(DbQuery.g_select_test_index).getTime()));
    }
}