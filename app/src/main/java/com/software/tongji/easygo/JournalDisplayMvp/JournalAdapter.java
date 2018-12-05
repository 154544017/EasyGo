package com.software.tongji.easygo.JournalDisplayMvp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.software.tongji.easygo.MyProvinceDisplayMvp.ProvinceAdapter;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.Journal;
import com.software.tongji.easygo.journal.JournalShowActivity;

import java.util.List;
import java.util.PriorityQueue;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {
    private Context mContext;
    private List<Journal> mJournalList;

    public JournalAdapter(Context context,List<Journal> journalList) {
        mJournalList = journalList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.journal_display_item, parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Journal journal = mJournalList.get(position);
        holder.title.setText(journal.getTitle());
        holder.city.setText(journal.getLocation());
        holder.date.setText(journal.getDate());
    }

    @Override
    public int getItemCount() {
        return mJournalList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cover;
        TextView title;
        TextView date;
        TextView city;

        public ViewHolder(View itemView){
            super(itemView);
            cover = itemView.findViewById(R.id.journal_cover);
            title = itemView.findViewById(R.id.journal_title);
            date = itemView.findViewById(R.id.publish_time);
            city = itemView.findViewById(R.id.journal_local);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), JournalShowActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
