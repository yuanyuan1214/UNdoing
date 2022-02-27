package com.app.undoing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.undoing.Adapter.AccountAdapter;
import com.app.undoing.interfaces.toolbarInterface;

public class RecommendList extends AppCompatActivity implements toolbarInterface {

    private AccountAdapter accountAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_list);
        accountAdapter=new AccountAdapter(this);

        RecyclerView recyclerView = findViewById(R.id.grass_list);
        recyclerView.setAdapter(accountAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        /* set up toolbar */
        ImageView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setImageDrawable(getResources().getDrawable(R.drawable.ic_yellow_title));
        setupBackListener();
        ImageView toolbar_education = findViewById(R.id.toolbar_education);
        toolbar_education.setImageDrawable(getResources().getDrawable(R.drawable.ic_education));
        setEducationListener(this);
        setTitleListener(this);

        accountAdapter.getData(AccountAdapter.TYPE_GREED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        accountAdapter.getData(AccountAdapter.TYPE_GREED);
    }

    private void setupBackListener() {
        findViewById(R.id.toolbar_back).setOnClickListener(view -> {
            finish();
        });
    }
}