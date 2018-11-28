package com.software.tongji.easygo.attractions;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.software.tongji.easygo.R;
import com.software.tongji.easygo.details.DetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttractionsAdapter extends RecyclerView.Adapter<AttractionsAdapter.AttractionsHolder>{

    private List<String> mNameList;

    public AttractionsAdapter(List<String> nameList){
        mNameList = nameList;
    }

    @NonNull
    @Override
    public AttractionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_attractions, parent, false);
        return new AttractionsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttractionsHolder holder, int position) {
        holder.mAttractionsCardView.findViewById(R.id.attractions_name).setAlpha(0.8f);
    }

    @Override
    public int getItemCount() {
        return mNameList.size();
    }

    public class AttractionsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.attractions_card_view)
        CardView mAttractionsCardView;

        public AttractionsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), DetailsActivity.class);
            view.getContext().startActivity(intent);
        }
    }
}


