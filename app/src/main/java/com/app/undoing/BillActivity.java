package com.app.undoing;

import android.os.Bundle;

import com.app.undoing.Adapter.DoingListAdapter;
import com.app.undoing.Adapter.UndoListAdapter;
import com.app.undoing.Content.DoingListItem;
import com.app.undoing.Content.UnDoListItem;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class BillActivity extends AppCompatActivity {

    private int selectedItem;

    //正向账单
    private LinkedList<DoingListItem> doingList;
    //好像是用于处理ListView的动态添加的
    private DoingListAdapter doingListAdapter;
    //反向账单
    private LinkedList<UnDoListItem> undoList;
    private UndoListAdapter undoListAdapter;
    private ListView Bill_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        selectedItem=0;
        //设置下拉选择框样式
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
                selectedItem=position;
                //设置列表
                try {
                    setDoingList();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setDoingList() throws ParseException {
        Bill_list = (ListView) findViewById(R.id.bill_list);
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=dateFormat.parse("2021-06-17");
        System.out.println("选择项"+selectedItem);
        if (selectedItem == 0) {
            doingList =new LinkedList<DoingListItem>();
            doingList.add(new DoingListItem("买水果",-21.00,R.drawable.dashicons_fruit, date));
            doingListAdapter = new DoingListAdapter(doingList,BillActivity.this);
            doingListAdapter.addItemData(new DoingListItem("五角场看电影",-48.00,R.drawable.dashicons_editor_video, date));
            doingListAdapter.addItemData(new DoingListItem("请同学吃饭",-121.00,R.drawable.dashicons_food, date));
            doingListAdapter.addItemData(new DoingListItem("网购洗发水",-78.00,R.drawable.dashicons_cart, date));
            doingListAdapter.addItemData(new DoingListItem("预订回家机票",-830.00,R.drawable.dashicons_plane,date));
            doingListAdapter.addItemData(new DoingListItem("买狗粮",-120.00,R.drawable.dashicons_pets,date));
            Bill_list.setAdapter(doingListAdapter);
            date=dateFormat.parse("2021-06-18");
            doingListAdapter.addItemData(new DoingListItem("请同学吃饭",-121.00,R.drawable.dashicons_food,date));
            doingListAdapter.addItemData(new DoingListItem("网购洗发水",-78.00,R.drawable.dashicons_cart,date));
            doingListAdapter.addItemData(new DoingListItem("预订回家机票",-830.00,R.drawable.dashicons_plane,date));
            doingListAdapter.addItemData(new DoingListItem("买狗粮",-120.00,R.drawable.dashicons_pets,date));
        }
        else {
            undoList =new LinkedList<UnDoListItem>();
            undoList.add(new UnDoListItem("买水果",21.00,R.drawable.dashicons_fruit, date));
            undoListAdapter = new UndoListAdapter(undoList,BillActivity.this);
            undoListAdapter.addItemData(new UnDoListItem("五角场看电影",48.00,R.drawable.dashicons_editor_video, date));
            undoListAdapter.addItemData(new UnDoListItem("请同学吃饭",121.00,R.drawable.dashicons_food, date));
            undoListAdapter.addItemData(new UnDoListItem("网购洗发水",78.00,R.drawable.dashicons_cart, date));
            undoListAdapter.addItemData(new UnDoListItem("预订回家机票",830.00,R.drawable.dashicons_plane,date));
            undoListAdapter.addItemData(new UnDoListItem("买狗粮",120.00,R.drawable.dashicons_pets,date));
            Bill_list.setAdapter(undoListAdapter);
            date=dateFormat.parse("2021-06-19");
            undoListAdapter.addItemData(new UnDoListItem("请同学吃饭",121.00,R.drawable.dashicons_food,date));
            undoListAdapter.addItemData(new UnDoListItem("网购洗发水",78.00,R.drawable.dashicons_cart,date));
            undoListAdapter.addItemData(new UnDoListItem("预订回家机票",830.00,R.drawable.dashicons_plane,date));
            undoListAdapter.addItemData(new UnDoListItem("买狗粮",120.00,R.drawable.dashicons_pets,date));
        }
    }
}