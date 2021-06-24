package com.app.undoing;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.AndroidRuntimeException;
import android.util.Log;

import com.app.undoing.Database.AccountBean;
import com.app.undoing.Database.CalenderForOne;
import com.app.undoing.Database.DBManager;
import com.baidu.aip.asrwakeup3.core.mini.AutoCheck;
import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;


public class WakeUp {
    public static final String TAG = WakeUp.class.getSimpleName();
    private EventManager wakeup;
    private Context context;
    MediaPlayer player;

    //语音识别变量
    private EventManager asr;
    private int State;
    private AccountBean inputData = new AccountBean();
    private int operationType; //1表示记账，2表示种草
    private int runTime;//循环次数

    /**
     * 唤醒构造方法
     *
     * @param context 一个上下文对象
     */
    public WakeUp(Context context) {
        this.context = context;
        //create方法示是一个静态方法，还有一个重载方法EventManagerFactory.create(context, name, version)
        //由于百度文档没有给出每个参数具体含义，我们只能按照官网给的demo写了
        wakeup = EventManagerFactory.create(context, "wp");
        //注册监听事件
        wakeup.registerListener(new MyEventListener());

        // 初始化EventManager对象
        asr = EventManagerFactory.create(context, "asr");
        // 基于sdk集成1.3 注册自己的输出事件类
        asr.registerListener(new VoiceEventListener()); //  EventListener 中 onEvent方法

        player=new MediaPlayer();

        System.out.println("唤醒构造");
    }

    /**
     * 开启唤醒功能
     */
    public void start() {
        HashMap<String, String> params = new HashMap<String, String>();
        // 设置唤醒资源, 唤醒资源请到 http://yuyin.baidu.com/wake#m4 来评估和导出
        params.put("kws-file", "assets:///WakeUp.bin");
        String json = null; // 这里可以替换成你需要测试的json
        json = new JSONObject(params).toString();
        wakeup.send(SpeechConstant.WAKEUP_START, json, null, 0, 0);
        Log.d(TAG, "----->唤醒已经开始工作了");
        System.out.println("唤醒开始");
    }

    /**
     * 关闭唤醒功能
     */
    public void stop() {
        // 具体参数的百度没有具体说明，大体需要以下参数
        // send(String arg1, byte[] arg2, int arg3, int arg4)
        wakeup.send("wp.stop", null, null, 0, 0);
        Log.d(TAG, "----->唤醒已经停止");
    }
    private void playMedia(String fileName,boolean control) {
        AssetManager assetManager;
//        MediaPlayer player = null;
//        player = new MediaPlayer();
        assetManager = context.getResources().getAssets();
        try {
            AssetFileDescriptor fileDescriptor = assetManager.openFd(fileName);
            player.reset();
            player.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            player.prepare();
            player.start();
            if (control) {
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        RecognizeStart();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void playFailMedia(String )

    private void RecognizeStart() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();

        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);

        // 请先使用如‘在线识别’界面测试和生成识别参数。 params同ActivityRecog类中myRecognizer.start(params);
        // 复制此段可以自动检测错误
        (new AutoCheck(context, new Handler() {
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

    private  class VoiceEventListener implements EventListener{
        @Override
        public void onEvent(String name, String params, byte[] data, int offset, int length) {
            String logTxt = "name: " + name;

            if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_FINISH)) {
                System.out.println("分支选择前"+State);
                switch (State) {
                    case 0:
                        if (runTime<3) {
                            playMedia("welcome.mp3",true);
                            runTime++;
                        } else {
                            State=-1;
                            playMedia("recordFail.mp3",false);
                            asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0);
                        }
                        break;
                    case 1://分类
                        if (runTime<3) {
                            playMedia("objectType.mp3",true);
                            runTime++;
                        } else {
                            State=-1;
                            playMedia("recordFail.mp3",false);
                            asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0);
                        }
                        break;
                    case 2://金额
                        if (runTime<3) {
                            playMedia("Money.mp3",true);
                            runTime++;
                        } else {
                            State=-1;
                            playMedia("recordFail.mp3",false);
                            asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0);
                        }
                        break;
                    case 3://备注
                        if(runTime<3) {
                            playMedia("itemName.mp3",true);
                            runTime++;
                        } else {
                            State=-1;
                            playMedia("recordFail.mp3",false);
                            asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0);
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
                            } else {
                                State=2;
                                runTime=0;
                                inputData.setTypename("其他");
                                inputData.setImagenum(R.drawable.dashicons_fruit);
                                playMedia("TypeFail.mp3",false);
                                while(true)
                                {
                                    if(!player.isPlaying())
                                        break;
                                }
                            }
                            break;
                        case 2://判断物品价格
                            if (params.contains("元")) {
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
                                    if(!str2.isEmpty())
                                    {
                                        State=3;
                                        runTime=0;
                                        inputData.setItemmoney(Integer.parseInt(str2));
                                    } else {
                                        playMedia("MoneyFail.mp3",false);
                                        while(true)
                                        {
                                            if(!player.isPlaying())
                                                break;
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                playMedia("MoneyFail.mp3",false);
                                while(true)
                                {
                                    if(!player.isPlaying())
                                        break;
                                }
                            }
                            break;
                        case 3://判断备注
                            System.out.println("我在case3");
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
                                playMedia("recordSuccess.mp3",false);
                                asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0);
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
    private class MyEventListener implements EventListener {
        @Override
        public void onEvent(String name, String params, byte[] data, int offset, int length) {
            String logTxt = "name: " + name;
            if(name.equals(SpeechConstant.CALLBACK_EVENT_WAKEUP_SUCCESS))
            {
               System.out.println("唤醒成功");
                State=0;
                runTime=0;
                playMedia("welcome.mp3",true);
            }
//            if (params != null && !params.isEmpty()) {
//                logTxt += " ;params :" + params;
//                try {
//                    JSONObject result = new JSONObject(params);
//                    String str=result.getString("word").trim();
//                    if(str=="打开记账")
//                    {
//                        System.out.println("唤醒成功");
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            } else if (data != null) {
//                logTxt += " ;data length=" + data.length;
//            }
            System.out.println("接收到"+logTxt);
        }

    }
}
