package com.example.quizadmindoancs2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.window.SplashScreen;

import com.example.quizadmindoancs2.Mylistener.MyCompleteListener;
import com.example.quizadmindoancs2.adapter.TestAdapter;
import com.example.quizadmindoancs2.query.DbQuery;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class TestActivity extends AppCompatActivity {
    private RecyclerView testView;
    private Button addTestB, resetB, dialogAddB, dialogCancel;
    private TestAdapter adapter;
    private Toolbar toolbar;
    private ProgressDialog progressDailog;
    private AlertDialog.Builder addTestDialog;
    private EditText dialogTestTime;
    private FirebaseFirestore firestore;
    private boolean addTestFT = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        toolbar = findViewById(R.id.toolbar_test);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Test " + DbQuery.g_catList.get(DbQuery.g_select_cat_index).getName());
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();

        // progressDailog show in LoginActivity
        progressDailog = new ProgressDialog(TestActivity.this);
        progressDailog.setContentView(R.layout.dailog_layout);
        progressDailog.setCancelable(false);
        progressDailog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressDailog.setMessage("Loading...");
        progressDailog.show();

        addTestDialog = new AlertDialog.Builder(TestActivity.this);
        View customLayout = getLayoutInflater().inflate(R.layout.add_test_category, null);
        addTestDialog.setView(customLayout).create();
        addTestDialog.setTitle("Add new Category");

        dialogAddB = customLayout.findViewById(R.id.add_test_btn);
        dialogTestTime = customLayout.findViewById(R.id.add_test_time);
        dialogCancel = customLayout.findViewById(R.id.add_test_cancel);

        testView = findViewById(R.id.test_recycler_view);
        addTestB = findViewById(R.id.addTestCatB);
        resetB = findViewById(R.id.resetTestB);


        addTestB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(addTestFT == true)
//                {
//                    Intent it = new Intent(TestActivity.this, CategoryActivity.class);
//                    startActivity(it);
//                    finish();
//                }
                if(customLayout.getParent() != null) {
                    ((ViewGroup)customLayout.getParent()).removeView(customLayout); // <- fix
                }
                addTestDialog.show();

            }

        });
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(TestActivity.this, TestActivity.class);
                startActivity(it);
                finish();
            }
        });

        resetB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               loadTest();
            }
        });

        dialogAddB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dialogTestTime.getText().toString().isEmpty())
                {
                    dialogTestTime.setError("Enter Time is Number");
                    return;
                }
                addNewTest();
                Intent intent = new Intent(TestActivity.this, TestActivity.class);
                startActivity(intent);
                finish();

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testView.setLayoutManager(layoutManager);
        loadTest();

    }

    private void loadTest()
    {
        DbQuery.loadTestData(new MyCompleteListener() {
            @Override
            public void onSuccess() {

                adapter = new TestAdapter(DbQuery.g_testList);
                testView.setAdapter(adapter);
                progressDailog.dismiss();

            }
            @Override
            public void onFailure() {
                progressDailog.dismiss();
                Toast.makeText(TestActivity.this, "Something went wrong! Please Try Again Later !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addNewTest()
    {
        String timeTest = dialogTestTime.getText().toString();
        DbQuery.addNewTest(timeTest, new MyCompleteListener() {
            @Override
            public void onSuccess() {
                updateNoOfTest();
                Toast.makeText(TestActivity.this, "Add Test Successfully", Toast.LENGTH_SHORT).show();
                progressDailog.dismiss();
            }

            @Override
            public void onFailure() {
                Toast.makeText(TestActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                progressDailog.dismiss();

            }
        });

    }
    private void updateNoOfTest()
    {
        String curr_cat_id = DbQuery.g_catList.get(DbQuery.g_select_cat_index).getDocID();
        firestore.collection("QUIZ").document(curr_cat_id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        int setNoOfTestList =documentSnapshot.getLong("NO_OF_TESTS").intValue();
                        DbQuery.g_catList.get(DbQuery.g_select_cat_index).setNoOfTest(setNoOfTestList);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TestActivity.this, "Update Test Error", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            TestActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}