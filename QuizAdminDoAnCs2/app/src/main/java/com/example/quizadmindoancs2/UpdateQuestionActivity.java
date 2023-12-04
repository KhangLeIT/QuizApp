package com.example.quizadmindoancs2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.quizadmindoancs2.model.QuestionModel;
import com.example.quizadmindoancs2.query.DbQuery;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class UpdateQuestionActivity extends AppCompatActivity {
    private EditText optionA, optionB, optionC, optionD;
    private EditText questionTest, answerQues;
    private Button UDQuesTest, cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_question);

        Toolbar toolbar = findViewById(R.id.UD_qes_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Question No " + String.valueOf(DbQuery.g_select_ques_index + 1));

        optionA = findViewById(R.id.UD_ques_optionA);
        optionB = findViewById(R.id.UD_ques_optionB);
        optionC = findViewById(R.id.UD_ques_optionC);
        optionD = findViewById(R.id.UD_ques_optionD);
        questionTest = findViewById(R.id.UD_question);
        answerQues = findViewById(R.id.UD_ques_answer);
        UDQuesTest = findViewById(R.id.UDQues_btn);
        cancel = findViewById(R.id.cancel_UDQues_btn);


        questionTest.setText(DbQuery.g_quesList.get(DbQuery.g_select_ques_index).getQuestion());
        optionA.setText(DbQuery.g_quesList.get(DbQuery.g_select_ques_index).getOptionA());
        optionB.setText(DbQuery.g_quesList.get(DbQuery.g_select_ques_index).getOptionB());
        optionC.setText(DbQuery.g_quesList.get(DbQuery.g_select_ques_index).getOptionC());
        optionD.setText(DbQuery.g_quesList.get(DbQuery.g_select_ques_index).getOptionD());

        answerQues.setText(String.valueOf(DbQuery.g_quesList.get(DbQuery.g_select_ques_index).getCorrectAns()));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(UpdateQuestionActivity.this, QuestionActivity.class);
                startActivity(it);
                finish();
            }
        });

        UDQuesTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = questionTest.getText().toString();
                String A = optionA.getText().toString();
                String B = optionB.getText().toString();
                String C = optionC.getText().toString();
                String D = optionD.getText().toString();
                if(question.isEmpty())
                {
                    questionTest.setError("Enter Question");
                    return;
                }

                if(A.isEmpty())
                {
                    optionA.setError("Enter A");
                    return;
                }
                if(B.isEmpty())
                {
                    optionB.setError("Enter B");
                    return;
                }
                if(C.isEmpty())
                {
                    optionC.setError("Enter C");
                    return;
                }
                if(D.isEmpty())
                {
                    optionD.setError("Enter D");
                    return;
                }
                if(answerQues.getText().toString().isEmpty())
                {
                    answerQues.setError("Enter Answer");
                    return;
                }
                int answer = Integer.parseInt(answerQues.getText().toString());
                if(answer <0 || answer > 4)
                {
                    answerQues.setError("answer is  1 - A, 2 - B, 3 - C, 4 - D");
                    return;
                }

                AlertDialog dialog = new AlertDialog.Builder(UpdateQuestionActivity.this)
                        .setTitle("Update Question No " +String.valueOf(DbQuery.g_select_ques_index))
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                UDQuestionOnTest(question, A, B, C, D, answer);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                dialog.getButton(dialog.BUTTON_POSITIVE).setBackgroundColor(Color.BLUE);
                dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                dialog.getButton(dialog.BUTTON_NEGATIVE).setBackgroundColor(Color.GREEN);
                dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 250, 0);
                dialog.getButton(dialog.BUTTON_NEGATIVE).setLayoutParams(params);

            }
        });

    }
    private void UDQuestionOnTest(String question, String a, String b, String c, String d, int answer)
    {
        FirebaseFirestore g_firestore = FirebaseFirestore.getInstance();
        Map<String, Object> quesData = new ArrayMap<>();
        quesData.put("QUESTION", question);
        quesData.put("A", a);
        quesData.put("B", b);
        quesData.put("C", c);
        quesData.put("D", d);
        quesData.put("ANSWER", answer);
//        String idCategory = DbQuery.g_catList.get(DbQuery.g_select_cat_index).getDocID();
//        quesData.put("CATEGORY", idCategory);
//        String idTest = DbQuery.g_testList.get(DbQuery.g_select_test_index).getTestID();
//        quesData.put("TEST",idTest);
        String doc_id = DbQuery.g_quesList.get(DbQuery.g_select_ques_index).getqID();
        g_firestore.collection("QUESTION").document(doc_id)
                .update(quesData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //DbQuery.g_quesList.add(new QuestionModel(doc_id, question, a, b, c, d, answer));
                        Intent it = new Intent(UpdateQuestionActivity.this, QuestionActivity.class);
                        startActivity(it);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateQuestionActivity.this, "add error", Toast.LENGTH_SHORT).show();

                    }
                });

    }
}