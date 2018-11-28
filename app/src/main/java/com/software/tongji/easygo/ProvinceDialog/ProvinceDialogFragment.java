package com.software.tongji.easygo.ProvinceDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.software.tongji.easygo.JournalDisplayMvp.JournalDisplayActivity;
import com.software.tongji.easygo.JournalDisplayMvp.JournalDisplayFragment;
import com.software.tongji.easygo.MapMvp.MapFragment;
import com.software.tongji.easygo.MyProvinceDisplayMvp.ProvinceDisplayActivity;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.Province;
import com.software.tongji.easygo.bean.ProvinceLab;
import com.software.tongji.easygo.navigation.NavigationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class ProvinceDialogFragment extends DialogFragment {
    public static final String EXTRA_NAME = "com.software.tongji.easygo.provincedialog.name";
    private static final String ARG_NAME = "province_name";

    private Unbinder mUnbinder;
    private AlertDialog mDialog;

    @BindView(R.id.dialog_state)
    TextView mDialogState;
    @BindView(R.id.card_bac)
    ImageView mBac;
    @BindView(R.id.unlock_layout)
    LinearLayout mUnlocklayout;
    @BindView(R.id.locked_go_back)
    ImageButton mLockedBack;
    @BindView(R.id.dialog_back)
    ImageButton mDialogBack;

    @BindView(R.id.dialog_journal)
    ImageButton mJournalButton;
    @BindView(R.id.unlock)
    ImageButton mUnlockButton;
    @BindView(R.id.local_attractions)
    ImageButton mLocalAttractions;


    public static ProvinceDialogFragment newInstance(String provinceName){
        Bundle args = new Bundle();
        args.putString(ARG_NAME, provinceName);

        ProvinceDialogFragment fragment = new ProvinceDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
            attributes.gravity = Gravity.CENTER;//对齐方式
            dialog.getWindow().setAttributes(attributes);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.9), (int) (dm.heightPixels * 0.8));
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String provinceName = getArguments().getString(ARG_NAME);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.province_dialog_fragment,null);
        mUnbinder = ButterKnife.bind(this,view);


        initByProvince(provinceName);

        mDialog =  new AlertDialog.Builder(getActivity())
                .setView(view).create();
        return mDialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void initByProvince(final String provinceName) {

        Province province = ProvinceLab.getProvinceLab(getActivity()).getProvinceByName(provinceName);
        if(province.isLocked()){
            Glide.with(this).load(R.drawable.guilin_card)
                    .apply(RequestOptions.bitmapTransform(new BlurTransformation(3,15)))
                    .into(mBac);
            mUnlocklayout.setVisibility(View.VISIBLE);
        }else{
            mJournalButton.setVisibility(View.VISIBLE);
            mDialogBack.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.guilin_card)
                    .into(mBac);
        }
        mLockedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProvinceDialogFragment.this.dismiss();
            }
        });

        mUnlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest(Activity.RESULT_OK, provinceName);
                Glide.with(ProvinceDialogFragment.this).load(R.drawable.guilin_card)
                        .into(mBac);
                mJournalButton.setVisibility(View.VISIBLE);
                mDialogBack.setVisibility(View.VISIBLE);
                mUnlocklayout.setVisibility(View.GONE);
            }
        });
        mLocalAttractions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                Intent intent = new Intent(getActivity(),ProvinceDisplayActivity.class);
                startActivity(intent);
            }
        });

        mJournalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                NavigationActivity activity = (NavigationActivity)getActivity();
                activity.changeFragment();
            }
        });
        mDialogBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void sendRequest(int resultCode,String provinceName){
        if(getTargetFragment() == null){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_NAME, provinceName);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode,intent);
    }
}

