package com.app.undoing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.undoing.Adapter.DoingListAdapter;
import com.app.undoing.Content.DoingListItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import com.app.undoing.R;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinkedList<DoingListItem> initList;
    //好像是用于处理ListView的动态添加的
    private DoingListAdapter listAdapter;
    private ListView doing_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        TextView yy=(TextView) findViewById(R.id.year);
        yy.setText(Integer.toString(year));
        TextView mm=(TextView) findViewById(R.id.month);
        mm.setText(Integer.toString(month));
        TextView dd=(TextView) findViewById(R.id.day);
        dd.setText(Integer.toString(day));
        TextView ww=(TextView) findViewById(R.id.week);
        ww.setText(Integer.toString(weekOfYear));
        //设置list列表
        try {
            setDoingList();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //设置页面跳转
        ImageButton btn = (ImageButton) findViewById(R.id.bt1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this , BillActivity.class);
                startActivity(i);
            }
        });
        ImageButton btn3 = (ImageButton) findViewById(R.id.bt3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this , RecommendList.class);
                startActivity(i);
            }
        });
    }

    private void setDoingList() throws ParseException {
        doing_list = (ListView) findViewById(R.id.doingList);
        initList =new LinkedList<DoingListItem>();
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=dateFormat.parse("2021-06-17");
        initList.add(new DoingListItem("买水果",-21.00,R.drawable.dashicons_fruit, date));
        listAdapter = new DoingListAdapter(initList,MainActivity.this);
        doing_list.setAdapter(listAdapter);
        listAdapter.addItemData(new DoingListItem("五角场看电影",-48.00,R.drawable.dashicons_editor_video, date));
        listAdapter.addItemData(new DoingListItem("请同学吃饭",-121.00,R.drawable.dashicons_food, date));
        listAdapter.addItemData(new DoingListItem("网购洗发水",-78.00,R.drawable.dashicons_cart, date));
        listAdapter.addItemData(new DoingListItem("预订回家机票",-830.00,R.drawable.dashicons_plane,date));
        listAdapter.addItemData(new DoingListItem("买狗粮",-120.00,R.drawable.dashicons_pets,date));
        date=dateFormat.parse("2021-06-18");
        listAdapter.addItemData(new DoingListItem("请同学吃饭",-121.00,R.drawable.dashicons_food,date));
        listAdapter.addItemData(new DoingListItem("网购洗发水",-78.00,R.drawable.dashicons_cart,date));
        listAdapter.addItemData(new DoingListItem("预订回家机票",-830.00,R.drawable.dashicons_plane,date));
        listAdapter.addItemData(new DoingListItem("买狗粮",-120.00,R.drawable.dashicons_pets,date));
    }
}