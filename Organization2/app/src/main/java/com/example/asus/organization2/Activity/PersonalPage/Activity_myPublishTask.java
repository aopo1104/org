package com.example.asus.organization2.Activity.PersonalPage;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Activity.WorkPage.AboutTask.Activity_primaryTaskCreate;
import com.example.asus.organization2.Activity.WorkPage.AboutTask.Activity_taskInformation;
import com.example.asus.organization2.Activity.WorkPage.AboutTask.Activity_workPlan;
import com.example.asus.organization2.Adapter.Adapter_taskList;
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

public class Activity_myPublishTask extends Activity_Base {


    private Intent intent;
    private ArrayList<Info_taskMessage> taskArr = new ArrayList<>();
    private ListView_Load Lv_myPublishTask;
    private Adapter_taskList taskAdapter;

    final Handler myHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == SUCCESS) {

                if (Lv_myPublishTask.getAdapter() == null)      //第一次加载的时候设置adapter
                {
                    taskAdapter = new Adapter_taskList(taskArr, mContext);
                    Lv_myPublishTask.setAdapter(taskAdapter);
                } else {
                    taskAdapter.setNewsList(taskArr);
                }
                //加载完成
                Lv_myPublishTask.loadComplete();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_publish_task);

        init();
    }

    private void init() {
        setTitle("我的发布");

        Lv_myPublishTask = findViewById(R.id.Lv_myPublishTask);
        Lv_myPublishTask.setOnItemClickListener(new Activity_myPublishTask.MyOnItemClickListener());
        getMessage();
    }

    private void getMessage() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("userbase_id", Message_Local.id + "");

        /**
         * 通过发布者Id查找任务信息接口
         * 输入发布者Id（userbase_id）
         * 返回id,title,content,startTime,endTime
         */
        client.post(String_NetURL.URL_getTaskMessageByPublishPersonId, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                Log.e("222", response);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    JSONArray taskMessage = object.getJSONArray("message");
                    if (taskMessage.length() != 0) {//如果这人存在任务
                        for (int messageNumber = 0; messageNumber < taskMessage.length(); messageNumber++) {
                            JSONObject taskSingleMessage = (JSONObject) taskMessage.get(messageNumber);
                            String orgName, report;
                            int id = taskSingleMessage.getInt("id");
                            String title = taskSingleMessage.getString("title");
                            String content = taskSingleMessage.getString("content");
                            String startTime = taskSingleMessage.getString("startTime");
                            String endTime = taskSingleMessage.getString("endTime");
                            taskArr.add(new Info_taskMessage(id, 1, "", title, content, startTime, endTime, 1, 1, null));
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
            Info_taskMessage message = (Info_taskMessage) parent.getItemAtPosition(position);
            Intent intent = new Intent(mContext, Activity_myTaskStatus.class);
            intent.putExtra("message", message);
            startActivity(intent);
        }
    }
}
