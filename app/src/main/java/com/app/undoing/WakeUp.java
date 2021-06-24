package com.app.undoing;

import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.util.AndroidRuntimeException;
import android.util.Log;
import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;


public class WakeUp {
    public static final String TAG = WakeUp.class.getSimpleName();
    private EventManager wakeup;
    private Context context;
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

    private class MyEventListener implements EventListener {
        @Override
        public void onEvent(String name, String params, byte[] data, int offset, int length) {
            String logTxt = "name: " + name;
            if(name.equals(SpeechConstant.CALLBACK_EVENT_WAKEUP_SUCCESS))
            {
               System.out.println("唤醒成功");

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
