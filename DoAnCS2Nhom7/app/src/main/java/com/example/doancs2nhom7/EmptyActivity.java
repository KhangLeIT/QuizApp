package com.example.doancs2nhom7;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doancs2nhom7.model.MyScoresModel;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.Value;

import java.io.Console;
import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmptyActivity extends AppCompatActivity {
    private TextView infoEmpty;
    public static String info;

    Map<String, Object> cartList = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        infoEmpty = findViewById(R.id.information_empty);
        FirebaseFirestore firestoreRef = FirebaseFirestore.getInstance();

        DocumentReference docRef = firestoreRef.collection("USERS")
                .document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                .document("MY_SCORES");




        Map<String, Object> cartList = new ArrayMap<>();

        cartList = (Map<String, Object>) cartList.get(firestoreRef.collection("USERS")
                .document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                .document("MY_SCORES"));

       Log.d("\n\n\n\n AAAAAAAAAAAAAA", String.valueOf(cartList));
    }


}