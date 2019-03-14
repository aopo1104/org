package com.example.asus.organization2.Activity.WorkPage.AboutTask;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class Activity_primaryTaskCreate extends Activity_Base implements View.OnClickListener {

    Button Btn_primaryTaskCreate_startDate,Btn_primaryTaskCreate_endDate,Btn_primaryTaskCreate_create;
    TextView Tv_primaryTaskCreate_startDate,Tv_primaryTaskCreate_endDate;
    CheckBox checkbox_primaryTaskCreate_1,checkbox_primaryTaskCreate_2,checkbox_primaryTaskCreate_3,checkbox_primaryTaskCreate_4;
    EditText Et_primaryTaskCreate_title,Et_primaryTaskCreate_content;
    int startYear,startMonth,startDay,endYear,endMonth,endDay;
    int canSeePlace1,canSeePlace2,canSeePlace3,canSeePlace4;
    String title,content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_task_create);

        init();
    }

    private void init(){
        setTitle("新建个人任务");

        Btn_primaryTaskCreate_startDate = findViewById(R.id.Btn_primaryTaskCreate_startDate);
        Btn_primaryTaskCreate_endDate = findViewById(R.id.Btn_primaryTaskCreate_endDate);
        Btn_primaryTaskCreate_create = findViewById(R.id.Btn_primaryTaskCreate_create);
        Tv_primaryTaskCreate_startDate = findViewById(R.id.Tv_primaryTaskCreate_startDate);
        Tv_primaryTaskCreate_endDate = findViewById(R.id.Tv_primaryTaskCreate_endDate);
        Et_primaryTaskCreate_title = findViewById(R.id.Et_primaryTaskCreate_title);
        Et_primaryTaskCreate_content = findViewById(R.id.Et_primaryTaskCreate_content);
        Btn_primaryTaskCreate_startDate.setOnClickListener(this);
        Btn_primaryTaskCreate_endDate.setOnClickListener(this);
        Btn_primaryTaskCreate_create.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Btn_primaryTaskCreate_startDate:    //开始日期
                setDate(1);
                break;
            case R.id.Btn_primaryTaskCreate_endDate:  //结束日期
                setDate(2);
                break;
            case R.id.Btn_primaryTaskCreate_create:   //发布
                getMessage();
                goprimaryTaskCreate();
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
                    Tv_primaryTaskCreate_startDate.setText(sb);
                    startYear = datePicker.getYear();
                    startMonth = datePicker.getMonth()+1;
                    startDay = datePicker.getDayOfMonth();
                }else{
                    Tv_primaryTaskCreate_endDate.setText(sb);
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
        title = Et_primaryTaskCreate_title.getText().toString().trim();
        content = Et_primaryTaskCreate_content.getText().toString().trim();
    }

    public void goprimaryTaskCreate(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.add("personId",Message_Local.id+"");
        params.add("title",title);
        params.add("content",content);
        params.add("startYear",startYear+"");
        params.add("startMonth",startMonth+"");
        params.add("startDay",startDay+"");
        params.add("endYear",endYear+"");
        params.add("endMonth",endMonth+"");
        params.add("endDay",endDay+"");
        client.post(String_NetURL.URL_primaryTaskCreate, params, new AsyncHttpResponseHandler() {
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
}
