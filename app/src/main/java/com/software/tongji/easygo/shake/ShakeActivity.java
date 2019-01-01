package com.software.tongji.easygo.shake;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.Attraction;
import com.software.tongji.easygo.details.DetailsActivity;
import com.software.tongji.easygo.net.ApiService;
import com.software.tongji.easygo.net.BaseResponse;
import com.software.tongji.easygo.net.DefaultObserver;
import com.software.tongji.easygo.net.RetrofitServiceManager;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShakeActivity extends AppCompatActivity implements SensorEventListener{

    private static final int START_SHAKE = 0x1;
    private static final int AGAIN_SHAKE = 0x2;
    private static final int END_SHAKE = 0x3;

    private SensorManager mSensorManager;
    private Sensor mAccelerometerSensor;
    private Vibrator mVibrator;
    private MyHandler mHandler;

    private boolean mShake = false;

    @BindView(R.id.main_linear_top)
    LinearLayout mTopLayout;
    @BindView(R.id.main_linear_bottom)
    LinearLayout mBottomLayout;
    @BindView(R.id.main_shake_top_line)
    ImageView mTopLine;
    @BindView(R.id.main_shake_bottom_line)
    ImageView mBottomLine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_shake);
        ButterKnife.bind(this);
        mTopLine.setVisibility(View.GONE);
        mBottomLine.setVisibility(View.GONE);

        mHandler = new MyHandler(this);
        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE));
        if (mSensorManager != null) {
            mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (mAccelerometerSensor != null) {
                mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int type = sensorEvent.sensor.getType();

        if (type == Sensor.TYPE_ACCELEROMETER) {
            //获取三个方向值
            float[] values = sensorEvent.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            if ((Math.abs(x) > 17 || Math.abs(y) > 17 || Math
                    .abs(z) > 17) && !mShake) {
                mShake = true;
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            mHandler.obtainMessage(START_SHAKE).sendToTarget();
                            Thread.sleep(500);
                            mHandler.obtainMessage(AGAIN_SHAKE).sendToTarget();
                            Thread.sleep(500);
                            mHandler.obtainMessage(END_SHAKE).sendToTarget();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private static class MyHandler extends Handler{
        private WeakReference<ShakeActivity> mReference;
        private ShakeActivity mShakeActivity;

        public MyHandler(ShakeActivity shakeActivity){
            mReference = new WeakReference<>(shakeActivity);
            if (mReference != null) {
                mShakeActivity = mReference.get();
            }
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case START_SHAKE:
                    mShakeActivity.mVibrator.vibrate(300);
                    mShakeActivity.mTopLine.setVisibility(View.VISIBLE);
                    mShakeActivity.mBottomLine.setVisibility(View.VISIBLE);
                    mShakeActivity.startAnimation(false);
                    break;
                case AGAIN_SHAKE:
                    mShakeActivity.mVibrator.vibrate(300);
                    break;
                case END_SHAKE:
                    mShakeActivity.mShake = false;
                    mShakeActivity.startAnimation(true);
                    break;
            }
        }
    }

    private void startAnimation(boolean isBack){
        int type = Animation.RELATIVE_TO_SELF;

        float topFromY;
        float topToY;
        float bottomFromY;
        float bottomToY;
        if (isBack) {
            topFromY = -0.5f;
            topToY = 0;
            bottomFromY = 0.5f;
            bottomToY = 0;
        } else {
            topFromY = 0;
            topToY = -0.5f;
            bottomFromY = 0;
            bottomToY = 0.5f;
        }

        TranslateAnimation topAnim = new TranslateAnimation(
                type, 0, type, 0, type, topFromY, type, topToY
        );
        topAnim.setDuration(200);
        topAnim.setFillAfter(true);

        TranslateAnimation bottomAnim = new TranslateAnimation(
                type, 0, type, 0, type, bottomFromY, type, bottomToY
        );
        bottomAnim.setDuration(200);
        bottomAnim.setFillAfter(true);

        if (isBack) {
            bottomAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}
                @Override
                public void onAnimationRepeat(Animation animation) {}
                @Override
                public void onAnimationEnd(Animation animation) {
                    mTopLine.setVisibility(View.GONE);
                    mBottomLine.setVisibility(View.GONE);
                    startNewActivity();
                }
            });
        }
        mTopLayout.startAnimation(topAnim);
        mBottomLayout.startAnimation(bottomAnim);
    }

    private void startNewActivity() {
        RetrofitServiceManager.getInstance()
                .getRetrofit()
                .create(ApiService.class)
                .getRandomAttraction()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResponse<Attraction>>() {
                    @Override
                    public void onSuccess(Object result) {
                        Attraction attraction = (Attraction) result;
                        jump(attraction);
                    }

                    @Override
                    public void onFail(String message){
                        showError(message);
                    }

                    @Override
                    public void onError(String message) {
                        showError(message);
                    }
                });
    }

    public void showError(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public void jump(Attraction attraction){
        Intent intent = DetailsActivity.newIntent(ShakeActivity.this, attraction);
        startActivity(intent);
    }
}
