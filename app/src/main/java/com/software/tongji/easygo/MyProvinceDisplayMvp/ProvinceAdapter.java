package com.software.tongji.easygo.MyProvinceDisplayMvp;

import android.content.Context;
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
import com.software.tongji.easygo.bean.Province;
import com.software.tongji.easygo.utils.HttpUtils;

import java.util.List;

import jp.wasabeef.glide.transformations.GrayscaleTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ViewHolder> {
    private Context mContext;
    private List<Province> mProvinceList;
    private ProvinceClickListener mProvinceClickListener;

    interface ProvinceClickListener{
        void onClick(int mode, String provinceName);
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView provinceImage;
        TextView provinceName;
        Province mProvince;
        Context mContext;

        public ViewHolder(View itemView){
            super(itemView);
            mContext = itemView.getContext();
            provinceImage = itemView.findViewById(R.id.province_display_image);
            provinceName = itemView.findViewById(R.id.province_display_name);
            itemView.setOnClickListener(v -> {
                if(mProvince.isLocked()){
                    mProvinceClickListener.onClick(0, mProvince.getName());
                }else{
                    mProvinceClickListener.onClick(1, mProvince.getName());
                }
            });
        }

        public void bind(Province province){
            mProvince = province;
            provinceName.setText(province.getName());
            if(province.isLocked()) {
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.province_place_holder)
                        .centerCrop();
                Glide.with(mContext).load(HttpUtils.getProvinceDisplayImageUrl(province.getPinYin()))
                        .apply(options)
                        .apply(bitmapTransform(new GrayscaleTransformation()))
                        .into(provinceImage);
            }else{
                Glide.with(mContext).load(HttpUtils.getProvinceDisplayImageUrl(province.getPinYin())).into(provinceImage);
            }
        }
    }

    public ProvinceAdapter(List<Province> provinces, ProvinceClickListener provinceClickListener) {
        mProvinceClickListener = provinceClickListener;
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
        holder.bind(province);
    }

    @Override
    public int getItemCount() {
        return mProvinceList.size();
    }
}
