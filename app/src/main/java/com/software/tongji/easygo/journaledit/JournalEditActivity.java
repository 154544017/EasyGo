package com.software.tongji.easygo.journaledit;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;

import com.software.tongji.easygo.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JournalEditActivity extends AppCompatActivity implements JournalEditView{

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

    @BindView(R.id.save_new_journal_button)
    FloatingActionButton mSaveJournalButton;
    @BindView(R.id.add_new_photo_button)
    FloatingActionButton mAddPhotoButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_edit);

        ButterKnife.bind(this);

        mNewJournalDate.setInputType(InputType.TYPE_NULL);
        mNewJournalDate.setInputType(InputType.TYPE_NULL);
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
                finish();
            }
        });
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
}
