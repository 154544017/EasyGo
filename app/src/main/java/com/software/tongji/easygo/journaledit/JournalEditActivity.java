package com.software.tongji.easygo.journaledit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.services.help.Tip;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.Journal;
import com.software.tongji.easygo.bean.JournalLab;
import com.software.tongji.easygo.inputTips.InputTipsActivity;
import com.software.tongji.easygo.utils.FileUtil;
import com.software.tongji.easygo.utils.ImageUtil;
import com.software.tongji.easygo.utils.MapHelper;

import java.io.File;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JournalEditActivity extends AppCompatActivity implements JournalEditView {


    private static final String EXTRA_JOURNAL_ID = "com.software.tongji.easygo.journal_id";
    private static final int REQUEST_ALBUM = 0;
    private static final int REQUEST_ALBUM_PERMISSION = 1;
    private static final int REQUEST_PLACE = 2;

    @BindView(R.id.new_journal_title)
    TextInputEditText mNewJournalTitle;
    @BindView(R.id.new_journal_date)
    TextInputEditText mNewJournalDate;
    @BindView(R.id.new_journal_location)
    TextInputEditText mNewJournalLocation;
    @BindView(R.id.new_journal_friends)
    TextInputEditText mNewJournalFriends;
    @BindView(R.id.new_journal_content)
    TextInputEditText mNewJournalContent;
    @BindView(R.id.new_journal_collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;

    @BindView(R.id.new_journal_image)
    ImageView mCoverImage;

    @BindView(R.id.save_new_journal_button)
    FloatingActionButton mSaveJournalButton;
    @BindView(R.id.add_new_photo_button)
    FloatingActionButton mAddPhotoButton;

    private File mCoverFile;
    private Journal mJournal;
    private Dialog mDialog;
    private JournalEditPresenter mPresenter;
    private String mJournalId;

    public static Intent newIntent(Context packageContext, String uuid){
        Intent intent = new Intent(packageContext, JournalEditActivity.class);
        intent.putExtra(EXTRA_JOURNAL_ID, uuid);
        return intent;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_edit);

        ButterKnife.bind(this);

        mPresenter = new JournalEditPresenter();
        mPresenter.bind(this, this);

        mJournalId = getIntent().getStringExtra(EXTRA_JOURNAL_ID);
        mNewJournalDate.setInputType(InputType.TYPE_NULL);

        if(!mJournalId.equals("add")) {
            mJournal = JournalLab.get(this).getJournal(mJournalId);
            mNewJournalTitle.setText(mJournal.getTitle());
            mNewJournalContent.setText(mJournal.getContent());
            mNewJournalDate.setText(mJournal.getDate());
            mNewJournalFriends.setText(mJournal.getFriends());
            mNewJournalLocation.setText(mJournal.getLocation());
        }else{
            mJournal = new Journal();
        }

        mCoverFile = JournalLab.get(this).getCoverFIle(mJournal);

        mNewJournalLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    Intent intent = new Intent(JournalEditActivity.this, InputTipsActivity.class);
                    startActivityForResult(intent, REQUEST_PLACE);
                }
            }
        });

        if(mCoverFile!= null && mCoverFile.exists()){
            mCollapsingToolbar.setTitle("");
            Glide.with(this).load(mCoverFile).into(mCoverImage);
        }

        mNewJournalDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    showDatePickDialog();
                }
            }
        });
        mNewJournalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickDialog();
            }
        });

        mSaveJournalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if(mNewJournalTitle.getText().toString().length() < 1){
                    Toast.makeText(JournalEditActivity.this, "请为游记添加题目", Toast.LENGTH_SHORT).show();
                }else if(!mCoverFile.exists()){
                    Toast.makeText(JournalEditActivity.this, "请为游记选一张合适的照片", Toast.LENGTH_SHORT).show();
                }else{
                    mJournal.setCoverUrl(mCoverFile.getPath());
                    mJournal.setTitle( mNewJournalTitle.getText().toString());
                    mJournal.setDate( mNewJournalDate.getText().toString());
                    mJournal.setContent(mNewJournalContent.getText().toString());
                    mJournal.setFriends(mNewJournalFriends.getText().toString());
                    mJournal.setLocation(mNewJournalLocation.getText().toString());
                    if(mJournalId.equals("add")){
                        mPresenter.addJournal(mJournal);
                    }else {
                        mPresenter.saveJournal(mJournal);
                    }
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        final Intent chooseFromAlbum = new Intent("android.intent.action.GET_CONTENT");
        chooseFromAlbum.setType("image/*");
        mAddPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(JournalEditActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    //requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_ALBUM_PERMISSION);
                }else{
                    startActivityForResult(chooseFromAlbum, REQUEST_ALBUM);
                }
            }
        });
    }

    @Override
    public void showLoadingDialog() {
        mDialog = new MaterialDialog.Builder(this)
                .title(R.string.load_journals)
                .content("Please Wait...")
                .progress(true, 0)
                .show();
    }

    @Override
    public void dismissLoadingDialog() {
        mDialog.dismiss();
    }

    @Override
    public void showDatePickDialog() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        mNewJournalDate.setText(year + "/" + (month + 1) + "/" + day);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void updateCoverImage() {
        if(mCoverFile == null || !mCoverFile.exists()){
            Glide.with(this).load(R.drawable.new_journal_holder).into(mCoverImage);
        }else{
            RequestOptions userAvatarOptions = new RequestOptions()
                    .signature(new ObjectKey(System.currentTimeMillis()))
                    .encodeQuality(70);
            Glide.with(this).load(mCoverFile).apply(userAvatarOptions).into(mCoverImage);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_ALBUM_PERMISSION:
                if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "无法读取相册",Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_ALBUM){
            if(resultCode != Activity.RESULT_OK){
                return;
            }
            String imagePath = ImageUtil.handleImageOnKitkat(this, data);
            FileUtil.copyFile(imagePath, mCoverFile.getPath());
            updateCoverImage();
        }else if(requestCode == REQUEST_PLACE){
            if(resultCode == InputTipsActivity.RESULT_CODE_INPUTTIPS){
                final Tip tip = data.getParcelableExtra("tip");
                if (tip.getName() != null) {
                    mNewJournalLocation.setText(tip.getName());
                    String province = tip.getDistrict().substring(0,2);
                    mJournal.setProvince(MapHelper.provinceBrief.get(province));
                }
            }
        }
    }
}
