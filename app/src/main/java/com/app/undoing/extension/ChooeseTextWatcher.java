package com.app.undoing.extension;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.app.undoing.model.ChooseButtonItem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChooeseTextWatcher implements TextWatcher {
    private int id;
    private EditText et;
    private TextView tv;
    private String name;
    private ChooseButtonItem chooseButtonItem;

    public ChooeseTextWatcher(int id, EditText et, TextView tv, String name, ChooseButtonItem chooseButtonItem){
        this.id=id;
        this.et=et;
        this.tv = tv;
        this.name=name;
        this.chooseButtonItem=chooseButtonItem;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.toString().equals("")) return;
        if(isNumeric(s.toString())){
            int number=Integer.parseInt(s.toString());
            if(number==0) {
                et.setText("");
                et.setHint(String.format("%s___件",name));
                et.setSelected(false);
                chooseButtonItem.setWrapNumberAtPosition(id,0);
                return;
            }
            et.setText("");
            et.setHint(String.format("%s %s 件", name, s.toString()));
            et.setSelected(true);
            tv.setSelected(false);
            chooseButtonItem.setWrapAtPosition(id,true);
            chooseButtonItem.setWrapNumberAtPosition(id,number);
            chooseButtonItem.setWrapAtPosition(2,false);
        }
        else et.setText("");
    }

    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9][0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

}
