package com.example.quizadmindoancs2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizadmindoancs2.Mylistener.MyCompleteListener;
import com.example.quizadmindoancs2.query.DbQuery;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class BeginActivity extends AppCompatActivity {
    private TextView appname;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);

        appname = findViewById(R.id.app_name);

        Typeface typeface = ResourcesCompat.getCachedFont(this, R.font.blacklist);
        appname.setTypeface(typeface);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.myanim);
        appname.setAnimation(anim);

        mAuth = FirebaseAuth.getInstance();

        DbQuery.g_firestore = FirebaseFirestore.getInstance();

        new Thread(){

            @Override
            public void run()
            {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(mAuth.getCurrentUser() != null){
                    DbQuery.loadData(new MyCompleteListener() {
                        @Override
                        public void onSuccess() {
                            Intent intent = new Intent(BeginActivity.this, CategoryActivity.class);
                            startActivity(intent);
                            BeginActivity.this.finish();
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(BeginActivity.this, "Something went wrong! Please Try Again Later !", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
                else {
                    Intent intent = new Intent(BeginActivity.this, MainActivity.class);
                    startActivity(intent);
                    BeginActivity.this.finish();
                }

            }

        }.start();



    }
}