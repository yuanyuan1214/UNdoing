package com.app.undoing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.undoing.Adapter.DoingListAdapter;
import com.app.undoing.Content.DoingListItem;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.app.undoing.Database.AccountBean;
import com.app.undoing.Database.CalenderForOne;
import com.app.undoing.Database.DBManager;
import com.app.undoing.R;
import java.util.List;
import java.util.Map;

import com.baidu.aip.asrwakeup3.core.mini.ActivityMiniRecog;
import com.baidu.aip.asrwakeup3.core.mini.AutoCheck;
import com.baidu.aip.asrwakeup3.core.recog.MyRecognizer;
import com.baidu.aip.asrwakeup3.core.recog.listener.IRecogListener;
import com.baidu.aip.asrwakeup3.core.recog.listener.MessageStatusRecogListener;
import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements EventListener {

    private LinkedList<DoingListItem> initList;
    //好像是用于处理ListView的动态添加的
    private DoingListAdapter listAdapter;
    private ListView doing_list;

    //语音识别变量
    private EventManager asr;
    private int State;
    private AccountBean inputData = new AccountBean();
    private int operationType; //1表示记账，2表示种草
    private int runTime;//循环次数

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

        // 初始化EventManager对象
        asr = EventManagerFactory.create(this, "asr");
        // 基于sdk集成1.3 注册自己的输出事件类
        asr.registerListener(this); //  EventListener 中 onEvent方法


        int year= CalenderForOne.getYear();
        int month=CalenderForOne.getMonth();
        int day=CalenderForOne.getDay();
        int weekOfYear=CalenderForOne.getWeekOfYear();
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
        int week=CalenderForOne.getWeekOfMonth();
        System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"+week);
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
        btnBook.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                State=0;
                runTime=0;
                playMedia("welcome.mp3");
                return true;
            }
        });
    }

    private void playMedia(String fileName) {
        AssetManager assetManager;
        MediaPlayer player = null;
        player = new MediaPlayer();
        assetManager = getResources().getAssets();
        try {
            AssetFileDescriptor fileDescriptor = assetManager.openFd(fileName);
            player.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            player.prepare();
            player.start();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();

        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);

        // 请先使用如‘在线识别’界面测试和生成识别参数。 params同ActivityRecog类中myRecognizer.start(params);
        // 复制此段可以自动检测错误
        (new AutoCheck(getApplicationContext(), new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 100) {
                    AutoCheck autoCheck = (AutoCheck) msg.obj;
                    synchronized (autoCheck) {
                        String message = autoCheck.obtainErrorMessage(); // autoCheck.obtainAllMessage();
                        ; // 可以用下面一行替代，在logcat中查看代码
                        Log.w("AutoCheckMessage", message);
                    }
                }
            }
        }, false)).checkAsr(params);

        String json = null; // 可以替换成自己的json
        json = new JSONObject(params).toString();

        asr.send(SpeechConstant.ASR_START, json, null, 0, 0);

    }

    private void setDoingList() throws ParseException {
        doing_list = (ListView) findViewById(R.id.doingList);
        initList =new LinkedList<DoingListItem>();
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH)+1;
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year= CalenderForOne.getYear();
        int month=CalenderForOne.getMonth();
        int day=CalenderForOne.getDay();
        List<AccountBean> positiveList = DBManager.getPositivetbByDay(year,month,day);
        for (int i=0;i<positiveList.size();i++) {
            AccountBean positiveItem = positiveList.get(i);
            Calendar dateCalendar = new GregorianCalendar(positiveItem.getYear(),positiveItem.getMonth(),positiveItem.getDay());
            Date date = dateCalendar.getTime();
            initList.add(new DoingListItem(positiveItem.getItemname(),positiveItem.getItemmoney()*(-1),positiveItem.getImagenum(),date));
        }
        List<AccountBean> negativeList = DBManager.getNegativetbByDay(year,month,day);
        for (int i=0;i<negativeList.size();i++) {
            AccountBean negativeItem = negativeList.get(i);
            Calendar dateCalendar = new GregorianCalendar(negativeItem.getYear(),negativeItem.getMonth(),negativeItem.getDay());
            Date date = dateCalendar.getTime();
            initList.add(new DoingListItem(negativeItem.getItemname(),negativeItem.getItemmoney(),negativeItem.getImagenum(),date));
        }
        listAdapter = new DoingListAdapter(initList,MainActivity.this);
        doing_list.setAdapter(listAdapter);
    }

    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {
        String logTxt = "name: " + name;

        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_FINISH)) {
            System.out.println("分支选择前"+State);
            switch (State) {
                case 0:
                    if (runTime<3) {
                        playMedia("welcome.mp3");
                        runTime++;
                    }
                    break;
                case 1://分类
                    if (runTime<3) {
                        playMedia("objectType.mp3");
                        runTime++;
                    }
                    break;
                case 2://金额
                    if (runTime<3) {
                        playMedia("Money.mp3");
                        runTime++;
                    }
                    break;
                case 3://备注
                    if(runTime<3) {
                        playMedia("itemName.mp3");
                        runTime++;
                    }
                    break;
                case 4://播报
                    break;
                default:break;
            }
            return;
        }

        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
            // 识别相关的结果都在这里
            if (params == null || params.isEmpty()) {
                return;
            }
            if (params.contains("\"nlu_result\"")) {
                // 一句话的语义解析结果
                if (length > 0 && data.length > 0) {
                    logTxt += ", 语义解析结果：" + new String(data, offset, length);
                }
            } else if (params.contains("\"partial_result\"")) {
                // 一句话的临时识别结果
                logTxt += ", 临时识别结果：" + params;
            }  else if (params.contains("\"final_result\""))  {
                // 一句话的最终识别结果
                logTxt += ", 最终识别结果：" + params;
                asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0);
                System.out.println("分支选择后"+State);
                switch (State){
                    case 0://判断类型
                        if (params.contains("记账")){
                            State=1;
                            runTime=0;
                            operationType=1;
                        }
                        else if (params.contains("种草")) {
                            State=1;
                            runTime=0;
                            operationType=2;
                        }
                        break;
                    case 1://判断物品分类
                        if (params.contains("交通")) {
                            State=2;
                            runTime=0;
                            inputData.setTypename("交通");
                            inputData.setImagenum(R.drawable.dashicons_plane);
                        }
                        else if (params.contains("文具")) {
                            State=2;
                            runTime=0;
                            inputData.setTypename("文具");
                            inputData.setImagenum(R.drawable.dashicons_pets);
                        }
                        break;
                    case 2://判断物品价格
                        if (params.contains("元")) {
                            State=3;
                            runTime=0;
                            try {
                                JSONObject result = new JSONObject(params);
                                String str=result.getString("best_result").trim();
                                String str2="";
                                if(str != null && !"".equals(str)) {
                                    for (int i = 0; i < str.length(); i++) {
                                        if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                                            str2 += str.charAt(i);
                                        }
                                    }
                                }
                                System.out.println("金额"+str2);
                                inputData.setItemmoney(Integer.parseInt(str2));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 3://判断备注
                        State=-1;
                        runTime=0;
                        try {
                            JSONObject result = new JSONObject(params);
                            String str=result.getString("best_result").trim();
                            inputData.setItemname(str);
                            int year= CalenderForOne.getYear();
                            int month=CalenderForOne.getMonth();
                            int day=CalenderForOne.getDay();
                            int week=CalenderForOne.getWeekOfMonth();
                            System.out.println("当前的周数"+week);
                            inputData.setDate(year,month,day,week);
                            inputData.setPoints(1,2,1,2,1);
                            if(operationType==1)
                            {
                                DBManager.insertItemToPositivetb(inputData);
                            }
                            else
                            {
                                DBManager.insertItemToGreedtb(inputData);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }  else {
                // 一般这里不会运行
                logTxt += " ;params :" + params;
                if (data != null) {
                    logTxt += " ;data length=" + data.length;
                }
            }
        } else {
            // 识别开始，结束，音量，音频数据回调
            if (params != null && !params.isEmpty()){
                logTxt += " ;params :" + params;
            }
            if (data != null) {
                logTxt += " ;data length=" + data.length;
            }
        }

        System.out.println("识别输出"+logTxt);
    }
}