package com.example.doancs2nhom7;

import static com.example.doancs2nhom7.query.DbQuery.ANSWERED;
import static com.example.doancs2nhom7.query.DbQuery.NOT_VISITED;
import static com.example.doancs2nhom7.query.DbQuery.REVIEW;
import static com.example.doancs2nhom7.query.DbQuery.UNANSWERED;
import static com.example.doancs2nhom7.query.DbQuery.g_catList;
import static com.example.doancs2nhom7.query.DbQuery.g_quesList;
import static com.example.doancs2nhom7.query.DbQuery.g_select_cat_index;
import static com.example.doancs2nhom7.query.DbQuery.g_select_test_index;
import static com.example.doancs2nhom7.query.DbQuery.g_testList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.doancs2nhom7.adapter.QuestionAdapter;
import com.example.doancs2nhom7.adapter.QuestionGridAdapter;
import com.example.doancs2nhom7.query.DbQuery;

import java.util.concurrent.TimeUnit;

public class QuestionsActivity extends AppCompatActivity {

    private RecyclerView questionsView;
    private TextView tvQuesID, timerTV, catNameTV;
    private Button submitB, markB, clearSelB;
    private ImageButton prevQuesB, nextQuesB;
    private ImageView quesListB;
    private int questID;
    QuestionAdapter quesAdapter;
    private DrawerLayout drawer;
    private ImageButton drawerCloseB;
    private GridView quesListGV;
    private ImageView markImage;
    private QuestionGridAdapter gridAdapter;
    private CountDownTimer timer;
    private long timeLeft;
    private ImageView bookmarkB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_list_layout);

        init();
        quesAdapter = new QuestionAdapter(g_quesList);
        questionsView.setAdapter(quesAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        questionsView.setLayoutManager(layoutManager);

        gridAdapter = new QuestionGridAdapter(this,g_quesList.size());
        quesListGV.setAdapter(gridAdapter);

        // phan cach category question 1 2 3
        setSnapHelper();
        // even button next and prev
        setClickListeners();
        // start time for question
        starTimer();

    }

    private void init()
    {
        questionsView = findViewById(R.id.questions_view);
        tvQuesID = findViewById(R.id.tv_quesID);
        timerTV =   findViewById(R.id.tv_timer);
        catNameTV =   findViewById(R.id.qa_catName);
        submitB =   findViewById(R.id.submitB);
        markB =   findViewById(R.id.markB);
        clearSelB =   findViewById(R.id.clear_selB);
        prevQuesB =   findViewById(R.id.prev_quesB);
        nextQuesB =   findViewById(R.id.next_quesB);
        quesListB =   findViewById(R.id.ques_list_gridB);
        drawer = findViewById(R.id.drawer_layout);
        markImage = findViewById(R.id.mark_image);
        quesListGV = findViewById(R.id.ques_list_gv);
        drawerCloseB = findViewById(R.id.drawerCloseB);
        bookmarkB = findViewById(R.id.qa_bookmarkB);

        questID = 0;
        tvQuesID.setText("1/" + String.valueOf(g_quesList.size()));
        catNameTV.setText(g_catList.get(g_select_cat_index).getName());
        if(g_quesList.size()>0){
            g_quesList.get(0).setStatus(UNANSWERED);
            if(g_quesList.get(0).isBookmarked())
            {
                bookmarkB.setImageResource(R.drawable.ic_bookmark_selected);
            }
            else
            {
                bookmarkB.setImageResource(R.drawable.ic_bookmark);

            }
        }


    }

    private void setSnapHelper()
    {
        final SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(questionsView);

        questionsView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
                questID = recyclerView.getLayoutManager().getPosition(view);

                if (g_quesList.get(questID).getStatus() == NOT_VISITED){
                    g_quesList.get(questID).setStatus(UNANSWERED);
                }
                if(g_quesList.get(questID).getStatus()==REVIEW)
                {
                    markImage.setVisibility(View.VISIBLE);
                }
                else
                {
                    markImage.setVisibility(View.GONE);
                }

                tvQuesID.setText(String.valueOf(questID + 1 +"/" + String.valueOf(g_quesList.size())));

                if(g_quesList.get(questID).isBookmarked())
                {
                    bookmarkB.setImageResource(R.drawable.ic_bookmark_selected);
                }
                else
                {
                    bookmarkB.setImageResource(R.drawable.ic_bookmark);

                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void setClickListeners()
    {
        prevQuesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(questID > 0)
                {
                    questionsView.smoothScrollToPosition(questID - 1);
                }

            }
        });

        nextQuesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(questID < g_quesList.size() -1)
                {
                    questionsView.smoothScrollToPosition(questID + 1);
                }

            }
        });

        clearSelB.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                g_quesList.get(questID).setSelectAns(-1);
                g_quesList.get(questID).setStatus(UNANSWERED);
                markImage.setVisibility(View.GONE);
                quesAdapter.notifyDataSetChanged();
            }
        });

        quesListB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( ! drawer.isDrawerOpen(GravityCompat.END)){
                    gridAdapter.notifyDataSetChanged();
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

        drawerCloseB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( drawer.isDrawerOpen(GravityCompat.END))
                {
                    drawer.closeDrawer(GravityCompat.END);
                }
            }
        });

        markB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (markImage.getVisibility() != view.VISIBLE)
                {
                    markImage.setVisibility(view.VISIBLE);
                    g_quesList.get(questID).setStatus(REVIEW);
                }
                else
                {
                    markImage.setVisibility(View.GONE);
                    if (g_quesList.get(questID).getSelectAns() != -1)
                    {
                        g_quesList.get(questID).setStatus(ANSWERED);
                    }
                    else
                    {
                        g_quesList.get(questID).setStatus(UNANSWERED);
                    }
                }
            }
        });

        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitTest();
            }
        });

        bookmarkB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToBookmark();
            }
        });
    }

    // void alertDialog
    private void submitTest()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuestionsActivity.this);
        builder.setCancelable(true);

        View view = getLayoutInflater().inflate(R.layout.alert_dailog_layout, null);
        Button cancelB = view.findViewById(R.id.cancelB);
        Button confirmB = view.findViewById(R.id.confirmB);

        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        // cancel
        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        // yes
        confirmB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                alertDialog.dismiss();
                Intent i = new Intent(QuestionsActivity.this, ScoreActivity.class);
                long totalTime = (long) g_testList.get(g_select_test_index).getTime() *60*1000;
                i.putExtra("TIME_TAKEN", totalTime - timeLeft);
                startActivity(i);
                QuestionsActivity.this.finish();
            }
        });
        alertDialog.show();
    }

    public void goTOQuestion(int pos)
    {
        questionsView.smoothScrollToPosition(pos);
        if (drawer.isDrawerOpen(GravityCompat.END))
        {
            drawer.closeDrawer(GravityCompat.END);
        }
    }

    private void starTimer()
    {
        long totalTime = (long) g_testList.get(g_select_test_index).getTime() *60*1000;
        timer = new CountDownTimer(totalTime + 1000, 1000) {
            @Override
            public void onTick(long remainingTime) {
                timeLeft = remainingTime;

                String time = String.format("%02d:%02d min",
                        TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                        TimeUnit.MILLISECONDS.toSeconds(remainingTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))
                );
                timerTV.setText(time);
            }

            @Override
            public void onFinish() {
                Intent i = new Intent(QuestionsActivity.this, ScoreActivity.class);
                long totalTime = (long) g_testList.get(g_select_test_index).getTime() *60*1000;
                i.putExtra("TIME_TAKEN", totalTime - timeLeft);
                startActivity(i);
                QuestionsActivity.this.finish();
            }
        };
        timer.start();
    }

    private void addToBookmark()
    {
        if (g_quesList.get(questID).isBookmarked())
        {
            g_quesList.get(questID).setBookmarked(false);
            bookmarkB.setImageResource(R.drawable.ic_bookmark);
        }
        else
        {
            g_quesList.get(questID).setBookmarked(true);
            bookmarkB.setImageResource(R.drawable.ic_bookmark_selected);
        }
    }
}