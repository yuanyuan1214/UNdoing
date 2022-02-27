package com.app.undoing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.undoing.Adapter.TypeBaseAdapter;
import com.app.undoing.Database.AccountBean;
import com.app.undoing.Database.CalenderForOne;
import com.app.undoing.Database.DBManager;
import com.app.undoing.databinding.ActivityBookKeepBinding;
import com.app.undoing.extension.EditTextWatcher;
import com.app.undoing.interfaces.chooseButtonInterface;
import com.app.undoing.interfaces.toolbarInterface;
import com.app.undoing.model.ChooseButtonItem;
import com.app.undoing.model.EditItem;
import com.app.undoing.model.TypeItem;

import java.util.ArrayList;
import java.util.List;

public class BookKeep extends AppCompatActivity implements chooseButtonInterface, toolbarInterface {

    private int selectedItem;
    String typename;
    String itemname;
    float itemmoney;

    ChooseButtonItem chooseButtonItem=new ChooseButtonItem();
    EditItem editItem=new EditItem();
    GridView typeGv;
    List<TypeItem>typeList;
    TypeBaseAdapter adapter;
    ImageView toolbar_title;
    EditText editmoney;
    Spinner spinner;
    TextView detail_type;
    AccountBean receiveBean=null;

    ActivityBookKeepBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_keep);
        binding.setGrassTheme(false);


        //设置下拉选择框样式
        setSpinnerStyle();
        spinner.setSelection(0);
        //设置单选按钮组
        loadDataToGV();
        typename=typeList.get(0).getTypename();
        editItem.setBigTypeName(typename);
        //添加按钮组监听事件
        setGVListener();

        //编辑框
        EditText editname=(EditText) findViewById(R.id.itemname);
        detail_type=findViewById(R.id.item_detail_type);
        editname.addTextChangedListener(new EditTextWatcher(editItem,detail_type));
        editmoney=(EditText) findViewById(R.id.itemmoney);
        DigitsKeyListener numericOnlyListener = new DigitsKeyListener(false,true);
        editmoney.setKeyListener(numericOnlyListener);
        TextView isnew_v1=findViewById(R.id.source_0);
        TextView isnew_v2=findViewById(R.id.source_1);
        setDoubleButtonListener(isnew_v1,isnew_v2,chooseButtonItem);
        EditText wrap_v1=findViewById(R.id.wrap_0);
        EditText wrap_v2=findViewById(R.id.wrap_1);
        TextView wrap_v3=findViewById(R.id.wrap_2);
        setTripleButtonListener(wrap_v1,wrap_v2,wrap_v3,chooseButtonItem);

        //添加底部按钮事件
        ImageButton btnSure = (ImageButton) findViewById(R.id.sureBtn);
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemname=editItem.getName();
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
                if(selectedItem==0&&(chooseButtonItem.getIsnew()==-1||chooseButtonItem.getWrapChooseState()==false)){
                    AlertDialog.Builder builder   = new AlertDialog.Builder(BookKeep.this);
                    builder.setTitle("注意") ;
                    builder.setMessage("请选择来源和包装！" ) ;
                    builder.setPositiveButton("是" ,   null );
                    builder.show();
                    return;
                }
                itemmoney=Float.parseFloat(money);
                //WEEK和MainActivity不一致是超级大坑！！！
