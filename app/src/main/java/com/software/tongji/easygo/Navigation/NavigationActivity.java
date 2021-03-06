package com.software.tongji.easygo.Navigation;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.software.tongji.easygo.AchievementModule.Achievement.AchievementFragment;
import com.software.tongji.easygo.JournalModule.JournalDisplay.JournalDisplayFragment;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.AttractinonsModule.AttractionsList.AttractionsListFragment;
import com.software.tongji.easygo.Bean.Journal;
import com.software.tongji.easygo.Bean.JournalLab;
import com.software.tongji.easygo.Bean.UserData;
import com.software.tongji.easygo.Bean.UserInfo;
import com.software.tongji.easygo.Net.ApiService;
import com.software.tongji.easygo.Net.BaseResponse;
import com.software.tongji.easygo.Net.DefaultObserver;
import com.software.tongji.easygo.Net.RetrofitServiceManager;
import com.software.tongji.easygo.TravelPlanModule.ScheduleList.ScheduleListFragment;
import com.software.tongji.easygo.Utils.FileUtil;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NavigationActivity extends AppCompatActivity {

    @BindView(R.id.bottom_tab_strip)
    PageNavigationView mPageNavigationView;

    private NavigationController mNavigationController;
    private FragmentManager mFragmentManager;
    private List<Fragment> mFragmentList;
    private MaterialDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_navigation);
        LitePal.getDatabase();

        ButterKnife.bind(this);

        mFragmentManager = getSupportFragmentManager();

        mNavigationController = mPageNavigationView.material()
                .addItem(R.drawable.ic_format_list_bulleted_black_24dp, "行程")
                .addItem(R.drawable.ic_edit_location_black_24dp, "足迹")
                .addItem(R.drawable.ic_collections_black_24dp, "游记")
                .addItem(R.drawable.ic_search_black_24dp, "搜索")
                .build();
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new ScheduleListFragment());
        mFragmentList.add(new AchievementFragment());
        mFragmentList.add(new JournalDisplayFragment());
        mFragmentList.add(new AttractionsListFragment());

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, mFragmentList.get(0))
                .add(R.id.fragment_container, mFragmentList.get(1))
                .add(R.id.fragment_container, mFragmentList.get(2))
                .add(R.id.fragment_container, mFragmentList.get(3))
                .hide(mFragmentList.get(1))
                .hide(mFragmentList.get(2))
                .hide(mFragmentList.get(3))
                .show(mFragmentList.get(0))
                .commit();

        //界面跳转
        mNavigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.hide(mFragmentList.get(old))
                        .show(mFragmentList.get(index))
                        .commit();
            }

            @Override
            public void onRepeat(int index) {

            }
        });

        getInfo();
    }

    public void showLoadDialog(){
        mDialog = new MaterialDialog.Builder(this)
                .title(R.string.sync)
                .content("Please Wait...")
                .progress(true, 0)
                .show();
    }

    public void dismissDialog(){
        mDialog.dismiss();
    }

    public void getInfo(){
        String userName = PreferenceManager.getDefaultSharedPreferences(this).getString("user_email","");
        UserInfo.userName = userName;
        showLoadDialog();
        RetrofitServiceManager.getInstance()
                .getRetrofit()
                .create(ApiService.class)
                .getInfo(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResponse<UserData>>() {
                    @Override
                    public void onSuccess(Object result) {
                        UserData data = (UserData)result;
                        UserInfo.journalCount = data.getJournalCount();
                        if (JournalLab.get(NavigationActivity.this).size() != data.getJournalCount()) {
                            syncJournals();
                        } else {
                            dismissDialog();
                        }
                    }

                    @Override
                    public void onFail(String message) {
                        showError(message);
                        dismissDialog();
                    }

                    @Override
                    public void onError(String message) {
                        showError(message);
                        dismissDialog();
                    }
                });
    }

    //跳转到景点搜索界面
    public void changeSearchFragmentWithArgs(String args){
        AttractionsListFragment fragment = (AttractionsListFragment)mFragmentList.get(3);
        fragment.setDefaultProvince(args);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.hide(mFragmentList.get(0))
                .hide(mFragmentList.get(1))
                .hide(mFragmentList.get(2))
                .show(mFragmentList.get(3))
                .commit();
        mNavigationController.setSelect(3);
    }

    //跳转到游记界面
    public void changeJournalFragmentWithArgs(String args){
        JournalDisplayFragment fragment = (JournalDisplayFragment) mFragmentList.get(2);
        fragment.setDefaultProvince(args);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.hide(mFragmentList.get(0))
                .hide(mFragmentList.get(1))
                .hide(mFragmentList.get(3))
                .show(mFragmentList.get(2))
                .commit();
        mNavigationController.setSelect(2);
    }

    public void showError(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    //从服务器同步游记
    public void syncJournals(){
        if (UserInfo.journalCount != JournalLab.get(this).size()) {
            RetrofitServiceManager.getInstance()
                    .getRetrofit()
                    .create(ApiService.class)
                    .getJournals(UserInfo.userName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(new DefaultObserver<BaseResponse<List<Journal>>>() {
                        @Override
                        public void onSuccess(Object result) {
                            List<Journal> data = (List<Journal>) result;
                            JournalLab journalLab = JournalLab.get(NavigationActivity.this);
                            for(Journal journal:data){
                                String fileName = journalLab.getCoverFIle(journal).toString();
                                FileUtil.saveImage(NavigationActivity.this, journal.getCoverUrl(),fileName);
                                journal.setCoverUrl(fileName);
                            }
                            LitePal.deleteAll(Journal.class);
                            LitePal.saveAll(data);
                            dismissDialog();
                        }

                        @Override
                        public void onFail(String message) {
                            showError(message);
                            dismissDialog();
                        }

                        @Override
                        public void onError(String message) {
                            showError(message);
                            dismissDialog();
                        }
                    });
        }
    }

}
