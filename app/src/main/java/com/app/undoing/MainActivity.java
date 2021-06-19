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
import java.util.GregorianCalendar;
import java.util.LinkedList;

import com.app.undoing.Database.AccountBean;
import com.app.undoing.Database.DBManager;
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
        int week=calendar.get(Calendar.WEEK_OF_MONTH);
        List<Float> weekMoney=DBManager.getWeekMoney(year,month,week);
        List<Integer> weekPoints=DBManager.getWeekPoints(year,month,week);
        TextView positive=(TextView) findViewById(R.id.positive);
        TextView negative=(TextView) findViewById(R.id.negative);
        TextView positiveP=(TextView)findViewById(R.id.positive_points);
        TextView negativeP=(TextView)findViewById(R.id.negative_points);
        positive.setText(String.format("%.1f",weekMoney.get(0)));
        negative.setText(String.format("%.1f",weekMoney.get(1)));
        positiveP.setText(weekPoints.get(0).toString());
        negativeP.setText(weekPoints.get(1).toString());

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
        ImageButton btnBook = (ImageButton) findViewById(R.id.bookbutton);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this , BookKeep.class);
                startActivity(i);
            }
        });
    }

    private void setDoingList() throws ParseException {
        doing_list = (ListView) findViewById(R.id.doingList);
        initList =new LinkedList<DoingListItem>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        List<AccountBean> positiveList = DBManager.getPositivetbByDay(year,month,day);
        for (int i=0;i<positiveList.size();i++) {
            AccountBean positiveItem = positiveList.get(i);
            Calendar dateCalendar = new GregorianCalendar(positiveItem.getYear(),positiveItem.getMonth(),positiveItem.getDay());
            Date date = dateCalendar.getTime();
            initList.add(new DoingListItem(positiveItem.getItemname(),positiveItem.getItemmoney(),positiveItem.getImagenum(),date));
        }
        List<AccountBean> negativeList = DBManager.getNegativetbByDay(year,month,day);
        for (int i=0;i<negativeList.size();i++) {
            AccountBean negativeItem = negativeList.get(i);
            Calendar dateCalendar = new GregorianCalendar(negativeItem.getYear(),negativeItem.getMonth(),negativeItem.getDay());
            Date date = dateCalendar.getTime();
            initList.add(new DoingListItem(negativeItem.getItemname(),negativeItem.getItemmoney()*(-1),negativeItem.getImagenum(),date));
        }
        listAdapter = new DoingListAdapter(initList,MainActivity.this);
        doing_list.setAdapter(listAdapter);
    }
}