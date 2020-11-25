package com.izak.instagramtest.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.izak.instagramtest.R;

public class CustomDialogPay extends Dialog {

    private static final Object Context = 8272;
    private Button mPositiveButton;
    private Button mNegativeButton;

    private View.OnClickListener mPositiveListener;
    private View.OnClickListener mNegativeListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.custom_2_dialog);

        //셋팅
        mPositiveButton=(Button)findViewById(R.id.custom_2_dialog_KBbutton);
        mNegativeButton=(Button)findViewById(R.id.custom_2_dialog_creditButton);

        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)
        mPositiveButton.setOnClickListener(mPositiveListener);
        mNegativeButton.setOnClickListener(mNegativeListener);
    }

    //생성자 생성
//    public CustomDialogPay(View.OnClickListener positiveListener, View.OnClickListener negativeListener) {
//        super(Context);
//
//    }
    public CustomDialogPay(View.OnClickListener positiveListener, View.OnClickListener negativeListener) {
        super((android.content.Context) Context);
        this.mPositiveListener = positiveListener;
        this.mNegativeListener = negativeListener;
    }
}