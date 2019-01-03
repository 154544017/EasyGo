package com.software.tongji.easygo.AchievementModule.ProvinceDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
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
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.Bean.Province;
import com.software.tongji.easygo.Bean.ProvinceLab;
import com.software.tongji.easygo.Navigation.NavigationActivity;
import com.software.tongji.easygo.Utils.HttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class ProvinceDialogFragment extends DialogFragment {
    public static final String EXTRA_NAME = "com.software.tongji.easygo.provincedialog.name";
    private static final String ARG_NAME = "province_name";

    private Unbinder mUnBinder;
    private AlertDialog mDialog;
    private String mProvinceName;

    @BindView(R.id.card_bac)
    ImageView mBac;
    @BindView(R.id.unlock_layout)
    LinearLayout mUnlockLayout;
    @BindView(R.id.locked_go_back)
    ImageButton mLockedBack;
    @BindView(R.id.dialog_back)
    ImageButton mDialogBack;

    @BindView(R.id.dialog_journal)
    ImageButton mJournalButton;
    @BindView(R.id.unlock_text)
    TextView mUnlockText;
    @BindView(R.id.unlock_image)
    ImageButton mUnlockButton;
    @BindView(R.id.attraction_text)
    TextView mAttractionText;
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
        mProvinceName = getArguments().getString(ARG_NAME);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.province_dialog_fragment,null);
        mUnBinder = ButterKnife.bind(this,view);


        initByProvince(mProvinceName);

        mDialog =  new AlertDialog.Builder(getActivity())
                .setView(view).create();
        return mDialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    private void initByProvince(final String provinceName) {

        Province province = ProvinceLab.getProvinceLab(getActivity()).getProvinceByName(provinceName);
        if(province.isLocked()){
            RequestOptions options = getRequestOption();
            Glide.with(this).load(HttpUtils.getProvinceDisplayImageUrl(province.getPinYin()))
                    .apply(options)
                    .apply(RequestOptions.bitmapTransform(new BlurTransformation(3,15)))
                    .into(mBac);
            mUnlockLayout.setVisibility(View.VISIBLE);
        }else{
            mJournalButton.setVisibility(View.VISIBLE);
            mDialogBack.setVisibility(View.VISIBLE);

            RequestOptions options = getRequestOption();
            Glide.with(this)
                    .load(HttpUtils.getProvinceDisplayImageUrl(province.getPinYin()))
                    .apply(options)
                    .into(mBac);
        }
        mLockedBack.setOnClickListener(v -> ProvinceDialogFragment.this.dismiss());

        mUnlockText.setOnClickListener(v -> {
            sendRequest(provinceName);
            RequestOptions requestOptions = getRequestOption();
            Glide.with(ProvinceDialogFragment.this).load(HttpUtils.getProvinceDisplayImageUrl(province.getPinYin()))
                    .apply(requestOptions)
                    .into(mBac);
            mJournalButton.setVisibility(View.VISIBLE);
            mDialogBack.setVisibility(View.VISIBLE);
            mUnlockLayout.setVisibility(View.GONE);
        });

        mUnlockButton.setOnClickListener(v -> {
            sendRequest(provinceName);
            RequestOptions requestOptions = getRequestOption();
            Glide.with(ProvinceDialogFragment.this).load(HttpUtils.getProvinceDisplayImageUrl(province.getPinYin()))
                    .apply(requestOptions)
                    .into(mBac);
            mJournalButton.setVisibility(View.VISIBLE);
            mDialogBack.setVisibility(View.VISIBLE);
            mUnlockLayout.setVisibility(View.GONE);
        });

        mAttractionText.setOnClickListener(v -> {
            mDialog.dismiss();
            NavigationActivity activity = (NavigationActivity)getActivity();
            if (activity != null) {
                activity.changeSearchFragmentWithArgs(mProvinceName);
            }

        });

        mLocalAttractions.setOnClickListener(v -> {
            mDialog.dismiss();
            NavigationActivity activity = (NavigationActivity)getActivity();
            if (activity != null) {
                activity.changeSearchFragmentWithArgs(mProvinceName);
            }

        });

        mJournalButton.setOnClickListener(v -> {
            mDialog.dismiss();
            NavigationActivity activity = (NavigationActivity)getActivity();
            if (activity != null) {
                activity.changeJournalFragmentWithArgs(mProvinceName);
            }
        });
        mDialogBack.setOnClickListener(v -> dismiss());
    }

    private void sendRequest(String provinceName){
        if(getTargetFragment() == null){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_NAME, provinceName);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
    }

    private RequestOptions getRequestOption(){
        return new RequestOptions()
                .placeholder(R.drawable.province_place_holder)
                .centerCrop();
    }
}

