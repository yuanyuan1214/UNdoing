package com.app.undoing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.app.undoing.Adapter.DoingListAdapter;
import com.app.undoing.Adapter.RecommendListAdapter;
import com.app.undoing.Content.DoingListItem;
import com.app.undoing.Content.RecommendListItem;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
        try {
            setRecommendList();
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
    private ListView recommend_list;
    private LinkedList<RecommendListItem> recommendList;
    private RecommendListAdapter recommendListAdapter;
    private void setRecommendList() throws ParseException {
        recommend_list=(ListView)findViewById(R.id.recommend_list);
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=dateFormat.parse("2021-06-18");
        recommendList =new LinkedList<RecommendListItem>();
        recommendList.add(new RecommendListItem("衣物","UT联名T恤",130,R.drawable.dashicons_cart,new ArrayList<Integer>(Arrays.asList(2,1,2,2,2)),date));
        recommendListAdapter= new RecommendListAdapter(recommendList,RecommendList.this);
        recommendListAdapter.addItemData(new RecommendListItem("美妆","KATE彩妆盒",200,R.drawable.dashicons_editor_video, new ArrayList<>(Arrays.asList(1, 1, 2, 2, 2)),date));
        recommendListAdapter.addItemData(new RecommendListItem("数码","无线蓝牙耳机",230,R.drawable.dashicons_computer, new ArrayList<>(Arrays.asList(1, 1, 1, 2, 0)),date));
    }
}