package com.software.tongji.easygo.tour;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.software.tongji.easygo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaveTourActivity extends AppCompatActivity {

    public static final String NEW_TOUR_TITLE = "new_tour_title";
    public static final String NEW_TOUR_REMARK = "new_tour_remark";

    @BindView(R.id.tour_title)
    TextInputEditText mTourTitle;
    @BindView(R.id.tour_remark)
    TextInputEditText mTourRemark;
    @BindView(R.id.save_tour_button)
    FloatingActionButton mSaveTourButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_tour);
        ButterKnife.bind(this);

        mSaveTourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if(mTourTitle.getText().toString().equals("")){
                    Toast.makeText(SaveTourActivity.this, "Please fill in the title at least", Toast.LENGTH_SHORT).show();
                }else{
                    intent.putExtra(NEW_TOUR_TITLE, mTourTitle.getText().toString());
                    intent.putExtra(NEW_TOUR_REMARK, mTourRemark.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

        });
    }
}
