package com.example.asus.organization2.Activity.PersonalPage;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Activity.WorkPage.AboutTask.Activity_taskInformation;
import com.example.asus.organization2.Adapter.Adapter_myTaskStatusList;
import com.example.asus.organization2.Adapter.Adapter_taskList;
import com.example.asus.organization2.Bean.Info_myTaskStatusMessage;
import com.example.asus.organization2.Bean.Info_taskMessage;
import com.example.asus.organization2.Message.Message_Local;
import com.example.asus.organization2.Override.ListView_Load;
import com.example.asus.organization2.R;
import com.example.asus.organization2.StringData.String_NetURL;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Activity_myTaskStatus extends Activity_Base {

    ListView Lv_myTaskStatus;
    Info_taskMessage message;
    ArrayList<Info_myTaskStatusMessage> memberArr = new ArrayList<>();
    Adapter_myTaskStatusList adapter_myTaskStatusList;

    final Handler myHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == SUCCESS) {

                if (Lv_myTaskStatus.getAdapter() == null)      //第一次加载的时候设置adapter
                {
                    adapter_myTaskStatusList = new Adapter_myTaskStatusList(memberArr, mContext);
                    Lv_myTaskStatus.setAdapter(adapter_myTaskStatusList);
                } else {
                    adapter_myTaskStatusList.setNewsList(memberArr);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task_status);

        init();
    }

    private void init(){
        setTitle("我的发布");
        message = (Info_taskMessage) getIntent().getSerializableExtra("message");

        Lv_myTaskStatus = findViewById(R.id.Lv_myTaskStatus);
        Lv_myTaskStatus.setOnItemClickListener(new Activity_myTaskStatus.MyOnItemClickListener());
        getMessage();
    }

    private void getMessage(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("task_id",message.getId() + "");

        /**
         * 查找任务的接收人的情况
         * 输入任务id(task_id)
         * 返回用户id(id), 用户姓名(name),用户是否阅读)(isread),isreport,report
         */
        client.post(String_NetURL.URL_getTaskMember, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                Log.e("222", response);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    JSONArray memberMessage = object.getJSONArray("message");
                    if (memberMessage.length() != 0) {//如果这人存在任务
                        for (int messageNumber = 0; messageNumber < memberMessage.length(); messageNumber++) {
                            JSONObject memberSingleMessage = (JSONObject) memberMessage.get(messageNumber);
                            int id = memberSingleMessage.getInt("id");
                            String name = memberSingleMessage.getString("name");
                            int isread = memberSingleMessage.getInt("isread");
                            int isreport = memberSingleMessage.getInt("isreport");
                            String report = memberSingleMessage.getString("report");
                            memberArr.add(new Info_myTaskStatusMessage(id,name,isread,isreport,report));
                        }
                    }
                    Message msg = Message.obtain();
                    msg.what = SUCCESS;
                    Bundle bundle = new Bundle();
                    msg.setData(bundle);
                    myHandler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(mContext, "请检查网络连接", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}
