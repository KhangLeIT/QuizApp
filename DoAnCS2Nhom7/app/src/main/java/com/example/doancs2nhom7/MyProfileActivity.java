package com.example.doancs2nhom7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doancs2nhom7.Mylistener.MyCompleteListener;
import com.example.doancs2nhom7.query.DbQuery;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyProfileActivity extends AppCompatActivity {

    private EditText name, email, phone;
    private LinearLayout editB;
    private Button cancelB, saveB;
    private TextView profileText;
    private LinearLayout button_layout;
    private String nameStr, phoneStr;
    private ProgressDialog progressDailog;
    private Pattern pattern;
    private Matcher matcher;
    private static final String reg = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.mp_name);
        email = findViewById(R.id.mp_email);
        phone = findViewById(R.id.mp_phone);
        profileText = findViewById(R.id.profile_text);
        editB = findViewById(R.id.editB);
        cancelB = findViewById(R.id.cancelB);
        saveB = findViewById(R.id.saveB);
        button_layout = findViewById(R.id.button_layout);

        // progressDailog show in LoginActivity
        progressDailog = new ProgressDialog(MyProfileActivity.this);
        progressDailog.setContentView(R.layout.dailog_layout);
        progressDailog.setCancelable(false);
        progressDailog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressDailog.setMessage("Updating Data...");

        disableEditing();

        editB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableEditing();
            }
        });

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableEditing();
            }
        });
        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                    saveData();
                }
            }
        });

    }
    private void disableEditing()
    {
        name.setEnabled(false);
        email.setEnabled(false);
        phone.setEnabled(false);

        name.setTextColor(Color.GRAY);
        phone.setTextColor(Color.GRAY);

        button_layout.setVisibility(View.GONE);

        name.setText(DbQuery.myProfile.getName());
        email.setText(DbQuery.myProfile.getEmail());

        if (DbQuery.myProfile.getPhone() != null)
        {
            phone.setText(DbQuery.myProfile.getPhone());
        }
        String profileName = DbQuery.myProfile.getName();
        profileText.setText(profileName.toUpperCase().substring(0,1));

    }

    private void enableEditing()
    {
        name.setEnabled(true);
        //email.setEnabled(true);
        name.setTextColor(Color.BLACK);
        phone.setTextColor(Color.BLACK);
        phone.setEnabled(true);
        button_layout.setVisibility(View.VISIBLE);

    }

    private boolean validate(){
        nameStr = name.getText().toString();
        phoneStr = phone.getText().toString();
        if (nameStr.isEmpty())
        {
            name.setError("Name can not be empty !");
            return false;
        }
        if(phoneValidator(phoneStr)==false)
        {
            phone.setError("Incorrect telephone number");
            return false;
        }
        if (nameStr.length() <4)
        {
            name.setError("Your Name must be 6 or more characters");
            return false;
        }
        if (!phoneStr.isEmpty())
        {
            if (!((phoneStr.length()==10 && (TextUtils.isDigitsOnly(phoneStr)))))
            {
                phone.setError("Enter Valid Phone Number");
                return false;
            }
        }
        return true;
    }

    private void saveData()
    {
        progressDailog.dismiss();
        if (phoneStr.isEmpty())
            phoneStr=null;

        DbQuery.saveProfileData(nameStr, phoneStr, new MyCompleteListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MyProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                disableEditing();
                progressDailog.dismiss();
            }

            @Override
            public void onFailure() {
                Toast.makeText(MyProfileActivity.this, "Something went wrong! Please try again later.", Toast.LENGTH_SHORT).show();
                progressDailog.dismiss();
            }
        });

    }
    private boolean phoneValidator(String hex) {
        pattern = Pattern.compile(reg);
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            MyProfileActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}