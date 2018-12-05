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

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleHolder>
        implements ItemTouchHelperAdapter{

    private List<Schedule> mScheduleList;
    private ScheduleListPresenter mScheduleListPresenter;

    public ScheduleAdapter(List<Schedule> scheduleList, ScheduleListPresenter scheduleListPresenter){
        mScheduleList = scheduleList;
        mScheduleListPresenter = scheduleListPresenter;
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
        holder.mDate.setText(mScheduleList.get(position).getDate());
        holder.mTime.setText(mScheduleList.get(position).getTime());
        switch (mScheduleList.get(position).getType()){
            case "ATTRACTIONS":
                holder.mIcon.setImageResource(R.mipmap.ic_attractions_schedule);
                break;
            case "DINING":
                holder.mIcon.setImageResource(R.mipmap.ic_dining_schedule);
                break;
            case "TRANSPORT":
                holder.mIcon.setImageResource(R.mipmap.ic_transport_schedule);
                break;
            case "HOTEL":
                holder.mIcon.setImageResource(R.mipmap.ic_hotel_schedule);
                break;
            default:
                break;
        }
        holder.mAddress.setText(mScheduleList.get(position).getAddress());
        holder.mRemark.setText(mScheduleList.get(position).getRemark());
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
        mScheduleListPresenter.checkScheduleList();
        notifyItemRemoved(position);
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

        public ScheduleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            //itemView.setOnClickListener(this);
        }
    }
}
