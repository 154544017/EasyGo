package com.software.tongji.easygo.checklist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.CheckItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.CheckItemHolder> {
    private Context mContext;
    private List<CheckItem> mCheckItemList;

    public CheckListAdapter(Context context, List<CheckItem> checkItemList){
        mContext = context;
        mCheckItemList = checkItemList;
    }

    public void addItem(String itemName){
        if(itemName.equals("")){
            Toast.makeText(mContext, "Item Name can't be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        int exitFlag = 0;
        for(int i = 0; i < mCheckItemList.size(); i++){
            if(mCheckItemList.get(i).getName().equals(itemName)){
                exitFlag = 1;
                break;
            }
        }
        if(exitFlag == 1){
            Toast.makeText(mContext, "Item exited in the list!", Toast.LENGTH_SHORT).show();
        }else{
            mCheckItemList.add(new CheckItem(itemName, false));
            notifyDataSetChanged();
        }
    }

    public void deleteItem(String itemName){
        if(itemName.equals("")){
            Toast.makeText(mContext, "Item Name can't be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        int deleteFlag = 0;
        for(int i = 0; i < mCheckItemList.size(); i++){
            if(mCheckItemList.get(i).getName().equals(itemName)){
                mCheckItemList.remove(i);
                notifyDataSetChanged();
                deleteFlag = 1;
                break;
            }
        }
        if(deleteFlag == 0){
            Toast.makeText(mContext, "No such item in the list!", Toast.LENGTH_SHORT).show();
        }
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
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCheckItemList.get(position).setCheck(b);
            }
        });
        holder.mCheckBox.setChecked(mCheckItemList.get(position).isCheck());
    }

    @Override
    public int getItemCount() {
        return mCheckItemList.size();
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
