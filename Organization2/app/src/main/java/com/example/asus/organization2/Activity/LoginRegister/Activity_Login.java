package com.example.asus.organization2.Activity.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.organization2.R;
import com.example.asus.organization2.Message.Message_Local;
import com.example.asus.organization2.StringData.String_NetURL;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ASUS on 2019/2/3.
 */

public class Activity_Login extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Activity_Login";
    Button Btn_Login_GoRegister,Btn_Login_Go,Btn_Login_ForgetPassword;
    EditText ET_Login_PhoneNumber,ET_Login_Password;
    String phoneNumber,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initEvent();
    }

    private void initView() {
        ET_Login_PhoneNumber = (EditText) findViewById(R.id.ET_Login_PhoneNumber);
        ET_Login_Password = (EditText) findViewById(R.id.ET_Login_Password);
        Btn_Login_GoRegister = (Button) findViewById(R.id.Btn_Login_GoRegister);
        Btn_Login_Go = (Button) findViewById(R.id.Btn_Login_Go);
        Btn_Login_ForgetPassword = (Button) findViewById(R.id.Btn_Login_ForgetPassword);
    }

    private void initEvent() {
        Btn_Login_GoRegister.setOnClickListener(this);
        Btn_Login_Go.setOnClickListener(this);
        Btn_Login_ForgetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.Btn_Login_GoRegister:
                Intent intent=new Intent(Activity_Login.this,Activity_Register.class);
                startActivity(intent);
                break;

            case R.id.Btn_Login_Go:
                phoneNumber=ET_Login_PhoneNumber.getText().toString();
                password=ET_Login_Password.getText().toString();
                goLogin(phoneNumber,password);
                break;

            case R.id.Btn_Login_ForgetPassword:
                break;
        }
    }

    private void goLogin(final String phoneNumber,String password){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.add("phoneNumber", phoneNumber);
        params.add("password", password);
            /*
         * 登录接口，
         * status=1:登录成功 返回status,id,name,phoneNumber,sex,email,school,academy,class,
         * studentid,birth,headpiture,isreal,
         * 名为organizationMessage的json（数组，每一项里有（organizationId(社团编号),organizationName(社团姓名),organizationPlace（该人所在的社团地位）））
         * status=2：登录失败，返回errorCode
         *      errorCode=1：帐号不存在 errorCode=2：密码错误
         */
        client.post(String_NetURL.URL_Login, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                Log.e(TAG,response);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    int status = object.getInt("status");
                    if(status == 2) //登录失败
                    {
                        int errorCode = object.getInt("errorCode");
                        switch (errorCode){
                            case 1:
                                Toast.makeText(Activity_Login.this,"帐号不存在",Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(Activity_Login.this,"密码错误",Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return;
                    }
                    else    //登录成功
                    {
                        int id = object.getInt("id");
                        Message_Local.saveId(1,id);
                        Toast.makeText(Activity_Login.this,"登录成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Activity_Login.this,Activity_Load2.class);
                        finish();
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(Activity_Login.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
