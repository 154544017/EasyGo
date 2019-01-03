package com.software.tongji.easygo.TravelPlanModule.ScheduleList;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;


public class ItemTouchHelperCallback extends ItemTouchHelper.Callback{

    private ScheduleAdapter mScheduleAdapter;

    /*@Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        viewHolder.itemView.setScrollX(0);
        ((ScheduleAdapter.ScheduleHolder) viewHolder).mDelete.setText(R.string.delete_text);
        FrameLayout.LayoutParams params =
                (FrameLayout.LayoutParams) ((ScheduleAdapter.ScheduleHolder) viewHolder).itemView.getLayoutParams();
        params.width = 150;
        params.height = 150;
        ((ScheduleAdapter.ScheduleHolder) viewHolder).itemView.setLayoutParams(params);
        ((ScheduleAdapter.ScheduleHolder) viewHolder).itemView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            if(Math.abs(dX) <= getSlideLimitation(viewHolder)){
                viewHolder.itemView.scrollTo(-(int) dX, 0);
            }
        }
        else{
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public int getSlideLimitation(RecyclerView.ViewHolder viewHolder){
        ViewGroup viewGroup = (ViewGroup) viewHolder.itemView;
        return viewGroup.getChildAt(1).getLayoutParams().width;
    }*/

    public ItemTouchHelperCallback(ScheduleAdapter scheduleAdapter){
        mScheduleAdapter = scheduleAdapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mScheduleAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mScheduleAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
