package com.software.tongji.easygo.schedule;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.Schedule;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleHolder>
        implements ItemTouchHelperAdapter{

    private List<Schedule> mScheduleList;

    public ScheduleAdapter(List<Schedule> scheduleList){
        mScheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_schedule, parent, false);
        return new ScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleHolder holder, int position) {
        switch (mScheduleList.get(position).getScheduleType()){
            case HOTEL:
                holder.mScheduleIcon.setImageResource(R.mipmap.ic_hotel_schedule_round);
                break;
            case DINING:
                holder.mScheduleIcon.setImageResource(R.mipmap.ic_dining_schedule_round);
                break;
            case TRANSPORT:
                holder.mScheduleIcon.setImageResource(R.mipmap.ic_transport_schedule_round);
                break;
            case ATTRACTIONS:
                holder.mScheduleIcon.setImageResource(R.mipmap.ic_attractions_schedule_round);
                break;
        }
        holder.mAddress.setText(mScheduleList.get(position).getAddress());
        holder.mBeginTime.setText(mScheduleList.get(position).getBeginTime());
    }

    @Override
    public int getItemCount() {
        return mScheduleList.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mScheduleList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mScheduleList.remove(position);
        notifyItemRemoved(position);
    }

    public class ScheduleHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.schedule_icon)
        ImageView mScheduleIcon;
        @BindView(R.id.schedule_address)
        TextView mAddress;
        @BindView(R.id.begin_time)
        TextView mBeginTime;

        public ScheduleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            //itemView.setOnClickListener(this);
        }
    }
}
