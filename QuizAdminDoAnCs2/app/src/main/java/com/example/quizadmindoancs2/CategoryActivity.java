package com.example.quizadmindoancs2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizadmindoancs2.Mylistener.MyCompleteListener;
import com.example.quizadmindoancs2.adapter.CategoryAdapter;
import com.example.quizadmindoancs2.model.CategoryModel;
import com.example.quizadmindoancs2.query.DbQuery;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryActivity extends AppCompatActivity {
    private GridView cat_view;
    private Button addCatB, resetB;
    private FirebaseFirestore firestore;
    private AlertDialog.Builder addCatDialog;
    private ProgressDialog progressDailog;
    private EditText dialogCatName;
    private Button dialogAddB, dialogCancel;
    private int noOfTest = 0;
    private CategoryAdapter adapter;

    private boolean addCartFT = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");

        cat_view = findViewById(R.id.cat_recycler);
        addCatB = findViewById(R.id.addCatB);
        resetB = findViewById(R.id.resetB);


        // progressDailog show in LoginActivity
        progressDailog = new ProgressDialog(CategoryActivity.this);
        progressDailog.setContentView(R.layout.dailog_layout);
        progressDailog.setCancelable(false);
        progressDailog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressDailog.setMessage("Signing In...");


        addCatDialog = new AlertDialog.Builder(CategoryActivity.this);
        View customLayout = getLayoutInflater().inflate(R.layout.add_category_dialog, null);
        addCatDialog.setView(customLayout);
        addCatDialog.setTitle("Add new Category");

        dialogAddB = customLayout.findViewById(R.id.ac_add_btn);
        dialogCatName = customLayout.findViewById(R.id.ac_cat_name);
        dialogCancel = customLayout.findViewById(R.id.ac_add_cancel);

        firestore = FirebaseFirestore.getInstance();

        adapter = new CategoryAdapter(DbQuery.g_catList);
        cat_view.setAdapter(adapter);

        addCatB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addCartFT == true){
                    Intent intent = new Intent(CategoryActivity.this, CategoryActivity.class);
                    startActivity(intent);
                }
                else {
                    dialogCatName.getText().clear();
                    addCatDialog.show();
                    addCartFT = true;
                }

            }
        });
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

        resetB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = new CategoryAdapter(DbQuery.g_catList);
                cat_view.setAdapter(adapter);

                Toast.makeText(CategoryActivity.this, "Reset Successfully ", Toast.LENGTH_SHORT).show();
            }
        });

        dialogAddB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dialogCatName.getText().toString().isEmpty())
                {
                    dialogCatName.setError("Enter Category Name");
                    return;
                }
                addNewCategory(dialogCatName.getText().toString());
            }
        });
    }

    private void addNewCategory(String title)
    {
        progressDailog.show();
        addCartFT = false;
        DbQuery.addCategory(title, new MyCompleteListener() {
            @Override
            public void onSuccess() {
                progressDailog.dismiss();

                adapter = new CategoryAdapter(DbQuery.g_catList);
                cat_view.setAdapter(adapter);

                progressDailog.dismiss();
                Toast.makeText(CategoryActivity.this, "Success", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(CategoryActivity.this, CategoryActivity.class);
                startActivity(i);
                finish();

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure() {
                progressDailog.dismiss();
                Toast.makeText(CategoryActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });



    }


}