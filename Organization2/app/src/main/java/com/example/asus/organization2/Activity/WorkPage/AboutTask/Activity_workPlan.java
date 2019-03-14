package com.example.asus.organization2.Activity.WorkPage.AboutTask;

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
import com.example.asus.organization2.Activity.AddressListPage.Activity_addFriends;
import com.example.asus.organization2.Activity.PersonalPage.Activity_FriendsList;
import com.example.asus.organization2.Activity.PersonalPage.Activity_PersonalInformation;
import com.example.asus.organization2.Adapter.Adapter_FriendsList;
import com.example.asus.organization2.Adapter.Adapter_taskList;
import com.example.asus.organization2.Bean.Info_PeronalMessage;
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

public class Activity_workPlan extends Activity_Base {

    private Intent intent;
    private ArrayList<Info_taskMessage> taskArr = new ArrayList<>();
    private ListView_Load Lv_myPublishTask;
    private Adapter_taskList taskAdapter;

    final Handler myHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == SUCCESS) {

                if(Lv_myPublishTask.getAdapter()==null)      //第一次加载的时候设置adapter
                {
                    taskAdapter = new Adapter_taskList(taskArr,mContext);
                    Lv_myPublishTask.setAdapter(taskAdapter);
                }else{
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
        setContentView(R.layout.activity_work_plan);

        init();
    }

    private void init(){
        setTitle("工作安排");

        Button rightButton = getHeadRightButton();
        rightButton.setBackground(getResources().getDrawable(R.drawable.add));   //设置头部右边按钮

        Lv_myPublishTask = findViewById(R.id.Lv_myPublishTask);
        Lv_myPublishTask.setOnItemClickListener(new Activity_workPlan.MyOnItemClickListener());
        getMessage();
    }

    @Override
    public void onHeadRightButtonClick(View v) {
        intent = new Intent(mContext,Activity_primaryTaskCreate.class);
        startActivity(intent);
    }

    private void getMessage(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("id",Message_Local.id+"");

        /**
         * 查找任务接口，包括社团任务(task中的数据)和个人任务(primaryTask中的数据)
         * 传入用户id("id")
         * 输出 type(类型，1为社团任务 2为个人任务),orgName(若type==1则没有),title,content,startTime,endTime的多个json字符串
         */
        client.post(String_NetURL.URL_getTaskMessage, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                Log.e("222", response);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    JSONArray taskMessage = object.getJSONArray("message");
                    if(taskMessage.length()!=0) {//如果这人存在任务
                        for(int messageNumber = 0;messageNumber < taskMessage.length();messageNumber++){
                            JSONObject taskSingleMessage = (JSONObject)taskMessage.get(messageNumber);
                            String orgName,report;
                            int isread,isreport;
                            int type = taskSingleMessage.getInt("type");    //
                            if(type == 1){  //为社团任务
                                orgName = taskSingleMessage.getString("orgName");
                                isread = taskSingleMessage.getInt("isread");
                                isreport = taskSingleMessage.getInt("isreport");
                                report = taskSingleMessage.getString("report");
                            }else{
                                orgName = "";
                                isread = 0;
                                isreport = 0;
                                report = "";
                            }
                            int id = taskSingleMessage.getInt("id");
                            String title = taskSingleMessage.getString("title");
                            String content = taskSingleMessage.getString("content");
                            String startTime = taskSingleMessage.getString("startTime");
                            String endTime = taskSingleMessage.getString("endTime");
                            taskArr.add(new Info_taskMessage(id,type,orgName,title,content,startTime,endTime,isread,isreport,report));
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
                Toast.makeText(mContext,"请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Info_taskMessage message = (Info_taskMessage) parent.getItemAtPosition(position);
            Intent intent=new Intent(mContext,Activity_taskInformation.class);
            intent.putExtra("message",message);
            startActivity(intent);
        }
    }

}
