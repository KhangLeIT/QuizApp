package com.example.doancs2nhom7.fragment;

import static com.example.doancs2nhom7.query.DbQuery.g_usersCount;
import static com.example.doancs2nhom7.query.DbQuery.g_usersList;
import static com.example.doancs2nhom7.query.DbQuery.myPerformance;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doancs2nhom7.MainActivity;
import com.example.doancs2nhom7.Mylistener.MyCompleteListener;
import com.example.doancs2nhom7.R;
import com.example.doancs2nhom7.TestActivity;
import com.example.doancs2nhom7.adapter.RankAdapter;
import com.example.doancs2nhom7.query.DbQuery;

/**
 * A simple {@link Fragment} subclass.

 */
public class LeaderBoardFragment extends Fragment {

    private TextView totalUsersTV, myImgTextTV, myScoreTV, myRankTV;
    private RecyclerView usersView;
    private RankAdapter adapter;
    private ProgressDialog progressDailog;
    private TextView dialogText;

    public LeaderBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_leader_board, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("LeaderBoard");

        unitViews(view);

        // progressDailog show in LoginActivity
        progressDailog = new ProgressDialog(getContext());
        progressDailog.setContentView(R.layout.dailog_layout);
        progressDailog.setCancelable(false);
        progressDailog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogText = progressDailog.findViewById(R.id.dialog_text);
        progressDailog.setMessage("Loading...");


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        usersView.setLayoutManager(layoutManager);

        adapter = new RankAdapter(DbQuery.g_usersList);
        usersView.setAdapter(adapter);

        DbQuery.getTopUsers(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                adapter.notifyDataSetChanged();
                if (myPerformance.getScore() != 0)
                {
                    if (! DbQuery.isMeOnTopList)
                    {
                        calculateRank();
                    }
                    myScoreTV.setText("Score: " + myPerformance.getScore());
                    myRankTV.setText("Rank - " + myPerformance.getRank());

                }
                progressDailog.dismiss();
            }

            @Override
            public void onFailure() {
                Toast.makeText(getContext(), "Something went wrong! Please Try Again Later !", Toast.LENGTH_SHORT).show();
                progressDailog.dismiss();
            }
        });

        totalUsersTV.setText("Total Users: " + g_usersCount);

        myImgTextTV.setText(myPerformance.getName().toUpperCase().substring(0,1));

        return view;
    }

    private void unitViews(View view)
    {
        totalUsersTV = view.findViewById(R.id.total_users);
        myImgTextTV = view.findViewById(R.id.img_text);
        myScoreTV = view.findViewById(R.id.total_score);
        myRankTV = view.findViewById(R.id.rank);
        usersView = view.findViewById(R.id.users_view);

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