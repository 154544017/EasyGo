package com.software.tongji.easygo.journal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;

import com.software.tongji.easygo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JournalShowActivity extends AppCompatActivity {

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

    @BindView(R.id.edit_journal_button)
    FloatingActionButton mEditJournalButton;
    @BindView(R.id.delete_journal_button)
    FloatingActionButton mDeleteJournalButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_show);

        ButterKnife.bind(this);

        mJournalTitle.setInputType(InputType.TYPE_NULL);
        mJournalDate.setInputType(InputType.TYPE_NULL);
        mJournalFriends.setInputType(InputType.TYPE_NULL);
        mJournalLocation.setInputType(InputType.TYPE_NULL);
        mJournalContent.setInputType(InputType.TYPE_NULL);

        mEditJournalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mDeleteJournalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
