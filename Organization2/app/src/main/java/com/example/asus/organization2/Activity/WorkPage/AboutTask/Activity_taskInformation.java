package com.example.asus.organization2.Activity.WorkPage.AboutTask;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Bean.Info_taskMessage;
import com.example.asus.organization2.Message.Message_Local;
import com.example.asus.organization2.R;
import com.example.asus.organization2.StringData.String_NetURL;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Activity_taskInformation extends Activity_Base implements View.OnClickListener {

    TextView Tv_taskInformation_title,Tv_taskInformation_time,Tv_taskInformation_content;
    EditText Et_taskInformation_report;
    Button Btn_taskInformation_isRead,Btn_taskInformation_report;
    int type;   //社团/个人任务
    Info_taskMessage message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_information);

        init();
    }

    private void init(){
        setTitle("任务详情");

        message = (Info_taskMessage) getIntent().getSerializableExtra("message");

        Tv_taskInformation_title = findViewById(R.id.Tv_taskInformation_title);
        Tv_taskInformation_time = findViewById(R.id.Tv_taskInformation_time);
        Tv_taskInformation_content = findViewById(R.id.Tv_taskInformation_content);
        Et_taskInformation_report = findViewById(R.id.Et_taskInformation_report);
        Btn_taskInformation_isRead = findViewById(R.id.Btn_taskInformation_isRead);
        Btn_taskInformation_report = findViewById(R.id.Btn_taskInformation_report);

        Tv_taskInformation_title.setText(message.getTitle());
        Tv_taskInformation_time.setText("时间："+ message.getStartTime() +" -> " + message.getEndTime());
        Tv_taskInformation_content.setText(message.getContent());

        Btn_taskInformation_report.setOnClickListener(this);
        Btn_taskInformation_isRead.setOnClickListener(this);

        type = message.getType();

        if(type == 1){
            orgTaskFunc();
        }else{
            personTaskFunc();
        }
    }

    //社团任务
    private void orgTaskFunc(){
        if(message.getIsread() == 1){   //为已阅
            Btn_taskInformation_isRead.setEnabled(false);
            isRead();
            if(message.getIsreport() == 1){ //为已反馈
                isReport();
            }
        }else{

        }
    }

    //为个人任务，则不需要"已阅"按钮
    private void personTaskFunc(){
        Btn_taskInformation_isRead.setVisibility(View.GONE);
    }

    private void haveReadTask(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("userbase_id", Message_Local.id+"");
        params.add("task_id",message.getId()+"");

        /**
         * 阅读任务接口，将isread 从 0 改为 1
         * 传入用户id('userbase_id')和任务id('task_id)
         */
        client.post(String_NetURL.URL_taskRead, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                Log.e("222", response);
                JSONObject object = null;
                Btn_taskInformation_isRead.setEnabled(false);
                message.setIsread(1);
                showToast(mContext,"已阅读");
                isRead();
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(mContext,"请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //反馈提交
    private void haveReported(final String report){

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("userbase_id", Message_Local.id+"");
        params.add("task_id",message.getId()+"");
        params.add("report",report);
        client.post(String_NetURL.URL_taskReport, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                Log.e("222", response);
                JSONObject object = null;
                Btn_taskInformation_report.setEnabled(false);
                message.setReport(report);
                message.setIsreport(1);
                isReport();
                showToast(mContext,"已提交");
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(mContext,"请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void isRead(){
        Et_taskInformation_report.setVisibility(View.VISIBLE);
        Btn_taskInformation_report.setVisibility(View.VISIBLE);
    }

    public void isReport(){
        Et_taskInformation_report.setText(message.getReport());
        Et_taskInformation_report.setEnabled(false);
        Btn_taskInformation_report.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Btn_taskInformation_isRead:
                haveReadTask();
                break;
            case R.id.Btn_taskInformation_report:
                String report = Et_taskInformation_report.getText().toString().trim();
                if (report == null)
                    break;
                else{
                    haveReported(report);
                }
                break;
        }
    }
}
