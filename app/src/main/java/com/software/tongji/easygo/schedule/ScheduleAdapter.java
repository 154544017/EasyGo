package com.software.tongji.easygo.schedule;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.software.tongji.easygo.R;
import com.software.tongji.easygo.ScheduleShow.ScheduleShow;
import com.software.tongji.easygo.bean.Schedule;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleHolder>
        implements ItemTouchHelperAdapter {

    private List<Schedule> mScheduleList;
    private ScheduleListPresenter mScheduleListPresenter;

    public ScheduleAdapter(ScheduleListPresenter scheduleListPresenter){
        mScheduleListPresenter = scheduleListPresenter;
    }

    public void updateList(List<Schedule> schedules){
        mScheduleList = schedules;
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
        Schedule schedule = mScheduleList.get(position);
        holder.bind(schedule);
    }

    @Override
    public int getItemCount() {
        return mScheduleList == null? 0 : mScheduleList.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        mScheduleListPresenter.swapSchedule(mScheduleList.get(fromPosition),mScheduleList.get(toPosition));
    }

    @Override
    public void onItemDismiss(int position) {
        mScheduleListPresenter.deleteSchedule(mScheduleList.get(position));
    }

    public class ScheduleHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.schedule_date)
        TextView mDate;
        @BindView(R.id.schedule_time)
        TextView mTime;
        @BindView(R.id.schedule_icon)
        ImageView mIcon;
        @BindView(R.id.schedule_address)
        TextView mAddress;
        @BindView(R.id.schedule_remark)
        TextView mRemark;
        Schedule mSchedule;

        public ScheduleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = ScheduleShow.newIntent(itemView.getContext(), mSchedule);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        public void bind(Schedule schedule){
            mSchedule = schedule;
            mDate.setText(schedule.getDate());
            mTime.setText(schedule.getTime());
            switch (schedule.getType()){
                case "ATTRACTIONS":
                    mIcon.setImageResource(R.mipmap.ic_attractions_schedule);
                    break;
                case "DINING":
                    mIcon.setImageResource(R.mipmap.ic_dining_schedule);
                    break;
                case "TRANSPORT":
                    mIcon.setImageResource(R.mipmap.ic_transport_schedule);
                    break;
                case "HOTEL":
                    mIcon.setImageResource(R.mipmap.ic_hotel_schedule);
                    break;
                default:
                    break;
            }
            mAddress.setText(schedule.getAddress());
            mRemark.setText(schedule.getRemark());
        }
    }
}
