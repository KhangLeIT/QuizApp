package com.example.doancs2nhom7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doancs2nhom7.Mylistener.MyCompleteListener;
import com.example.doancs2nhom7.databinding.DailogLayoutBinding;
import com.example.doancs2nhom7.query.DbQuery;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private EditText name, email, pass, confirmPass;
    private Button signUpB;
    private ImageView backB;
    private String emailStr, passStr, confirmPassStr, nameStr;
    private ProgressDialog progressDailog;
    private TextView dialogText;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";// cấu trúc 1 email thông thường
    private Pattern pattern;
    private Matcher matcher;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.username);
        email = findViewById(R.id.emailID);
        pass = findViewById(R.id.password);
        confirmPass = findViewById(R.id.confirm_pass);
        signUpB = findViewById(R.id.signupB);
        backB = findViewById(R.id.backB);

        progressDailog = new ProgressDialog(SignUpActivity.this);
        progressDailog.setContentView(R.layout.dailog_layout);
        progressDailog.setCancelable(false);
        progressDailog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogText = progressDailog.findViewById(R.id.dialog_text);

        progressDailog.setMessage("Registering User... ");


        mAuth = FirebaseAuth.getInstance();

        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        signUpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    signupNewUser();
                }

            }
        });

    }
    private boolean validate(){
        nameStr = name.getText().toString().trim();
        passStr = pass.getText().toString().trim();
        emailStr = email.getText().toString().trim();
        confirmPassStr = confirmPass.getText().toString().trim();


        if(nameStr.isEmpty()){
            name.setError("Enter Your Name");
            return false;
        }
        if(nameStr.length() < 4){
            name.setError("Your Name must be 6 or more characters");
            return false;
        }
        if(emailStr.isEmpty()){
            email.setError("Enter E-Mail ID");
            return false;
        }
        if(EmailValidator(emailStr) == false)
        {
            Toast.makeText(SignUpActivity.this, "Email ko dung", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(passStr.isEmpty()){
            pass.setError("Enter Password");
            return false;
        }
        if(confirmPassStr.isEmpty()){
            confirmPass.setError("Enter Password");
            return false;
        }
        if (passStr.compareTo(confirmPassStr) != 0){
            Toast.makeText(SignUpActivity.this, "Password and confirm password should be same! ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(passStr.length() <6)
        {
            Toast.makeText(SignUpActivity.this, "Password must be 6 or more characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;


    }
    private void signupNewUser(){
        progressDailog.show();
        mAuth.createUserWithEmailAndPassword(emailStr, passStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                            // add users if user new on cloud firebase
                            DbQuery.createUserData(emailStr,nameStr, new MyCompleteListener() {
                                @Override
                                public void onSuccess() {

                                    DbQuery.loadData(new MyCompleteListener() {
                                        @Override
                                        public void onSuccess() {

                                            progressDailog.dismiss();

                                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            SignUpActivity.this.finish();
                                        }

                                        @Override
                                        public void onFailure() {
                                            Toast.makeText(SignUpActivity.this, "Something went wrong! Please Try Again Later !", Toast.LENGTH_SHORT).show();
                                            progressDailog.dismiss();

                                        }
                                    });

                                }

                                @Override
                                public void onFailure() {
                                    Toast.makeText(SignUpActivity.this, "Something went wrong! Please Try Again Later !", Toast.LENGTH_SHORT).show();
                                    progressDailog.dismiss();

                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDailog.dismiss();
                            AlertDialog dialog = new AlertDialog.Builder(SignUpActivity.this)
                                    .setTitle("Email, password is incorrect or the account exist !")
                                    .setPositiveButton("OK", null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                            dialog.getButton(dialog.BUTTON_POSITIVE).setBackgroundColor(Color.BLUE);
                            dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                           // Toast.makeText(SignUpActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private boolean EmailValidator(String hex) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }


}