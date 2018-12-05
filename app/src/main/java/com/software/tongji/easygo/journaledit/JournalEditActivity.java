package com.software.tongji.easygo.journaledit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.software.tongji.easygo.R;
import com.software.tongji.easygo.newschedule.NewScheduleActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JournalEditActivity extends AppCompatActivity implements JournalEditView{

    public static final String NEW_JOURNAL_TITLE = "new_journal_title";
    public static final String NEW_JOURNAL_DATE = "new_journal_date";
    public static final String NEW_JOURNAL_LOCATION = "new_journal_location";
    public static final String NEW_JOURNAL_FRIENDS = "new_journal_friends";
    public static final String NEW_JOURNAL_CONTENT = "new_journal_content";

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
                Intent intent = new Intent();
                if(mNewJournalTitle.getText().toString().length() < 1){
                    Toast.makeText(JournalEditActivity.this, "Please fill the title before your commit!", Toast.LENGTH_SHORT).show();
                }else{
                    intent.putExtra(NEW_JOURNAL_TITLE, mNewJournalTitle.getText().toString());
                    intent.putExtra(NEW_JOURNAL_DATE, mNewJournalDate.getText().toString());
                    intent.putExtra(NEW_JOURNAL_LOCATION, mNewJournalLocation.getText().toString());
                    intent.putExtra(NEW_JOURNAL_FRIENDS, mNewJournalFriends.getText().toString());
                    intent.putExtra(NEW_JOURNAL_CONTENT, mNewJournalContent.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        mAddPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
