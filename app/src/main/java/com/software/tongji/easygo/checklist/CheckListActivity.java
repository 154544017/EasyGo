package com.software.tongji.easygo.checklist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.CheckItemLab;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckListActivity extends AppCompatActivity {
    @BindView(R.id.edit_text_check)
    EditText mEditText;
    @BindView(R.id.check_list_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.check_list_add_button)
    FloatingActionButton mAddButton;
    @BindView(R.id.check_list_delete_button)
    FloatingActionButton mDeleteButton;

    private CheckListAdapter mCheckListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
        ButterKnife.bind(this);

        mCheckListAdapter = new CheckListAdapter(this, CheckItemLab.get(this).getCheckItemList());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mCheckListAdapter);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = mEditText.getText().toString();
                mCheckListAdapter.addItem(itemName);
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = mEditText.getText().toString();
                mCheckListAdapter.deleteItem(itemName);
            }
        });
    }
}
