package com.app.undoing;

import android.content.Intent;
import android.os.Bundle;

import com.app.undoing.Adapter.DoingListAdapter;
import com.app.undoing.Content.DoingListItem;
import com.app.undoing.Database.AccountBean;
import com.app.undoing.Database.CalenderForOne;
import com.app.undoing.Database.DBManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;


public class BillActivity extends AppCompatActivity {

    private int selectedItem;

    //正向账单
    public LinkedList<DoingListItem> doingList;
    //好像是用于处理ListView的动态添加的
    private DoingListAdapter doingListAdapter;
    //反向账单
    public LinkedList<DoingListItem> undoList;
    private DoingListAdapter undoListAdapter;
    private ListView Bill_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        selectedItem=0;
        //设置下拉选择框样式
        setSpinnerStyle();

        //添加顶部按钮事件
        ImageButton btnBack = (ImageButton) findViewById(R.id.backmenu);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BillActivity.this , MainActivity.class);
                startActivity(i);
            }
        });
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
                System.out.println("选择项"+selectedItem);
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
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH)+1;
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year= CalenderForOne.getYear();
        int month=CalenderForOne.getMonth();
        int day=CalenderForOne.getDay();
        if (selectedItem == 0) {
            doingList =new LinkedList<DoingListItem>();
            List<AccountBean> positiveList = DBManager.getPositivetbByDay(year,month,day);
            for (int i=0;i<positiveList.size();i++) {
                AccountBean positiveItem = positiveList.get(i);
                Calendar dateCalendar = new GregorianCalendar(positiveItem.getYear(),positiveItem.getMonth(),positiveItem.getDay());
                Date date = dateCalendar.getTime();
                doingList.add(new DoingListItem(positiveItem.getItemname(),positiveItem.getItemmoney()*(-1),positiveItem.getImagenum(),date));
            }
            doingListAdapter = new DoingListAdapter(doingList,BillActivity.this);
            Bill_list.setAdapter(doingListAdapter);
        }
        else {
            undoList =new LinkedList<DoingListItem>();
            List<AccountBean> negativeList = DBManager.getNegativetbByDay(year,month,day);
            for (int i=0;i<negativeList.size();i++) {
                AccountBean negativeItem = negativeList.get(i);
                Calendar dateCalendar = new GregorianCalendar(negativeItem.getYear(),negativeItem.getMonth(),negativeItem.getDay());
                Date date = dateCalendar.getTime();
                undoList.add(new DoingListItem(negativeItem.getItemname(),negativeItem.getItemmoney(),negativeItem.getImagenum(),date));
            }
            undoListAdapter = new DoingListAdapter(undoList,BillActivity.this);
            Bill_list.setAdapter(undoListAdapter);
        }
    }
}