package com.software.tongji.easygo.InputTipModule.InputTips;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.Toast;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.software.tongji.easygo.R;

import java.util.List;

public class InputTipsActivity extends AppCompatActivity{

    private ListView mInputListView;
    private List<Tip> mCurrentTipList;
    private InputTipsAdapter mInputTipsAdapter;

    public static String DEFAULT_CITY = "北京";
    public static final int RESULT_CODE_INPUT_TIPS = 101;
    public static final int REQUEST_SUC = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_tips);

        //初始化searchView
        SearchView searchView = findViewById(R.id.keyWord);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    InputtipsQuery inputQuery = new InputtipsQuery(newText, DEFAULT_CITY);
                    Inputtips inputTips = new Inputtips(InputTipsActivity.this, inputQuery);
                    inputTips.setInputtipsListener((list, i) -> {
                        if (i == REQUEST_SUC) {
                            mCurrentTipList = list;
                            mInputTipsAdapter = new InputTipsAdapter(getApplicationContext(), mCurrentTipList);
                            mInputListView.setAdapter(mInputTipsAdapter);
                            mInputTipsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(InputTipsActivity.this, "错误码 :" + i, Toast.LENGTH_SHORT).show();
                        }
                    });
                    inputTips.requestInputtipsAsyn();
                } else {
                    // 如果输入为空  则清除 listView 数据
                    if (mInputTipsAdapter != null && mCurrentTipList != null) {
                        mCurrentTipList.clear();
                        mInputTipsAdapter.notifyDataSetChanged();
                    }
                }
                return true;
            }
        });
        //设置SearchView默认为展开显示
        searchView.setIconified(false);
        searchView.onActionViewExpanded();
        searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(false);

        //初始化listView
        mInputListView = findViewById(R.id.input_tip_list);
        mInputListView.setOnItemClickListener((parent, view, position, id) -> {
            if (mCurrentTipList != null) {
                Tip tip = (Tip) parent.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.putExtra("tip", tip);
                setResult(RESULT_CODE_INPUT_TIPS, intent);
                InputTipsActivity.this.finish();
            }
        });
    }

}
