package com.example.asus.organization2.Activity.WorkPage.AboutTask;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Message.Message_Local;
import com.example.asus.organization2.R;
import com.example.asus.organization2.StringData.String_NetURL;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by ASUS on 2019/3/3.
 */

public class Activity_taskPublish extends Activity_Base implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    Button Btn_taskpublish_startDate,Btn_taskpublish_endDate,Btn_taskPublish_create;
    TextView Tv_taskpublish_startDate,Tv_taskpublish_endDate;
    CheckBox checkbox_taskpublish_1,checkbox_taskpublish_2,checkbox_taskpublish_3,checkbox_taskpublish_4;
    EditText Et_taskPublish_title,Et_taskPublish_content;
    int startYear,startMonth,startDay,endYear,endMonth,endDay;
    int canSeePlace1,canSeePlace2,canSeePlace3,canSeePlace4;
    String title,content;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskpublish);

        init();
    }

    private void init(){
        setTitle("发布任务");

        Btn_taskpublish_startDate = findViewById(R.id.Btn_taskpublish_startDate);
        Btn_taskpublish_endDate = findViewById(R.id.Btn_taskpublish_endDate);
        Btn_taskPublish_create = findViewById(R.id.Btn_taskPublish_create);
        Tv_taskpublish_startDate = findViewById(R.id.Tv_taskpublish_startDate);
        Tv_taskpublish_endDate = findViewById(R.id.Tv_taskpublish_endDate);
        Et_taskPublish_title = findViewById(R.id.Et_taskPublish_title);
        Et_taskPublish_content = findViewById(R.id.Et_taskPublish_content);
        checkbox_taskpublish_1  = findViewById(R.id.checkbox_taskpublish_1);
        checkbox_taskpublish_2  = findViewById(R.id.checkbox_taskpublish_2);
        checkbox_taskpublish_3  = findViewById(R.id.checkbox_taskpublish_3);
        checkbox_taskpublish_4  = findViewById(R.id.checkbox_taskpublish_4);
        checkbox_taskpublish_1.setOnCheckedChangeListener(this);
        checkbox_taskpublish_2.setOnCheckedChangeListener(this);
        checkbox_taskpublish_3.setOnCheckedChangeListener(this);
        checkbox_taskpublish_4.setOnCheckedChangeListener(this);
        Btn_taskpublish_startDate.setOnClickListener(this);
        Btn_taskpublish_endDate.setOnClickListener(this);
        Btn_taskPublish_create.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Btn_taskpublish_startDate:    //开始日期
                setDate(1);
                break;
            case R.id.Btn_taskpublish_endDate:  //结束日期
                setDate(2);
                break;
            case R.id.Btn_taskPublish_create:   //发布
                getMessage();
                goTaskPublish();
                break;
        }
    }

    public void setDate(final int type){
        //type 为1 表示 开始日期，为2 表示 结束日期
        //点击"日期"按钮布局 设置日期
                //通过自定义控件AlertDialog实现
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View view = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_datepicker, null);
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
                //设置日期简略显示 否则详细显示 包括:星期\周
                datePicker.setCalendarViewShown(false);
                Calendar calendar = Calendar.getInstance();
                //初始化当前日期
                calendar.setTimeInMillis(System.currentTimeMillis());
                datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH), null);
                //设置date布局
                builder.setView(view);
                builder.setTitle("设置日期信息");
                builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //日期格式
                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        if(type == 1){
                            Tv_taskpublish_startDate.setText(sb);
                            startYear = datePicker.getYear();
                            startMonth = datePicker.getMonth()+1;
                            startDay = datePicker.getDayOfMonth();
                        }else{
                            Tv_taskpublish_endDate.setText(sb);
                            endYear = datePicker.getYear();
                            endMonth = datePicker.getMonth()+1;
                            endDay = datePicker.getDayOfMonth();
                        }

                        //赋值后面闹钟使用
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("取  消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }

    public void getMessage(){
        title = Et_taskPublish_title.getText().toString().trim();
        content = Et_taskPublish_content.getText().toString().trim();
    }

    public void goTaskPublish(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("organizationId", Message_Local.organizationId.get(Message_Local.selectOrg)+"");
        params.add("personId",Message_Local.id+"");
        params.add("title",title);
        params.add("content",content);
        params.add("startYear",startYear+"");
        params.add("startMonth",startMonth+"");
        params.add("startDay",startDay+"");
        params.add("endYear",endYear+"");
        params.add("endMonth",endMonth+"");
        params.add("endDay",endDay+"");
        params.add("canSeePlace1",canSeePlace1+"");
        params.add("canSeePlace2",canSeePlace2+"");
        params.add("canSeePlace3",canSeePlace3+"");
        params.add("canSeePlace4",canSeePlace4+"");
        client.post(String_NetURL.URL_taskPublish, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                Log.e("222", response);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    int status = object.getInt("status");
                    if(status == 2) //获取信息失败
                    {
                        Toast.makeText(mContext,"请检查网络连接",Toast.LENGTH_SHORT).show();
                     }
                    else    //获取成功
                    {
                        Toast.makeText(mContext,"发布成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }
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



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.checkbox_taskpublish_1:
                if(isChecked)
                    canSeePlace1 = 1;
                else
                    canSeePlace1 = 0;
                break;
            case R.id.checkbox_taskpublish_2:
                if(isChecked)
                    canSeePlace2 = 1;
                else
                    canSeePlace2 = 0;
                break;
            case R.id.checkbox_taskpublish_3:
                if(isChecked)
                    canSeePlace3 = 1;
                else
                    canSeePlace3 = 0;
                break;
            case R.id.checkbox_taskpublish_4:
                if(isChecked)
                    canSeePlace4 = 1;
                else
                    canSeePlace4 = 0;
                break;
        }
    }
}
