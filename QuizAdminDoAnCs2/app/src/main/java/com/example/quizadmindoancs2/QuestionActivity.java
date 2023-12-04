package com.example.quizadmindoancs2;

import static com.example.quizadmindoancs2.query.DbQuery.g_quesList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizadmindoancs2.Mylistener.MyCompleteListener;
import com.example.quizadmindoancs2.adapter.QuestionAdapter;
import com.example.quizadmindoancs2.query.DbQuery;

public class QuestionActivity extends AppCompatActivity {
    private RecyclerView questionsView;
    QuestionAdapter quesAdapter;
    private TextView total_ques;
    private Button addQuestionB, resetB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Toolbar toolbar = findViewById(R.id.q_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Question of Test " + String.valueOf(DbQuery.g_select_test_index + 1) );
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        total_ques = findViewById(R.id.total_ques);

        addQuestionB = findViewById(R.id.addQuesB);
        resetB = findViewById(R.id.resetQuesB);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        questionsView = findViewById(R.id.q_recycler_view);

        loadQuestion();


        addQuestionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(QuestionActivity.this, AddQuestionActivity.class);
                startActivity(it);
                finish();
            }
        });

        resetB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadQuestion();
            }
        });


    }

    private void loadQuestion()
    {
        DbQuery.loadQuestionTest(new MyCompleteListener() {
            @Override
            public void onSuccess() {

                quesAdapter = new QuestionAdapter(g_quesList);
                questionsView.setAdapter(quesAdapter);

                total_ques.setText("Total Question: " + String.valueOf(g_quesList.size() ));

                LinearLayoutManager layoutManager = new LinearLayoutManager(QuestionActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                questionsView.setLayoutManager(layoutManager);
            }
            @Override
            public void onFailure() {
                Toast.makeText(QuestionActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            QuestionActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


}