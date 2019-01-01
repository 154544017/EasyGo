package com.software.tongji.easygo.newschedule;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amap.api.services.help.Tip;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.inputTips.InputTipsActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewScheduleActivity extends AppCompatActivity implements NewScheduleView {

    private NewSchedulePresenter mNewSchedulePresenter;

    public static final String NEW_SCHEDULE_TYPE = "new_schedule_type";
    public static final String NEW_SCHEDULE_ADDRESS = "new_schedule_address";
    public static final String NEW_SCHEDULE_DATE = "new_schedule_date";
    public static final String NEW_SCHEDULE_TIME = "new_schedule_time";
    public static final String NEW_SCHEDULE_COST = "new_schedule_cost";
    public static final String NEW_SCHEDULE_REMARK = "new_schedule_remark";
    public static final String NEW_SCHEDULE_LAT = "new_schedule_lat";
    public static final String NEW_SCHEDULE_LON = "new_schedule_lon";

    private static final int REQUEST_PLACE = 1;

    @BindView(R.id.new_schedule_address)
    TextInputEditText mScheduleAddress;
    @BindView(R.id.new_schedule_date)
    TextInputEditText mScheduleDate;
    @BindView(R.id.new_schedule_time)
    TextInputEditText mScheduleTime;
    @BindView(R.id.spinner_schedule_type)
    Spinner mScheduleType;
    @BindView(R.id.new_schedule_cost)
    TextInputEditText mScheduleCost;
    @BindView(R.id.new_schedule_remark)
    TextInputEditText mScheduleRemark;
    @BindView(R.id.new_schedule_ok)
    FloatingActionButton mNewScheduleOk;

    private double mLatitude;
    private double mLongitude;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);

        ButterKnife.bind(this);

        mScheduleDate.setInputType(InputType.TYPE_NULL);
        mScheduleDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    showDatePickDialog();
                }
            }
        });
        mScheduleDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickDialog();
            }
        });

        mScheduleTime.setInputType(InputType.TYPE_NULL);
        mScheduleTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    showTimePickDialog();
                }
            }
        });

        mScheduleTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickDialog();
            }
        });


        mScheduleAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewScheduleActivity.this, InputTipsActivity.class);
                startActivityForResult(intent, REQUEST_PLACE);
            }
        });

        mNewScheduleOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if(mScheduleAddress.getText().toString().length() < 1){
                    Toast.makeText(NewScheduleActivity.this, "Please fill the address before your commit!", Toast.LENGTH_SHORT).show();
                }else{
                    intent.putExtra(NEW_SCHEDULE_ADDRESS,mScheduleAddress.getText().toString());
                    intent.putExtra(NEW_SCHEDULE_DATE, mScheduleDate.getText().toString());
                    intent.putExtra(NEW_SCHEDULE_TIME, mScheduleTime.getText().toString());
                    intent.putExtra(NEW_SCHEDULE_TYPE, mScheduleType.getSelectedItem().toString());
                    intent.putExtra(NEW_SCHEDULE_COST, mScheduleCost.getText().toString());
                    intent.putExtra(NEW_SCHEDULE_REMARK, mScheduleRemark.getText().toString());
                    intent.putExtra(NEW_SCHEDULE_LAT, mLatitude);
                    intent.putExtra(NEW_SCHEDULE_LON, mLongitude);
                    setResult(RESULT_OK, intent);
                    finish();
                }
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
                        mScheduleDate.setText(year + "/" + (month + 1) + "/" + day);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void showTimePickDialog() {
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        mScheduleTime.setText(hour + ":" + minute);
                    }
                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PLACE) {
            if (resultCode == InputTipsActivity.RESULT_CODE_INPUTTIPS) {
                final Tip tip = data.getParcelableExtra("tip");
                if (tip.getName() != null) {
                    mScheduleAddress.setText(tip.getName());
                    mLatitude = tip.getPoint().getLatitude();
                    mLongitude = tip.getPoint().getLongitude();
                }
            }
        }
    }
}
