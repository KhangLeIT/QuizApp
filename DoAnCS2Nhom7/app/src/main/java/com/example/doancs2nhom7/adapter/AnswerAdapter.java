package com.example.doancs2nhom7.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancs2nhom7.R;
import com.example.doancs2nhom7.model.QuestionModel;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private List<QuestionModel> quesList;

    public AnswerAdapter(List<QuestionModel> quesList) {
        this.quesList = quesList;
    }

    @NonNull
    @Override
    public AnswerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answers_item_layout,parent,false);

        return new AnswerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.ViewHolder holder, int position) {
        String ques = quesList.get(position).getQuestion();
        String a = quesList.get(position).getOptionA();
        String b = quesList.get(position).getOptionB();
        String c = quesList.get(position).getOptionC();
        String d = quesList.get(position).getOptionD();
        int selected = quesList.get(position).getSelectAns();
        int ans = quesList.get(position).getCorrectAns();

        holder.setData(position,ques,a,b,c,d,selected,ans);


    }

    @Override
    public int getItemCount() {
        return quesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView quesNo, question, optionA, optionB, optionC, optionD, result;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            quesNo = itemView.findViewById(R.id.quesNo);
            question = itemView.findViewById(R.id.question);
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);
            result = itemView.findViewById(R.id.result);

        }

        private void setData(int pos, String ques, String a, String b, String c, String d, int selected, int correctAns)
        {

            quesNo.setText("Question No. " + String.valueOf(pos + 1));
            question.setText(ques);
            optionA.setText("A. " + a);
            optionB.setText("B. " + b);
            optionC.setText("C. " + c);
            optionD.setText("D. " + d);

            if (selected==-1)
            {
                result.setText("UN-ANSWERED");
                result.setTextColor(itemView.getContext().getResources().getColor(R.color.black));

                setOptionColor(selected, R.color.text_normal);
            }
            else{
                if (selected ==correctAns)
                {
                    result.setText("CORRECT");
                    result.setTextColor(itemView.getContext().getResources().getColor(R.color.green));
                    setOptionColor(selected, R.color.green);
                }
                else{
                    result.setText("WRONG");
                    result.setTextColor(itemView.getContext().getResources().getColor(R.color.red));
                    setOptionColor(selected, R.color.red);
                }

            }

        }
        private void setOptionColor(int selected, int color)
        {
            if (selected==1)
            {
                optionA.setTextColor(itemView.getContext().getResources().getColor(color));
            }
            else
            {
                optionA.setTextColor(itemView.getContext().getResources().getColor(R.color.text_normal));
            }
            if (selected==2)
            {
                optionB.setTextColor(itemView.getContext().getResources().getColor(color));
            }
            else
            {
                optionB.setTextColor(itemView.getContext().getResources().getColor(R.color.text_normal));
            }
            if (selected==3)
            {
                optionC.setTextColor(itemView.getContext().getResources().getColor(color));
            }
            else
            {
                optionC.setTextColor(itemView.getContext().getResources().getColor(R.color.text_normal));
            }

            if (selected==4)
            {
                optionD.setTextColor(itemView.getContext().getResources().getColor(color));
            }
            else
            {
                optionD.setTextColor(itemView.getContext().getResources().getColor(R.color.text_normal));
            }
        }
    }

}
