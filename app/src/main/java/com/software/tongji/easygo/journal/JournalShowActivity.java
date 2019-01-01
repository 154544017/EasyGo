package com.software.tongji.easygo.journal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.Journal;
import com.software.tongji.easygo.bean.JournalLab;
import com.software.tongji.easygo.journaledit.JournalEditActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JournalShowActivity extends AppCompatActivity {

    private static String EXTRA_ID = "com.software.tongji.easygo.extra_id";
    @BindView(R.id.journal_title)
    TextInputEditText mJournalTitle;
    @BindView(R.id.journal_date)
    TextInputEditText mJournalDate;
    @BindView(R.id.journal_location)
    TextInputEditText mJournalLocation;
    @BindView(R.id.journal_friends)
    TextInputEditText mJournalFriends;
    @BindView(R.id.journal_content)
    TextInputEditText mJournalContent;

    @BindView(R.id.journal_image)
    ImageView mImageView;

    @BindView(R.id.edit_journal_button)
    FloatingActionButton mEditJournalButton;
    @BindView(R.id.delete_journal_button)
    FloatingActionButton mDeleteJournalButton;

    private Journal mJournal;

    public static Intent newIntent(Context packageContext, String uuid){
        Intent intent = new Intent(packageContext,JournalShowActivity.class);
        intent.putExtra(EXTRA_ID, uuid);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_show);

        ButterKnife.bind(this);

        String uuid = getIntent().getStringExtra(EXTRA_ID);
        mJournal = JournalLab.get(getApplicationContext()).getJournal(uuid);

        mJournalTitle.setText(mJournal.getTitle());
        mJournalContent.setText(mJournal.getContent());
        mJournalDate.setText(mJournal.getDate());
        mJournalFriends.setText(mJournal.getFriends());
        mJournalLocation.setText(mJournal.getLocation());
        if(mJournal.getCoverUrl() != null){
            Glide.with(this).load(mJournal.getCoverUrl()).into(mImageView);
        }

        mEditJournalButton.setOnClickListener(view -> {
            Intent intent = JournalEditActivity.newIntent(getApplicationContext(),mJournal.getId());
            startActivity(intent);
            finish();
        });

        mDeleteJournalButton.setOnClickListener(view -> {
            JournalLab.get(getApplicationContext()).deleteJournal(mJournal);
            finish();
        });

    }
}
