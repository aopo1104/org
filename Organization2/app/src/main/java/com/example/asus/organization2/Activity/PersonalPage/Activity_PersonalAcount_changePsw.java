package com.example.asus.organization2.Activity.PersonalPage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Message.Message_Local;
import com.example.asus.organization2.StringData.String_NetURL;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class Activity_PersonalAcount_changePsw extends Activity_Base implements View.OnClickListener{

    private EditText Et_changePsw_oldPsw,Et_changePsw_newPsw,Et_changePsw_renewPsw;
    private TextView Tv_changePsw_quxiao,Tv_personal_changepsw_forgetpsw,Tv_changePsw_phoneNumber;
    private Button Bt_changePsw_finish;
    private MyDialog myDialog;
    private Button button;

    cn.smssdk.EventHandler eventHandler;
    int i = 30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_acount_changepsw);
        init();
        phoneNumber();
    }

    private void init(){
        setTitle("修改密码");
        Button rightButton = getHeadRightButton();
        rightButton.setText("完成");
        Tv_changePsw_phoneNumber=findViewById(R.id.Tv_changePsw_phoneNumber);
        Et_changePsw_oldPsw=findViewById(R.id.Et_changePsw_oldPsw);
        Et_changePsw_newPsw=findViewById(R.id.Et_changePsw_newPsw);
        Et_changePsw_renewPsw=findViewById(R.id.Et_changePsw_renewPsw);
        Tv_personal_changepsw_forgetpsw=findViewById(R.id.Tv_personal_changepsw_forgetpsw);

        Tv_personal_changepsw_forgetpsw.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Tv_personal_changepsw_forgetpsw:
                myDialog=new MyDialog(Activity_PersonalAcount_changePsw.this, R.style.MyDialog);
                myDialog.setTitle("   ");
                myDialog.setMessage("你的账号当前已经绑定手机号，可以通过短信验证码重置微信密码。即将发送验证码到"+ Message_Local.phoneNumber);
                myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
                    @Override
                    public void onYesOnclick() {
                        back(Activity_PersonalAcount_changePsw.this);
//                        Toast.makeText(getApplicationContext(),"短信已发送",Toast.LENGTH_LONG).show();
                        myDialog.dismiss();
                    }
                });
                myDialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        Toast.makeText(getApplicationContext(),"明智的选择",Toast.LENGTH_LONG).show();
                        myDialog.dismiss();
                    }
                });
                myDialog.show();
                break;

        }
    }

    private void change(){
        String oldpsw=Et_changePsw_oldPsw.getText().toString();
        String newpsw=Et_changePsw_newPsw.getText().toString();
        String repsw=Et_changePsw_renewPsw.getText().toString();
        if(TextUtils.isEmpty(oldpsw)){
            Toast.makeText(this,"请输入你的旧密码",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(newpsw)){
            Toast.makeText(this,"请输入你的新密码",Toast.LENGTH_SHORT).show();
        } else if(!newpsw.equals(repsw)){
            Toast.makeText(this,"输入两次的密码不一样",Toast.LENGTH_SHORT).show();
        }else if(newpsw.equals(oldpsw)){
            Toast.makeText(this,"新密码不能和原密码相同",Toast.LENGTH_SHORT).show();
        }else {
            String id=String.valueOf(Message_Local.id);
            gochange(id,oldpsw,newpsw);
//            gochange(newpsw);

        }
//        Intent changeintent=new Intent(Activity_PersonalAcount_changePsw.this,Acticity_PersonalAcount.class);
//        startActivity(changeintent);

    }

    private void gochange(String id,String oldpsw,String newpsw){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.add("Id",id);
        params.add("oldPsw",oldpsw);
        params.add("newPsw",newpsw);
        client.post(String_NetURL.URL_changePsw, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response=new String(bytes);
                Log.e("666",response);
                JSONObject object=null;
                try{
                    object = new JSONObject(response);
                    int status = object.getInt("status");
                    if(status==2){
                        int errorCode = object.getInt("errorCode");
                        switch (errorCode){
                            case 1:
                                Toast.makeText(Activity_PersonalAcount_changePsw.this,"原密码错误",Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(Activity_PersonalAcount_changePsw.this,"原密码与新密码相同",Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return;
                    }
                    else{
                        Toast.makeText(Activity_PersonalAcount_changePsw.this,"密码修改完成",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(Activity_PersonalAcount_changePsw.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void phoneNumber() {
        Tv_changePsw_phoneNumber.setText(Message_Local.phoneNumber);
    }

    private void back(Context context){
        Intent smsintent = new Intent();
        smsintent.setClass(context, Activity_Personal_smsVerification.class);
        context.startActivity(smsintent);
        ((Activity) context).finish();
    }

    @Override
    public void onHeadRightButtonClick(View view){
        change();
    }
}
