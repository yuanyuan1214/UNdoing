package com.app.undoing;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.undoing.Database.AccountBean;
import com.app.undoing.Database.CalenderForOne;
import com.app.undoing.Database.DBManager;
import com.app.undoing.extension.LGvideoview;
import com.app.undoing.interfaces.toolbarInterface;
import com.app.undoing.model.VideoItem;

import java.util.List;

public class MainActivity extends AppCompatActivity implements toolbarInterface {

    LGvideoview loginVv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH)+1;
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        calendar.setFirstDayOfWeek(Calendar.MONDAY);
//        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

        int year= CalenderForOne.getYear();
        int month=CalenderForOne.getMonth();
        int day = CalenderForOne.getDay();
        int weekOfYear = CalenderForOne.getWeekOfYear();
        TextView yy = (TextView) findViewById(R.id.year);
        yy.setText(Integer.toString(year));
        TextView mm = (TextView) findViewById(R.id.month);
        mm.setText(Integer.toString(month));
        TextView dd = (TextView) findViewById(R.id.day);
        dd.setText(Integer.toString(day));
        TextView ww = (TextView) findViewById(R.id.week);
        ww.setText(Integer.toString(weekOfYear));
        loginVv=findViewById(R.id.login_vv);

        setTable();

        //设置页面跳转
        ImageButton btn = (ImageButton) findViewById(R.id.main_book);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, BillActivity.class);
                startActivity(i);
            }
        });
        ImageButton btn3 = (ImageButton) findViewById(R.id.main_grass);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this , RecommendList.class);
                startActivity(i);
            }
        });
        ImageButton btnBook = (ImageButton) findViewById(R.id.main_book_keep);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DBManager.insertItemToPositivetb(new AccountBean(0, "学习", "买了笔", 20.5f, 2022, 2, 12, 2, 0, 100.5f, 10.2f));
//                DBManager.insertItemToNegativetb(new AccountBean(0, "学习", "买了笔", 20.5f, 2022, 2, 12, 2, 0, 100.5f, 10.2f));
//                DBManager.insertItemToGreedtb(new AccountBean(0, "学习", "买了笔", 20.5f, 2022, 2, 12, 2, 0, 100.5f, 10.2f));
                Intent i = new Intent(MainActivity.this , BookKeep.class);
                startActivity(i);
            }
        });

        /* set up toolbar */
        ImageView toolbar_back = findViewById(R.id.toolbar_back);
        toolbar_back.setVisibility(View.INVISIBLE);
        ImageView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setImageDrawable(getResources().getDrawable(R.drawable.ic_dark_title));
        ImageView toolbar_education = findViewById(R.id.toolbar_education);
        toolbar_education.setImageDrawable(getResources().getDrawable(R.drawable.ic_education_dark));
        setEducationListener(this);

        initVideView();
    }

    //播放视频背景
    private void initVideView(){
        //播放路径
        loginVv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + VideoItem.getVideoResource()));
        //播放
        loginVv.start();
        //循环播放
        loginVv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                loginVv.start();
            }
        });
    }

    private void setTable(){
        int year= CalenderForOne.getYear();
        int month=CalenderForOne.getMonth();
        int week=CalenderForOne.getWeekOfMonth();
        List<Float> weekMoney=DBManager.getWeekMoney(year,month,week);
        List<Float> weekCarbon=DBManager.getWeekPoints(year,month,week);
        TextView positive=(TextView) findViewById(R.id.positive);
        TextView negative=(TextView) findViewById(R.id.negative);
        TextView positiveP=(TextView)findViewById(R.id.positive_points);
        TextView negativeP=(TextView)findViewById(R.id.negative_points);
        positive.setText(String.format("%.1f",weekMoney.get(0)));
        negative.setText(String.format("%.1f",weekMoney.get(1)));
        positiveP.setText(String.format("%.1f",weekCarbon.get(0)));
        negativeP.setText(String.format("%.1f",weekCarbon.get(1)));
    }

    @Override
    protected void onRestart() {
        //返回重新加载
        initVideView();
        setTable();
        super.onRestart();
    }


    @Override
    protected void onStop() {
        //防止锁屏或者弹出的时候，音乐在播放
        loginVv.stopPlayback();
        super.onStop();
    }

}