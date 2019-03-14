package com.example.asus.organization2.Activity.HomePage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Adapter.Adapter_activityList;
import com.example.asus.organization2.Adapter.Adapter_homePage_RollerPageView;
import com.example.asus.organization2.Bean.Info_ActivityMessage;
import com.example.asus.organization2.StringData.String_NetURL;
import com.example.asus.organization2.Utils.Util_Buffer;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ASUS on 2019/2/2.
 */

public class Activity_Home_Page extends Activity_Base implements View.OnClickListener {

    private RelativeLayout layout_moreActivity;
    private RollPagerView mRollViewPager;
    private ListView list_activity;
    private View bufferView;    //加载页面
    private ArrayList<Info_ActivityMessage> activityMessages = new ArrayList<>();
    private ArrayList<Info_ActivityMessage> activityMessagesforTop = new ArrayList<>(); //上面回马灯用的
    private ArrayList<Info_ActivityMessage> activityMessagesforDown = new ArrayList<>();

    final Handler myHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == SUCCESS) {

                bufferView.setVisibility(View.GONE);
                for(int temp = 0;temp<10;temp++){
                    if(temp<=3)
                        activityMessagesforTop.add(activityMessages.get(temp));
                    else
                        activityMessagesforDown.add(activityMessages.get(temp));
                }
                mRollViewPager.setAdapter(new Adapter_homePage_RollerPageView(mContext,activityMessagesforTop));
                Log.e("333",activityMessagesforDown.toString());
                list_activity.setAdapter(new Adapter_activityList(activityMessagesforDown,mContext));
                list_activity.setOnItemClickListener(new MyOnItemClickListener());
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        init();
    }

    public void init(){
        bufferView = Util_Buffer.CreateBufferActivity(Activity_Home_Page.this);    //还在加载时，先显示缓冲页面
        setTitle("首页");
        setHeadLeftButtonVisibility(View.GONE);
        layout_moreActivity = findViewById(R.id.layout_moreActivity);
        layout_moreActivity.setOnClickListener(this);
        list_activity = findViewById(R.id.Lv_homePage_activity);
        list_activity.setFocusable(false);
        LoadMessage();
        initRollPageView();
    }

    private void initRollPageView(){
        mRollViewPager = (RollPagerView) findViewById(R.id.Pv_homePage);
        //设置播放时间间隔
        mRollViewPager.setPlayDelay(5000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        //mRollViewPager.setAdapter(new Adapter_homePage_RollerPageView(getWindow().getDecorView()));

        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        mRollViewPager.setHintView(new ColorPointHintView(this, Color.YELLOW,Color.WHITE));
        //mRollViewPager.setHintView(new TextHintView(this));
        //mRollViewPager.setHintView(null);
    }


    private void LoadMessage(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("number","0");
        /*
         * 获取比赛的详细信息，每次获取10条，是哪10条由number来定
         * 传入 number（控制获取哪10条），若为0，则是数据库的最后十条
         * 返回 成功  status = 1 ,id,type,title,content,picture,createTime,organizationName,personName
         * 没有数据可读了 status = 2
         */
        client.post(String_NetURL.URL_getActivityMessage, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                Log.e("fra",response);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    int status = object.getInt("status");
                    if(status == 2) //获得失败
                    {
                        showToast(mContext,"暂时没有活动比赛信息");
                        return;
                    }
                    else    //获得成功！
                    {
                        JSONArray message = object.getJSONArray("message");
                        //Log.e("fra",message.toString());
                        for(int temp = 0;temp<message.length();temp++){
                            JSONObject messageJsonObject = (JSONObject)message.get(temp);
                            Log.e("object",messageJsonObject.toString());
                            int id = messageJsonObject.getInt("id");
                            int type = messageJsonObject.getInt("type");
                            String title = messageJsonObject.getString("title");
                            String content = messageJsonObject.getString("content");
                            String picture = messageJsonObject.getString("picture");
                            String createTime = messageJsonObject.getString("createTime");
                            String organizationName = messageJsonObject.getString("organizationName");
                            String personName = messageJsonObject.getString("personName");

                            Info_ActivityMessage newActivityMessage = new Info_ActivityMessage(id,organizationName,personName,type,title,content,picture,createTime);
                            activityMessages.add(newActivityMessage);
                        }
                        Message msg = Message.obtain();
                        msg.what = SUCCESS;
                        Bundle bundle = new Bundle();
                        msg.setData(bundle);
                        myHandler.sendMessage(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                showToast(mContext,"请检查网络连接");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_moreActivity:
                Intent intent = new Intent(mContext,Activity_moreActivity.class);
                startActivity(intent);
                break;
        }
    }


    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Info_ActivityMessage activityMessage = (Info_ActivityMessage) parent.getItemAtPosition(position);
            Intent intent = new Intent(mContext, Activity_activityDetail.class);
            intent.putExtra("message",activityMessage);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.e("111","onResume");
        LoadMessage();
        initRollPageView();
    }

   protected void onPause(){
        super.onPause();
        activityMessages.clear();
        activityMessagesforTop.clear();
        activityMessagesforDown.clear();
   }
}
