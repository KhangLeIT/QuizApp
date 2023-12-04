package com.example.doancs2nhom7.adapter;

import static com.example.doancs2nhom7.query.DbQuery.myPerformance;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancs2nhom7.R;
import com.example.doancs2nhom7.model.RankModel;

import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {

    private List<RankModel> userList;

    public RankAdapter(List<RankModel> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public RankAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankAdapter.ViewHolder holder, int position) {

        String name = userList.get(position).getName();
        int score = userList.get(position).getScore();
        int rank = userList.get(position).getRank();

        holder.setData(name, score, rank);

    }

    @Override
    public int getItemCount() {
        if (userList.size() >10) {
            return 10;
        }
        else
            return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTV, rankTV, scoreTV, imgTV;
        private ConstraintLayout constraintRank;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.name);
            rankTV = itemView.findViewById(R.id.rank);
            scoreTV = itemView.findViewById(R.id.score);
            imgTV = itemView.findViewById(R.id.img_text);
            constraintRank = itemView.findViewById(R.id.constraint_rank);
        }

        private void setData(String name, int score, int rank)
        {
            nameTV.setText(name);
            scoreTV.setText("Score: " + score);
            rankTV.setText("Rank - " + rank);
            imgTV.setText(name.toUpperCase().substring(0,1));
            if(rank==1)
            {
                constraintRank.setBackgroundResource(R.drawable.background_rank_1);
            }
            if(rank==2)
            {
                constraintRank.setBackgroundResource(R.drawable.background_rank_2);
            }
            if(rank==3)
            {
                constraintRank.setBackgroundResource(R.drawable.background_rank_3);
            }
        }
    }
}
