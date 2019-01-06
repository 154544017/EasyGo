package com.software.tongji.easygo.DrawerModule.CheckList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.Bean.CheckItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckListActivity extends AppCompatActivity implements CheckListView {
    @BindView(R.id.edit_text_check)
    EditText mEditText;
    @BindView(R.id.check_list_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.check_list_add_button)
    FloatingActionButton mAddButton;
    @BindView(R.id.check_list_delete_button)
    FloatingActionButton mDeleteButton;

    private CheckListAdapter mCheckListAdapter;
    private MaterialDialog mDialog;
    private CheckListPresenterImpl mListPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
        ButterKnife.bind(this);

        mListPresenter = new CheckListPresenterImpl(this,this);
        mCheckListAdapter = new CheckListAdapter(this, (itemName, state) -> mListPresenter.changeItemState(itemName, state));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mCheckListAdapter);

        mAddButton.setOnClickListener(view -> {
            String itemName = mEditText.getText().toString();
            mListPresenter.addCheckItem(itemName);
        });

        mDeleteButton.setOnClickListener(view -> {
            String itemName = mEditText.getText().toString();
            mListPresenter.deleteCheckItem(itemName);
        });

        mListPresenter.getCheckLists();
    }

    //加载提示
    @Override
    public void showLoadingDialog() {
        if (mDialog == null || mDialog.isCancelled()) {
            mDialog = new MaterialDialog.Builder(this)
                    .title(R.string.app_name)
                    .content("Please Wait...")
                    .progress(true, 0)
                    .show();
        }
    }

    @Override
    public void dismissLoadingDialog() {
        mDialog.dismiss();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    //刷新checklist
    @Override
    public void refreshView(List<CheckItem> checkItemList) {
        mCheckListAdapter.updateCheckList(checkItemList);
        mCheckListAdapter.notifyDataSetChanged();
    }
}
