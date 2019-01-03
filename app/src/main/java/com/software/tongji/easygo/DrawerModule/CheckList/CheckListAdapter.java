package com.software.tongji.easygo.DrawerModule.CheckList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.software.tongji.easygo.R;
import com.software.tongji.easygo.Bean.CheckItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.CheckItemHolder> {
    private List<CheckItem> mCheckItemList;
    private ChangeItemStateListener mChangeItemStateListener;

    public interface ChangeItemStateListener{
        void changeItemState(String itemName, Boolean state);
    }
    public CheckListAdapter(Context context,ChangeItemStateListener listener){
        mChangeItemStateListener = listener;
        Context context1 = context;
    }


    public void updateCheckList(List<CheckItem> items){
        mCheckItemList = items;
    }

    @NonNull
    @Override
    public CheckItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_checkbox, parent, false);
        return new CheckItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckItemHolder holder, int position) {
        holder.mItemName.setText(mCheckItemList.get(position).getName());
        holder.mCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
            CheckItem item = mCheckItemList.get(position);
            item.setCheck(b);
            mChangeItemStateListener.changeItemState(item.getName(),b);
        });
        holder.mCheckBox.setChecked(mCheckItemList.get(position).isCheck());
    }

    @Override
    public int getItemCount() {
        return mCheckItemList == null? 0 : mCheckItemList.size();
    }


    public class CheckItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.travel_check_box)
        CheckBox mCheckBox;
        @BindView(R.id.item_name)
        TextView mItemName;

        public CheckItemHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
