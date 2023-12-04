package com.example.doancs2nhom7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doancs2nhom7.Mylistener.MyCompleteListener;
import com.example.doancs2nhom7.adapter.TestAdapter;
import com.example.doancs2nhom7.fragment.CategoryFragment;
import com.example.doancs2nhom7.model.TestModel;
import com.example.doancs2nhom7.query.DbQuery;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private RecyclerView testView;
    private Toolbar toolbar;
    private TestAdapter adapter;
    private ProgressDialog progressDailog;
    private TextView dialogText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(DbQuery.g_catList.get(DbQuery.g_select_cat_index).getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        testView = findViewById(R.id.test_recycler_view);

        // progressDailog show in LoginActivity
        progressDailog = new ProgressDialog(TestActivity.this);
        progressDailog.setContentView(R.layout.dailog_layout);
        progressDailog.setCancelable(false);
        progressDailog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogText = progressDailog.findViewById(R.id.dialog_text);
        progressDailog.setMessage("Loading...");

        progressDailog.show();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        testView.setLayoutManager(layoutManager);

        DbQuery.loadTestData(new MyCompleteListener() {
            @Override
            public void onSuccess() {

                DbQuery.loadMyScore(new MyCompleteListener() {
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

            @Override
            public void onFailure() {
                progressDailog.dismiss();
                Toast.makeText(TestActivity.this, "Something went wrong! Please Try Again Later !", Toast.LENGTH_SHORT).show();
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