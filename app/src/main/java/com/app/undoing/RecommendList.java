package com.app.undoing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.Calendar;

public class RecommendList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_list);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        MaterialButtonToggleGroup materialButtonToggleGroup = findViewById(R.id.toggleGroup);
        materialButtonToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId==R.id.btnMonth) {

                    }
                    if (checkedId==R.id.btnWeek) {

                    }
                    if (checkedId==R.id.btnDay) {

                    }
                }
            }
        });
    }
}