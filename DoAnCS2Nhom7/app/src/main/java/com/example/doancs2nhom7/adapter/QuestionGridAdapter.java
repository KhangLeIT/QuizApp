package com.example.doancs2nhom7.adapter;

import static com.example.doancs2nhom7.query.DbQuery.ANSWERED;
import static com.example.doancs2nhom7.query.DbQuery.NOT_VISITED;
import static com.example.doancs2nhom7.query.DbQuery.REVIEW;
import static com.example.doancs2nhom7.query.DbQuery.UNANSWERED;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.doancs2nhom7.QuestionsActivity;
import com.example.doancs2nhom7.R;
import com.example.doancs2nhom7.query.DbQuery;

public class QuestionGridAdapter extends BaseAdapter {

    private int numOfQues;
    private Context context;

    public QuestionGridAdapter(Context context, int numOfQues) {
        this.numOfQues = numOfQues;
        this.context = context;
    }

    @Override
    public int getCount() {
        return numOfQues;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View myView;
        if (view == null)
        {
            myView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ques_grid_item,viewGroup,false);
        }
        else
        {
            myView = view;
        }

        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof QuestionsActivity)
                {
                    ((QuestionsActivity)context).goTOQuestion(i);
                }

            }
        });

        TextView quesTV = myView.findViewById(R.id.ques_num);
        quesTV.setText(String.valueOf(i+1));

        Log.d("LOG..." ,String.valueOf(DbQuery.g_quesList.get(i).getStatus()));
        switch(DbQuery.g_quesList.get(i).getStatus())
        {
            case NOT_VISITED:
                quesTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myView.getContext(), R.color.grey)));
                break;
            case UNANSWERED:
                quesTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myView.getContext(), R.color.red)));
                break;
            case ANSWERED:
                quesTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myView.getContext(), R.color.green)));
                break;
            case REVIEW:
                quesTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myView.getContext(), R.color.pink)));
                break;
            default: break;
        }

        return myView;
    }
}
