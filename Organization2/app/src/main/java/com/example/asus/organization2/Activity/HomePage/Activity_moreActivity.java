package com.example.asus.organization2.Activity.HomePage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Adapter.Adapter_activityList;
import com.example.asus.organization2.Bean.Info_ActivityMessage;
import com.example.asus.organization2.Override.ListView_Load;
import com.example.asus.organization2.StringData.String_NetURL;
import com.example.asus.organization2.Utils.Util_Buffer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ASUS on 2019/2/14.
 */

public class Activity_moreActivity extends Activity_Base{

    private ListView_Load Lv_moreActivity;
    private int number =  2;    //因为首页已经读了10条，并且在init中会读取number = 1时 所以这里从2开始
    private View bufferView;    //加载页面
    private ArrayList<Info_ActivityMessage> activityMessages = new ArrayList<>();
    private Adapter_activityList activityAdapter;

    final Handler myHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == SUCCESS) {
                if(Lv_moreActivity.getAdapter()==null)      //第一次加载的时候设置adapter
                {
                    bufferView.setVisibility(View.GONE);
                    activityAdapter = new Adapter_activityList(activityMessages,mContext);
                    Lv_moreActivity.setAdapter(activityAdapter);
                }else{
                    activityAdapter.setNewsList(activityMessages);
                }
                //加载完成
                Lv_moreActivity.loadComplete();
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreactivity);

        init();
    }

    private void init(){
        bufferView = Util_Buffer.CreateBufferActivity(Activity_moreActivity.this);    //还在加载时，先显示缓冲页面

        Lv_moreActivity = findViewById(R.id.Lv_moreActivity);
        setTitle("更多");
        LoadMessage(1);
    }

    private void LoadMessage(int number){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("number",number+"");
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

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Info_ActivityMessage activityMessage = (Info_ActivityMessage) parent.getItemAtPosition(position);
            Intent intent = new Intent(mContext, Activity_activityDetail.class);
            intent.putExtra("message",activityMessage);
            startActivity(intent);
        }
    }

    //若到达最下面，则继续加载
    private void myLoadNewNews() {
        Lv_moreActivity.setInterface(new ListView_Load.ILoadListener() {
            @Override
            public void onLoad() {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        LoadMessage(number++);
                    }
                }.start();
            }
        });
    }
}
