package com.app.undoing.interfaces;

import android.text.method.DigitsKeyListener;
import android.widget.EditText;
import android.widget.TextView;

import com.app.undoing.extension.ChooeseTextWatcher;
import com.app.undoing.model.ChooseButtonItem;

public interface chooseButtonInterface {
   default void setDoubleButtonListener(TextView v1, TextView v2, ChooseButtonItem chooseButtonItem) {
       v1.setOnClickListener(view -> {chooseButtonItem.setIsnew(0);v1.setSelected(true);v2.setSelected(false);});
       v2.setOnClickListener(view -> {chooseButtonItem.setIsnew(1);v2.setSelected(true);v1.setSelected(false);});
   }

   default void setTripleButtonListener(EditText v1, EditText v2, TextView v3, ChooseButtonItem chooseButtonItem) {
       v3.setOnClickListener(view -> {
           chooseButtonItem.setWrapAtPosition(0,false);
           chooseButtonItem.setWrapAtPosition(1,false);
           chooseButtonItem.setWrapAtPosition(2,true);
           v1.setSelected(false);
           v2.setSelected(false);
           v1.setText("");
           v1.setHint("塑料___件");
           v2.setText("");
           v2.setHint("纸类___件");
           v3.setSelected(true);
       });
       DigitsKeyListener numericOnlyListener = new DigitsKeyListener(false,true);
       v1.setKeyListener(numericOnlyListener);
       v2.setKeyListener(numericOnlyListener);
       v1.addTextChangedListener(new ChooeseTextWatcher(0,v1,v3,"塑料",chooseButtonItem));
       v2.addTextChangedListener(new ChooeseTextWatcher(1,v2,v3,"纸类",chooseButtonItem));
   }
}
