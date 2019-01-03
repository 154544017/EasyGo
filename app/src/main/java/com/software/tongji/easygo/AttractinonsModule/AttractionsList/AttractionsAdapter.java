package com.software.tongji.easygo.AttractinonsModule.AttractionsList;

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
import com.bumptech.glide.request.RequestOptions;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.Bean.Attraction;
import com.software.tongji.easygo.AttractinonsModule.AttractionsDetails.DetailsActivity;
import com.software.tongji.easygo.Utils.MapHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttractionsAdapter extends RecyclerView.Adapter<AttractionsAdapter.AttractionsHolder>{

    private List<Attraction> mAttractionList;

    public void updateItemData(List<Attraction> newAttractions) {
        mAttractionList = newAttractions;
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
       Attraction attraction = mAttractionList.get(position);
       holder.bind(attraction);
    }

    @Override
    public int getItemCount() {
        return mAttractionList == null? 0 : mAttractionList.size();
    }

    public class AttractionsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.attractions_province)
        TextView provinceName;
        @BindView(R.id.attractions_name)
        TextView attractionName;
        @BindView(R.id.attractions_city)
        TextView cityName;
        @BindView(R.id.attractions_picture)
        ImageView attractionImage;

        private Context context;
        private Attraction mAttraction;

        AttractionsHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(Attraction attraction){
            mAttraction = attraction;
            provinceName.setText(MapHelper.provinceHanzi.get(mAttraction.getProvince()));
            attractionName.setText(mAttraction.getName());
            cityName.setText(attraction.getCity());
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.attractions_place_holder)
                    .centerCrop();
            Glide.with(context).load(mAttraction.getImages().get(1)).apply(options).into(attractionImage);
        }

        @Override
        public void onClick(View view) {
            Intent intent = DetailsActivity.newIntent(view.getContext(), mAttraction);
            view.getContext().startActivity(intent);
        }
    }
}


