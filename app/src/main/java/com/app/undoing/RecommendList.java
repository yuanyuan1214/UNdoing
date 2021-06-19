package com.app.undoing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.app.undoing.Adapter.DoingListAdapter;
import com.app.undoing.Adapter.RecommendListAdapter;
import com.app.undoing.Content.DoingListItem;
import com.app.undoing.Content.RecommendListItem;
import com.app.undoing.Database.AccountBean;
import com.app.undoing.Database.DBManager;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

        //添加顶部按钮事件
        ImageButton btnBack = (ImageButton) findViewById(R.id.backmenu);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RecommendList.this , MainActivity.class);
                startActivity(i);
            }
        });

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
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        recommendList =new LinkedList<RecommendListItem>();
        List<AccountBean> greedList = DBManager.getGreedtbByDay(year,month,day);
        for (int i=0;i<greedList.size();i++) {
            AccountBean greedItem = greedList.get(i);
            System.out.println("这是greedItem.getId()"+greedItem.getId());
            Calendar dateCalendar = new GregorianCalendar(greedItem.getYear(),greedItem.getMonth(),greedItem.getDay());
            Date date = dateCalendar.getTime();
            recommendList.add(new RecommendListItem(greedItem.getTypename(),greedItem.getItemname(),greedItem.getItemmoney(),greedItem.getImagenum(),
                    new ArrayList<Integer>(
                            Arrays.asList(greedItem.getWater(),greedItem.getLand(),greedItem.getAir(),greedItem.getMineral(),greedItem.getAnimal())
                    ),date,greedItem.getWeek(),greedItem.getId()));
        }
        recommendListAdapter= new RecommendListAdapter(recommendList,RecommendList.this);
        recommend_list.setAdapter(recommendListAdapter);
    }
}