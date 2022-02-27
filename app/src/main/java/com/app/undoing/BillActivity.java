package com.app.undoing;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.undoing.Adapter.AccountAdapter;
import com.app.undoing.interfaces.toolbarInterface;

import java.util.ArrayList;
import java.util.List;


public class BillActivity extends AppCompatActivity implements toolbarInterface {

    private int selectedItem;
    private AccountAdapter accountAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        selectedItem = 0;
        accountAdapter = new AccountAdapter(this);
        //设置下拉选择框样式
        setSpinnerStyle();

        RecyclerView recyclerView = findViewById(R.id.bill_list);
        recyclerView.setAdapter(accountAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        /* set up toolbar */
        ImageView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setImageDrawable(getResources().getDrawable(R.drawable.ic_light_title));
        setupBackListener(this);
        ImageView toolbar_education = findViewById(R.id.toolbar_education);
        toolbar_education.setImageDrawable(getResources().getDrawable(R.drawable.ic_education));
        setEducationListener(this);
        setTitleListener(this);

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
                    v.setBackgroundColor(getResources().getColor(R.color.spinner_font));
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
                selectedItem = position;
                int type = 0;
                if (selectedItem == 1) type = AccountAdapter.TYPE_NEGATIVE;
                else type = AccountAdapter.TYPE_POSITIVE;
                accountAdapter.getData(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}