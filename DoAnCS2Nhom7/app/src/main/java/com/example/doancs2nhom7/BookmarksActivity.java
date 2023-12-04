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
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.doancs2nhom7.Mylistener.MyCompleteListener;
import com.example.doancs2nhom7.adapter.AnswerAdapter;
import com.example.doancs2nhom7.adapter.BookmarkAdapter;
import com.example.doancs2nhom7.query.DbQuery;

public class BookmarksActivity extends AppCompatActivity {
    private RecyclerView questionsView;
    private ProgressDialog progressDailog;
    private TextView dialogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        Toolbar toolbar = findViewById(R.id.ba_toolbar);

        questionsView = findViewById(R.id.ba_recycler_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Saved Questions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // progressDailog show in LoginActivity
        progressDailog = new ProgressDialog(BookmarksActivity.this);
        progressDailog.setContentView(R.layout.dailog_layout);
        progressDailog.setCancelable(false);
        progressDailog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogText = progressDailog.findViewById(R.id.dialog_text);
        progressDailog.setMessage("Loading...");

        progressDailog.show();


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        questionsView.setLayoutManager(layoutManager);

        DbQuery.loadBookmarks(new MyCompleteListener() {
            @Override
            public void onSuccess() {

                BookmarkAdapter adapter = new BookmarkAdapter(DbQuery.g_bookmarksList);
                questionsView.setAdapter(adapter);

                progressDailog.dismiss();
            }

            @Override
            public void onFailure() {
                progressDailog.dismiss();

            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            BookmarksActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}