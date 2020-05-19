package com.studentproject.journalapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class journalAdapter extends RecyclerView.Adapter<journalAdapter.journalViewHolder> {
    private ArrayList<journalshow> journalList;
    private Activity activity;

    public journalAdapter(ArrayList<journalshow> journalList, Activity activity) {
        this.journalList = journalList;
        this.activity = activity;
    }
    @NonNull
    @Override
    public journalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycle,parent,false);
        return new journalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull journalViewHolder holder, final int position) {
        final journalshow journal = journalList.get(position);
        holder.Date.setText(journal.getDate());
        holder.Time.setText(journal.getTime());
        holder.historyDay.setText(journal.getHistoryday());
        holder.relativeLayout.setBackgroundColor(Color.parseColor(journal.getColor()));



        /*Sự kiện click vào item*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, journalList.get(position).toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }



    @Override
    public int getItemCount() {
        return journalList.size();
    }

    public class journalViewHolder extends  RecyclerView.ViewHolder {
        private TextView Date;
        private TextView Time;
        private TextView historyDay;
        private ImageView imgIcons;
        private RelativeLayout relativeLayout;

        public journalViewHolder(View itemView) {
            super(itemView);
            Date = (TextView) itemView.findViewById(R.id.tv_date);
            Time = (TextView) itemView.findViewById(R.id.tv_time);
            historyDay = (TextView) itemView.findViewById(R.id.tv_historyDay);
            imgIcons = itemView.findViewById(R.id.img_icons);
            relativeLayout = itemView.findViewById(R.id.relative);
        }
    }

}
