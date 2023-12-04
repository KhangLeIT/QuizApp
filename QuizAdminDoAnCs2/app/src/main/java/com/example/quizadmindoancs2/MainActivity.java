package com.example.quizadmindoancs2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizadmindoancs2.Mylistener.MyCompleteListener;
import com.example.quizadmindoancs2.query.DbQuery;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private EditText email, pass;
    private Button login;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDailog;
    private TextView dialogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.loginB);

        firebaseAuth= FirebaseAuth.getInstance();
        DbQuery.g_firestore = FirebaseFirestore.getInstance();

        progressDailog = new ProgressDialog(MainActivity.this);
        progressDailog.setContentView(R.layout.dailog_layout);
        progressDailog.setCancelable(false);
        progressDailog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogText = progressDailog.findViewById(R.id.dialog_text);
        progressDailog.setMessage("Signing In...");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().isEmpty())
                {
                    email.setError("Enter Email ID");
                    return;
                }
                else {
                    email.setError(null);
                }
                if(pass.getText().toString().isEmpty())
                {
                    pass.setError("Enter Password");
                    return;
                }
                else {
                    pass.setError(null);
                }
                firebaseLogin();

            }
        });
        if (firebaseAuth.getCurrentUser() != null){
            DbQuery.loadData(new MyCompleteListener() {
                @Override
                public void onSuccess() {
                    Intent i = new Intent(MainActivity.this, CategoryActivity.class );
                    startActivity(i);
                   // finish();
                    MainActivity.this.finish();
                }

                @Override
                public void onFailure() {
                    Toast.makeText( MainActivity.this, "Something went wrong! Please Try Again Later !", Toast.LENGTH_SHORT).show();

                }
            });

        }

    }
    private void firebaseLogin(){
        progressDailog.show();
        firebaseAuth.signInWithEmailAndPassword(email.getText().toString().trim(), pass.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, "Successes", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainActivity.this, BeginActivity.class );
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        }
                        progressDailog.dismiss();
                    }
                });
    }
}