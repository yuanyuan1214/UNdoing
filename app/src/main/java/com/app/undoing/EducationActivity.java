package com.app.undoing;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.undoing.Adapter.EducationAdapter;
import com.app.undoing.interfaces.toolbarInterface;
import com.app.undoing.model.EducationList;

public class EducationActivity extends AppCompatActivity implements toolbarInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.education_list);
        recyclerView.setAdapter(new EducationAdapter(this, EducationList.getInstance().list));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        /* set up toolbar */
        ImageView toolbar_title=(ImageView) findViewById(R.id.toolbar_title);
        toolbar_title.setImageDrawable(getResources().getDrawable(R.drawable.ic_green_title));
        ImageView toolbar_education=(ImageView) findViewById(R.id.toolbar_education);
        toolbar_education.setVisibility(View.INVISIBLE);
        setupBackListener(this);
        setTitleListener(this);
    }

}
