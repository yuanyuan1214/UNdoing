package com.app.undoing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.undoing.Adapter.TypeBaseAdapter;
import com.app.undoing.Database.AccountBean;
import com.app.undoing.Database.DBManager;
import com.app.undoing.Database.TypeBean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BookKeep extends AppCompatActivity {

    private int selectedItem;
    String typename;
    String itemname;
    float itemmoney;

    GridView typeGv;
    List<TypeBean>typeList;
    TypeBaseAdapter adapter;
    EditText editname;
    EditText editmoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_keep);
        selectedItem=0;
        //设置下拉选择框样式
        setSpinnerStyle();

        //设置单选按钮组
        loadDataToGV();
        //添加按钮组监听事件
        setGVListener();

        //添加顶部按钮事件
        ImageButton btnBack = (ImageButton) findViewById(R.id.backmenu);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BookKeep.this , MainActivity.class);
                startActivity(i);
            }
        });

        //编辑框
        editname=(EditText) findViewById(R.id.itemname);
        editmoney=(EditText) findViewById(R.id.itemmoney);
        DigitsKeyListener numericOnlyListener = new DigitsKeyListener(false,true);
        editmoney.setKeyListener(numericOnlyListener);

        //添加底部按钮事件
        ImageButton btnSure = (ImageButton) findViewById(R.id.sureBtn);
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemname=editname.getText().toString();
                String money=editmoney.getText().toString();
                if(TextUtils.isEmpty(itemname) ||TextUtils.isEmpty(money))
                {
                    AlertDialog.Builder builder   = new AlertDialog.Builder(BookKeep.this);
                    builder.setTitle("注意") ;
                    builder.setMessage("请输入备注和金额！" ) ;
                    builder.setPositiveButton("是" ,   null );
                    builder.show();
                    return;
                }
                else
                {
                    itemmoney=Float.parseFloat(money);
                    Calendar c=Calendar.getInstance();
                    int year=c.get(Calendar.YEAR);
                    int month=c.get(Calendar.MONTH)+1;
                    int day=c.get(Calendar.DAY_OF_MONTH);
                    int week=c.get(Calendar.WEEK_OF_MONTH);
                    AccountBean bean=new AccountBean(0,typename,itemname,itemmoney,year,month,day,week,1,0,1,1,0);
                    if(selectedItem==0)
                    {
                        DBManager.insertItemToPositivetb(bean);
                    }
                    else
                    {
                        DBManager.insertItemToGreedtb(bean);
                    }
                    Intent i = new Intent(BookKeep.this , MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    //设置下拉选择框
    private void setSpinnerStyle() {
        Spinner spinner=(Spinner)findViewById(R.id.book_type_change);
        List<String> list=new ArrayList<String>();
        list.add("支出");
        list.add("种草");
        selectedItem =-1;
        ArrayAdapter<String> spinner_adapter=new ArrayAdapter<String>(this,R.layout.spinner_style,R.id.spinner_text,list) {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /* 设置GridView每一项的点击事件*/
    private void setGVListener() {
        typeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.selectPos = position;
                adapter.notifyDataSetInvalidated();  //提示绘制发生变化了
                TypeBean typeBean = typeList.get(position);
                typename = typeBean.getTypename();
            }
        });
    }

    /* 给GridView填出数据的方法*/
    public void loadDataToGV() {
        typeGv=(GridView) findViewById(R.id.frag_record_gv);
        typeList = new ArrayList<TypeBean>();
        typeList.add(new TypeBean(0,"服饰",R.drawable.ic_clothes_n,R.drawable.ic_clothes_y,0));
        typeList.add(new TypeBean(1,"饮食",R.drawable.ic_eat_n,R.drawable.ic_eat_y,0));
        typeList.add(new TypeBean(2,"文具",R.drawable.ic_wenju_n,R.drawable.ic_wenju_y,0));
        typeList.add(new TypeBean(3,"网购",R.drawable.ic_wanggou_n,R.drawable.ic_wanggou_y,0));
        typeList.add(new TypeBean(4,"交通",R.drawable.ic_jiaotong_n,R.drawable.ic_jiaotong_y,0));
        typeList.add(new TypeBean(5,"数码",R.drawable.ic_shuma_n,R.drawable.ic_shuma_y,0));
        typeList.add(new TypeBean(6,"水电",R.drawable.ic_shuidian_n,R.drawable.ic_shuidian_y,0));
        typeList.add(new TypeBean(7,"运动",R.drawable.ic_yundong_n,R.drawable.ic_yundong_y,0));
        typeList.add(new TypeBean(8,"娱乐",R.drawable.ic_yule_n,R.drawable.ic_yule_y,0));
        typeList.add(new TypeBean(9,"通信",R.drawable.ic_tongxin_n,R.drawable.ic_tongxin_y,0));
        typeList.add(new TypeBean(10,"学习",R.drawable.ic_xuexi_n,R.drawable.ic_xuexi_y,0));
        typeList.add(new TypeBean(11,"美妆",R.drawable.ic_meizhuang_n,R.drawable.ic_meizhuang_y,0));
        typeList.add(new TypeBean(12,"其他",R.drawable.ic_qita_n,R.drawable.ic_qita_y,0));

        adapter = new TypeBaseAdapter(this, typeList);
        typeGv.setAdapter(adapter);
    }
}