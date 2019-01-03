package com.software.tongji.easygo.DrawerModule.Tour;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.software.tongji.easygo.R;
import com.software.tongji.easygo.Bean.Tour;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TourListAdapter extends RecyclerView.Adapter<TourListAdapter.TourHolder> {

    private List<Tour> mTourList;
    private OnTourClickListener mOnTourClickListener;

    public TourListAdapter(List<Tour> tourList,OnTourClickListener listener){
        mOnTourClickListener = listener;
        mTourList = tourList;
    }

    public interface OnTourClickListener{
        void changeToSchedule(String tourId);
    }

    @NonNull
    @Override
    public TourHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_tour, parent, false);
        return new TourListAdapter.TourHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourHolder holder, int position) {
        holder.bind(mTourList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTourList.size();
    }

    public class TourHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.tour_title)
        TextView mTourTitle;
        @BindView(R.id.tour_remark)
        TextView mTourRemark;

        private Tour mTour;
        public TourHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(Tour tour){
            mTour = tour;
            mTourTitle.setText(tour.getTitle());
            mTourRemark.setText(tour.getRemark());
        }
        @Override
        public void onClick(View view) {
            mOnTourClickListener.changeToSchedule(mTour.getId());
        }
    }
}
