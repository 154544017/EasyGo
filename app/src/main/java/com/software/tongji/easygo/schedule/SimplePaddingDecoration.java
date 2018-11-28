package com.software.tongji.easygo.schedule;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.software.tongji.easygo.R;

public class SimplePaddingDecoration extends RecyclerView.ItemDecoration {

    private int mDividrHeight;

    public SimplePaddingDecoration(Context context){
        mDividrHeight = context.getResources().getDimensionPixelSize(R.dimen.divider_height);

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = mDividrHeight;
    }
}
