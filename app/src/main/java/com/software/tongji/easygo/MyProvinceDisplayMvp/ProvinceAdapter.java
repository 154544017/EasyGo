package com.software.tongji.easygo.MyProvinceDisplayMvp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.Province;

import java.util.List;

import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ViewHolder> {
    private Context mContext;
    private List<Province> mProvinceList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView provinceImage;
        TextView provinceName;

        public ViewHolder(View itemView){
            super(itemView);
            provinceImage = itemView.findViewById(R.id.province_display_image);
            provinceName = itemView.findViewById(R.id.province_display_name);
        }

    }

    public ProvinceAdapter(List<Province> provinces) {
        mProvinceList = provinces;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.province_display_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Province province = mProvinceList.get(position);
        holder.provinceName.setText(province.getName());
        if(province.isLocked()) {
            Glide.with(mContext).load(R.drawable.guilin)
                    .apply(bitmapTransform(new GrayscaleTransformation()))
                    .into(holder.provinceImage);
        }else{
            Glide.with(mContext).load(R.drawable.guilin_name).into(holder.provinceImage);
        }
    }

    @Override
    public int getItemCount() {
        return mProvinceList.size();
    }
}
