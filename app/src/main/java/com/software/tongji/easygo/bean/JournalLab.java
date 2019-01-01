package com.software.tongji.easygo.bean;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.software.tongji.easygo.net.ApiService;
import com.software.tongji.easygo.net.BaseResponse;
import com.software.tongji.easygo.net.DefaultObserver;
import com.software.tongji.easygo.net.RetrofitServiceManager;
import com.software.tongji.easygo.utils.HttpUtils;
import com.software.tongji.easygo.utils.MyLitepalGson;

import org.litepal.LitePal;

import java.io.File;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class JournalLab {

    public static JournalLab sJournalLab;

    private Context mContext;

    public static JournalLab get(Context context){
        if(sJournalLab == null){
            sJournalLab = new JournalLab(context);
        }
        return sJournalLab;
    }

    private JournalLab(Context context){
        mContext = context.getApplicationContext();
    }

    public int size(){
        return LitePal.count(Journal.class);
    }

    public void addJournal(Journal journal){
        journal.save();
        insertJournalSync(journal);
    }

    public void updateJournal(Journal journal){
        journal.updateAll("mId = ?", journal.getId());
        insertJournalSync(journal);
    }

    public void deleteJournal(Journal journal){
        LitePal.deleteAll(Journal.class, "mId = ?", journal.getId());
        deleteJournalSync(journal.getId());

    }

    public Journal getJournal(String uuid){
        List<Journal> journals = LitePal.where("mId = ?", uuid)
                .find(Journal.class);
        return journals.get(0);
    }

    public List<Journal> getJournalList() {
        return LitePal.findAll(Journal.class);
    }

    public List<Journal> getJournalListByProvince(String province){
        List<Journal> journals = LitePal.where("mProvince = ?", province)
                .find(Journal.class);
        return journals;
    }

    public File getCoverFIle(Journal journal){
        File fileDir = mContext.getFilesDir();
        return new File(fileDir, journal.getCoverFileName());
    }

    void deleteJournalSync(String id){
        FormBody formBody = new FormBody.Builder().add("journalId", id).build();
        RetrofitServiceManager.getInstance()
                .getRetrofit()
                .create(ApiService.class)
                .deleteJournal(UserInfo.userName, formBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResponse<Void>>() {
                    @Override
                    public void onSuccess(Object result) {
                        Toast.makeText(mContext,"删除成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(String message) {
                        Toast.makeText(mContext,"删除失败，未上传到服务器,原因: " + message,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(mContext,"删除失败，未上传到服务器,原因: " + message,Toast.LENGTH_LONG).show();
                    }
                });

    }

    void insertJournalSync(Journal journal){
        MyLitepalGson gson = new MyLitepalGson();
        RequestBody journalJson = HttpUtils.getFormDataRequest(gson.toJson(journal));
        Log.e("Gson",gson.toJson(journal));
        File file = getCoverFIle(journal);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("editormd-image-file", journal.getCoverFileName(), RequestBody.create(MediaType.parse("image/*"), file));
        RetrofitServiceManager.getInstance()
                .getRetrofit()
                .create(ApiService.class)
                .postJournals(UserInfo.userName, journalJson, filePart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResponse<Void>>() {
                    @Override
                    public void onSuccess(Object result) {
                        Toast.makeText(mContext,"保存成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(String message) {
                        Toast.makeText(mContext,"保存失败，未上传到服务器,原因: " + message,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(mContext,"保存失败，未上传到服务器,原因: " + message,Toast.LENGTH_LONG).show();
                    }
                });
    }
}
