package com.software.tongji.easygo.MyProvinceDisplayMvp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.Province;
import com.software.tongji.easygo.utils.HttpUtils;

import java.util.List;

import jp.wasabeef.glide.transformations.GrayscaleTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.bumptech.glide.request.RequestOptions.priorityOf;

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
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
            Glide.with(mContext).load(HttpUtils.getProvinceDisplayImageUrl(province.getPinYin()))
                    .apply(bitmapTransform(new GrayscaleTransformation()))
                    .apply(options)
                    .into(holder.provinceImage);
        }else{
            Glide.with(mContext).load(HttpUtils.getProvinceDisplayImageUrl(province.getPinYin())).into(holder.provinceImage);
        }
    }

    @Override
    public int getItemCount() {
        return mProvinceList.size();
    }
}
