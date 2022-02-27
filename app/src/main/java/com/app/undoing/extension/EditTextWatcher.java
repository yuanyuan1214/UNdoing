package com.app.undoing.extension;

import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;

import com.app.undoing.model.EditItem;

import android.os.Handler;
import android.widget.TextView;

public class EditTextWatcher implements TextWatcher {
    private EditItem editItem;
    private TextView tv;
    public EditTextWatcher(EditItem editItem,TextView tv) {
        this.editItem=editItem;
        this.tv=tv;
    }


    public static final int EDIT_OK=1000;
    private Handler mHandler=new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (EDIT_OK == msg.what) {
                String category=editItem.calculate();
                tv.setText(String.format("种类：%s", category));
            }
        }
    };

    private Runnable mRunnable = () -> mHandler.sendEmptyMessage(EDIT_OK);

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mHandler.removeCallbacks(mRunnable);
        //800毫秒没有输入认为输入完毕
        mHandler.postDelayed(mRunnable, 1000);
    }

    @Override
    public void afterTextChanged(Editable s) {
        editItem.setName(s.toString());
    }
}