//                    Calendar c=Calendar.getInstance();
//                    int year=c.get(Calendar.YEAR);
//                    int month=c.get(Calendar.MONTH)+1;
//                    int day=c.get(Calendar.DAY_OF_MONTH);
//                    int week=c.get(Calendar.WEEK_OF_MONTH);
//                    int imagenum=typeImage.get(typename);

                int year= CalenderForOne.getYear();
                int month=CalenderForOne.getMonth();
                int day=CalenderForOne.getDay();
                int week=CalenderForOne.getWeekOfMonth();
                float carbon=(chooseButtonItem.getIsnew()==1) ? 0.0f : editItem.getSelfCarbon();
                AccountBean bean=new AccountBean(0,typename,itemname,itemmoney,year,month,day,week,chooseButtonItem.getIsnew(),carbon,chooseButtonItem.calculate());
                if(receiveBean!=null){
                    DBManager.deleteItemFromGreedtbById(receiveBean.getId());
                }
                if(selectedItem==0)
                {
                    DBManager.insertItemToPositivetb(bean);
                    Intent i = new Intent(BookKeep.this , BillActivity.class);
                    startActivity(i);
                    finish();
                }
                else if(selectedItem==1)
                {
                    DBManager.insertItemToGreedtb(bean);
                    Intent i = new Intent(BookKeep.this ,RecommendList.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        /* set up toolbar */
        toolbar_title = findViewById(R.id.toolbar_title);
        setupBackListener(this);
        ImageView toolbar_education = findViewById(R.id.toolbar_education);
        toolbar_education.setImageDrawable(getResources().getDrawable(R.drawable.ic_education));
        setEducationListener(this);
        setTitleListener(this);

        //从种草界面跳转过来
        receiveBean= (AccountBean) getIntent().getSerializableExtra("accountBean");
        if(receiveBean!=null){
            editname.setText(receiveBean.getItemname());
            editmoney.setText(String.valueOf(receiveBean.getItemmoney()));
            spinner.setEnabled(false);
            typename=receiveBean.getTypename();
            editItem.setBigTypeName(typename);
            final int[] i = {0};
            ViewTreeObserver viewTreeObserver = typeGv.getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    i[0]++;
                    if(i[0]==3){
                        typeGv.getChildAt(0).findViewById(R.id.item_recordfrag_back).setSelected(false);
                        typeGv.getChildAt(0).findViewById(R.id.item_recordfrag_iv).setSelected(false);
                        typeGv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        for(int i=0;i<typeList.size();++i){
                            if(typeList.get(i).getTypename().equals(typename)){
                                typeGv.getChildAt(i).findViewById(R.id.item_recordfrag_back).setSelected(true);
                                typeGv.getChildAt(i).findViewById(R.id.item_recordfrag_iv).setSelected(true);
                                break;
                            }
                        }
                    }
                }
            });
        }

    }


    //设置下拉选择框
    private void setSpinnerStyle() {
        spinner=(Spinner)findViewById(R.id.book_type_change);
        List<String> list=new ArrayList<String>();
        list.add("支出");
        list.add("种草");
        ArrayAdapter<String> spinner_adapter=new ArrayAdapter<String>(this,R.layout.spinner_style,R.id.spinner_text,list) {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = null;
                v = super.getDropDownView(position, null, parent);
                // If this is the selected item position
                if (position == selectedItem) {
                    if(selectedItem==0){
                        v.setBackgroundColor(getResources().getColor(R.color.spinner_font));
                        TextView textView=(TextView) v.findViewById(R.id.spinner_text);
                        textView.setTextColor(getResources().getColor(R.color.white));
                    }
                    else if(selectedItem==1){
                        v.setBackgroundColor(getResources().getColor(R.color.type_image_grass));
                        TextView textView=(TextView) v.findViewById(R.id.spinner_text);
                        textView.setTextColor(getResources().getColor(R.color.grass_detail_background));
                    }
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
                if(selectedItem==0){
                    binding.setGrassTheme(false);
                    adapter.setType(0);
                    toolbar_title.setImageDrawable(getResources().getDrawable(R.drawable.ic_light_title));
                }
                else if(selectedItem==1){
                    binding.setGrassTheme(true);
                    adapter.setType(1);
                    toolbar_title.setImageDrawable(getResources().getDrawable(R.drawable.ic_yellow_title));
                }
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
                TypeItem typeItem = typeList.get(position);
                typename = typeItem.getTypename();
                editItem.setBigTypeName(typename);
            }
        });
    }

    /* 给GridView填出数据的方法*/
    public void loadDataToGV() {
        typeGv=(GridView) findViewById(R.id.frag_record_gv);
        typeList = new ArrayList<TypeItem>();
        typeList.add(new TypeItem(0,"服饰",R.drawable.ic_clothes));
        typeList.add(new TypeItem(1,"小物",R.drawable.ic_tinythings));
        typeList.add(new TypeItem(2,"日化",R.drawable.ic_daily));
        typeList.add(new TypeItem(3,"学习",R.drawable.ic_study));
        typeList.add(new TypeItem(4,"数码",R.drawable.ic_digit));
        typeList.add(new TypeItem(5,"餐饮",R.drawable.ic_eat));
        typeList.add(new TypeItem(6,"零食",R.drawable.ic_snack));
        typeList.add(new TypeItem(7,"交通",R.drawable.ic_transport));
        typeList.add(new TypeItem(8,"旅行",R.drawable.ic_travel));
        typeList.add(new TypeItem(9,"娱乐",R.drawable.ic_entertain));
        typeList.add(new TypeItem(10,"医疗",R.drawable.ic_medical));
        typeList.add(new TypeItem(11,"通讯",R.drawable.ic_communication));
        typeList.add(new TypeItem(12,"校园",R.drawable.ic_school));
        typeList.add(new TypeItem(13,"水电",R.drawable.ic_water));
        typeList.add(new TypeItem(14,"洗衣",R.drawable.ic_wash));
        typeList.add(new TypeItem(15,"其他",R.drawable.ic_other));
        adapter = new TypeBaseAdapter(this, typeList);
        typeGv.setAdapter(adapter);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev==null) return super.dispatchTouchEvent(null);
        if (this.getCurrentFocus() != null && ev.getAction() == MotionEvent.ACTION_DOWN) {
            int[] sourceCoordinates = new int[2];
            View editText = this.getCurrentFocus();
            editText.getLocationOnScreen(sourceCoordinates);
            float x = ev.getRawX() + editText.getLeft() - sourceCoordinates[0];
            float y = ev.getRawY() + editText.getTop() - sourceCoordinates[1];
            if (x < editText.getLeft() || x > editText.getRight()  || y < editText.getTop() - 10 || y > editText.getBottom() + 10) {
                hideKeyboard(this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void hideKeyboard(Activity activity) {
        try {
            ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);
            activity.getWindow().getDecorView().getRootView().clearFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}