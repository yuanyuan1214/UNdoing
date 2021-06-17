package com.app.undoing;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class BillActivity extends AppCompatActivity {

    private int selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        //设置下拉选择框
        setSpinnerStyle();
    }

    //设置下拉选择框
    private void setSpinnerStyle() {
        Spinner spinner=(Spinner)findViewById(R.id.bill_type_change);
        List<String> bill_type_list=new ArrayList<String>();
        bill_type_list.add("正向账单");
        bill_type_list.add("反向账单");
        selectedItem =-1;
        ArrayAdapter<String> spinner_adapter=new ArrayAdapter<String>(this,R.layout.spinner_style,R.id.spinner_text,bill_type_list) {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = null;
                v = super.getDropDownView(position, null, parent);
                // If this is the selected item position
                if (position == selectedItem) {
                    v.setBackgroundColor(getResources().getColor(R.color.green));
                    TextView textView=(TextView) v.findViewById(R.id.spinner_text);
                    textView.setTextColor(getResources().getColor(R.color.white));
                }
                return v;
            }
        };
        spinner_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ConstraintLayout constraintLayout=(ConstraintLayout)view;
//                LinearLayout linearLayout=(LinearLayout)constraintLayout.getChildAt(0);
//                TextView textView=(TextView)linearLayout.getChildAt(0);
//                textView.setTextColor(getResources().getColor(R.color.white));
//                textView.setBackgroundColor(getResources().getColor(R.color.green));
                selectedItem=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}