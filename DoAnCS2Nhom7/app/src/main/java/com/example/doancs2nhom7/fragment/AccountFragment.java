package com.example.doancs2nhom7.fragment;

import static com.example.doancs2nhom7.query.DbQuery.g_usersCount;
import static com.example.doancs2nhom7.query.DbQuery.g_usersList;
import static com.example.doancs2nhom7.query.DbQuery.myPerformance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.doancs2nhom7.BookmarksActivity;
import com.example.doancs2nhom7.LoginActivity;
import com.example.doancs2nhom7.MainActivity;
import com.example.doancs2nhom7.MyProfileActivity;
import com.example.doancs2nhom7.Mylistener.MyCompleteListener;
import com.example.doancs2nhom7.R;
import com.example.doancs2nhom7.TestActivity;
import com.example.doancs2nhom7.databinding.ActivityMainBinding;
import com.example.doancs2nhom7.query.DbQuery;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;
/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private LinearLayout logoutB, leaderB, profileB, bookmarkB;
    private TextView profile_img_text, name, score, rank;
    private BottomNavigationView bottomNavigationView;
    private ProgressDialog progressDailog;
    private TextView dialogText;


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        initViews(view);

        //Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("My Account");


        // progressDailog show in LoginActivity
        progressDailog = new ProgressDialog(getContext());
        progressDailog.setContentView(R.layout.dailog_layout);
        progressDailog.setCancelable(false);
        progressDailog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogText = progressDailog.findViewById(R.id.dialog_text);
        progressDailog.setMessage("Loading...");

        //img account
        String userName = DbQuery.myProfile.getName();
        profile_img_text.setText(userName.toUpperCase().substring(0,1));
        //set data
        name.setText(userName);
        score.setText(String.valueOf(DbQuery.myPerformance.getScore()));

        if(DbQuery.g_usersList.size() == 0)
        {
            progressDailog.show();
            DbQuery.getTopUsers(new MyCompleteListener() {
                @Override
                public void onSuccess() {
                    if (myPerformance.getScore() != 0)
                    {
                        if (! DbQuery.isMeOnTopList)
                        {
                            calculateRank();
                        }
                        score.setText("Score: " + myPerformance.getScore());
                        rank.setText("Rank - " + myPerformance.getRank());
                    }
                    progressDailog.dismiss();
                }

                @Override
                public void onFailure() {
                    Toast.makeText(getContext(), "Something went wrong! Please Try Again Later !", Toast.LENGTH_SHORT).show();
                    progressDailog.dismiss();
                }
            });
        }
        else
        {
            score.setText("Score: " + myPerformance.getScore());
            if (myPerformance.getScore() != 0)
                rank.setText("Rank - " + myPerformance.getRank());
        }


        logoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("585293785321-j6o2r81oa6764jnbrnvh45jt024dcmbb.apps.googleusercontent.com")
                        .requestEmail()
                        .build();

                GoogleSignInClient mGoogleClient = GoogleSignIn.getClient(getContext(), gso);

                mGoogleClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
            }
        });

        bookmarkB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), BookmarksActivity.class);
                startActivity(i);
            }
        });

        profileB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), MyProfileActivity.class);
                startActivity(i);

            }
        });

        leaderB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigationView.setSelectedItemId(R.id.navigation_leaderboard) ;

            }
        });

        return view;
    }
    private void initViews(View view)
    {
        logoutB = view.findViewById(R.id.logoutB);
        profile_img_text = view.findViewById(R.id.profile_img_text);
        name = view.findViewById(R.id.name);
        score = view.findViewById(R.id.total_score);
        rank = view.findViewById(R.id.rank);
        leaderB = view.findViewById(R.id.leaderB);
        bookmarkB = view.findViewById(R.id.bookmarkB);
        profileB = view.findViewById(R.id.profileB);
        bottomNavigationView = getActivity().findViewById(R.id.bottom_nav_bar);

    }

    private void calculateRank()
    {
        int lowTopScore = g_usersList.get(g_usersList.size() - 1).getScore();
        int remaining_slots = g_usersCount - 20;
        int mySlot = (myPerformance.getScore()*remaining_slots)/lowTopScore;
        int rank;

        if (lowTopScore != myPerformance.getScore())
        {
            rank = g_usersCount - mySlot;
        }
        else
        {
            rank = 21;
        }
        myPerformance.setRank(rank);
    }
}