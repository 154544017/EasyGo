package com.software.tongji.easygo.JournalModule.JournalDisplay;

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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.Bean.Journal;
import com.software.tongji.easygo.JournalModule.JournalShow.JournalShowActivity;

import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {
    private Context mContext;
    private List<Journal> mJournalList;

    public void updateItemData(List<Journal> newJournals) {
        mJournalList = newJournals;
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
        holder.bind(journal);
    }

    @Override
    public int getItemCount() {
        return mJournalList == null? 0 : mJournalList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        Journal mJournal;
        Context context;
        ImageView cover;
        TextView title;
        TextView date;
        TextView city;

        public ViewHolder(View itemView){
            super(itemView);
            context = itemView.getContext();
            cover = itemView.findViewById(R.id.journal_cover_image);
            title = itemView.findViewById(R.id.journal_title);
            date = itemView.findViewById(R.id.publish_time);
            city = itemView.findViewById(R.id.journal_local);
            itemView.setOnClickListener(view -> {
                Intent intent = JournalShowActivity.newIntent(context,mJournal.getId());
                itemView.getContext().startActivity(intent);
            });
        }

        public void bind(Journal journal){
            mJournal = journal;
            title.setText(journal.getTitle());
            date.setText(journal.getDate());
            city.setText(journal.getLocation());
            if(journal.getCoverUrl() != null){
                //因为更新游记后Glide因为缓存不更新图片，所以取消掉本地缓存
                RequestOptions options = new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE);
                Glide.with(context)
                        .load(journal.getCoverUrl())
                        .apply(options)
                        .into(cover);
            }
        }
    }
}
