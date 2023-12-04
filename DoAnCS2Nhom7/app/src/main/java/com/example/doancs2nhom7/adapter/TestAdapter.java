package com.example.doancs2nhom7.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancs2nhom7.QuestionsActivity;
import com.example.doancs2nhom7.R;
import com.example.doancs2nhom7.StarTestActivity;
import com.example.doancs2nhom7.model.TestModel;
import com.example.doancs2nhom7.query.DbQuery;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private List<TestModel> testList;

    public TestAdapter(List<TestModel> testList) {
        this.testList = testList;
    }

    @NonNull
    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestAdapter.ViewHolder holder, int position) {
        int progress = testList.get(position).getTopScore();
        holder.setData(position, progress);
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView testNo;
        private TextView topScore;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            testNo = itemView.findViewById(R.id.textNo);
            topScore = itemView.findViewById(R.id.scoreText);
            progressBar = itemView.findViewById(R.id.testProgressbar);



        }

        private void setData(final int pos, int progress){
            testNo.setText("Test No " + String.valueOf(pos +1));
            topScore.setText(String.valueOf(progress)+ "%");

            progressBar.setProgress(progress);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DbQuery.g_select_test_index = pos;

                    Intent intent = new Intent(itemView.getContext(), StarTestActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }
}
