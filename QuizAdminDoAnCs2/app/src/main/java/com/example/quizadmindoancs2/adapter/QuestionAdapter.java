package com.example.quizadmindoancs2.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizadmindoancs2.AddQuestionActivity;
import com.example.quizadmindoancs2.QuestionActivity;
import com.example.quizadmindoancs2.R;
import com.example.quizadmindoancs2.UpdateQuestionActivity;
import com.example.quizadmindoancs2.model.QuestionModel;
import com.example.quizadmindoancs2.query.DbQuery;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private List<QuestionModel> questionList;

    public QuestionAdapter(List<QuestionModel> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView ques, quesNo;
        private TextView optionA, optionB, optionC, optionD, answer;
        private ImageView quesDeleteB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ques = itemView.findViewById(R.id.question);
            quesNo = itemView.findViewById(R.id.quesNo);
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);
            answer = itemView.findViewById(R.id.answer);
            quesDeleteB= itemView.findViewById(R.id.quesDeleteB);
        }
        private void setData(final int pos)
        {
            quesNo.setText("Question No " +String.valueOf(pos +1));
            ques.setText(questionList.get(pos).getQuestion());
            optionA.setText(" A " + questionList.get(pos).getOptionA());
            optionB.setText(" B " + questionList.get(pos).getOptionB());
            optionC.setText(" C " + questionList.get(pos).getOptionC());
            optionD.setText(" D " + questionList.get(pos).getOptionD());

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    DbQuery.g_select_ques_index = pos;
                    Intent it = new Intent(itemView.getContext(), UpdateQuestionActivity.class);
                    itemView.getContext().startActivity(it);
                    ((Activity)itemView.getContext()).finish();
                    return false;
                }
            });

            String answerCorrect;
            if(questionList.get(pos).getCorrectAns() == 1){
                answerCorrect = "A";
            }
            else if(questionList.get(pos).getCorrectAns() == 2){
                 answerCorrect = "B";
            }
            else if(questionList.get(pos).getCorrectAns() == 3){
                answerCorrect = "C";
            }
            else if (questionList.get(pos).getCorrectAns() == 4)
            {
                answerCorrect = "D";
            }
            else answerCorrect = "No Answer";

            answer.setText("Answer is " + answerCorrect );

            quesDeleteB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog dialog = new AlertDialog.Builder(itemView.getContext())
                            .setTitle("Delete Question")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    deleteQuestion(pos);
                                    Intent it = new Intent(itemView.getContext(), QuestionActivity.class);
                                    itemView.getContext().startActivity(it);
                                    ((Activity) itemView.getContext()).finish();

                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    dialog.getButton(dialog.BUTTON_POSITIVE).setBackgroundColor(Color.BLUE);
                    dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                    dialog.getButton(dialog.BUTTON_NEGATIVE).setBackgroundColor(Color.GREEN);
                    dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 0, 250, 0);
                    dialog.getButton(dialog.BUTTON_NEGATIVE).setLayoutParams(params);
                }
            });

        }


        private void deleteQuestion(int pos)
        {
            FirebaseFirestore g_firestore = FirebaseFirestore.getInstance();
            String IdQuesDelete = DbQuery.g_quesList.get(pos).getqID();
            g_firestore.collection("QUESTION").document(IdQuesDelete)
                    .delete();
            DbQuery.g_quesList.remove(pos);
        }
    }
}
