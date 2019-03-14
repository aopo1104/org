package com.example.asus.organization2.Activity.LoginRegister;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.R;
import com.example.asus.organization2.StringData.String_NetURL;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ASUS on 2019/2/3.
 */

public class Activity_RegisterDetail extends Activity_Base{


    private Button btn_register;//注册按钮
    //用户名，密码，再次输入的密码的控件
    private EditText et_user_name,et_psw,et_psw_again,et_school,et_academy,et_class,et_studentId;
    //用户名，密码，再次输入的密码的控件的获取值
    private String userName,psw,pswAgain,userSex,userPhoneNumber,school,academy,className,studentId;
    //用户的性别
    private RadioGroup sex;
    //标题布局
    private RelativeLayout rl_title_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置页面布局 ,注册界面
        setContentView(R.layout.activity_register_detail);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
        setTitle("注册");
        //从activity_register.xml 页面中获取对应的UI控件
        btn_register=findViewById(R.id.Btn_RegisterDetail);
        et_user_name=findViewById(R.id.Et_RegisterDetail_userName);
        et_psw=findViewById(R.id.Et_RegisterDetail_password);
        et_psw_again=findViewById(R.id.Et_RegisterDetail_passwordAgain);
        et_school=findViewById(R.id.Et_RegisterDetail_school);
        et_academy=findViewById(R.id.Et_RegisterDetail_academy);
        et_class=findViewById(R.id.Et_RegisterDetail_class);
        et_studentId = findViewById(R.id.Et_RegisterDetail_studentId);
        sex=findViewById(R.id.Rb_RegisterDetail_sex);
        userSex="男";
        //进行性别选择
        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id=group.getCheckedRadioButtonId();
                RadioButton choise=findViewById(id);
                userSex=choise.getText().toString();
                Toast.makeText(mContext, "你选择的性别为：" + userSex, Toast.LENGTH_SHORT).show();
            }
        });

        //注册按钮
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入在相应控件中的字符串
                getEditString();
                //判断输入框内容
                int judge = JudgeMessageFormat();
                if(judge == 0) return;
                else{
                    Intent intent=getIntent();
                    userPhoneNumber=intent.getStringExtra("phoneNumber");
                    saveRegisterInfoToServe(userName,userSex,psw,school,academy,className,studentId,userPhoneNumber);
                }
            }
        });
    }

    /**
     * 获取控件中的字符串
     */
    private void getEditString(){
        userName=et_user_name.getText().toString().trim();
        psw=et_psw.getText().toString().trim();
        pswAgain=et_psw_again.getText().toString().trim();
        school= et_school.getText().toString().trim();
        academy= et_academy.getText().toString().trim();
        className = et_class.getText().toString().trim();
        studentId = et_studentId.getText().toString().trim();
    }

    //检验输入的信息正确性，全正确返回1，否则0
    private int JudgeMessageFormat(){
        if(TextUtils.isEmpty(userName)){
            showToast(mContext,"请输入真实姓名");
            return 0;
        }else if(TextUtils.isEmpty(psw)){
            showToast(mContext,"请输入密码");
            return 0;
        }else if(TextUtils.isEmpty(pswAgain)){
            showToast(mContext,"请再次输入密码");
            return 0;
        }else if(!psw.equals(pswAgain)){
            showToast(mContext,"输入两次的密码不一样");
            return 0;
        }else if(school.isEmpty()){
            showToast(mContext,"请输入学校名");
            return 0;
        }else if(academy.isEmpty()){
            showToast(mContext,"请输入学院名");
            return 0;
        }else if(className.isEmpty()){
            showToast(mContext,"请输入班级名");
            return 0;
        } else if(studentId.isEmpty()){
            showToast(mContext,"请输入学号");
            return 0;
        }
        return 1;
    }

    //上传到服务器数据这里打包
    private void saveRegisterInfoToServe(String string_Username, String string_Usersex, String string_Password, String string_school,String string_academy,String string_class, String string_studentId,String string_phoneNumber) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.add("name", string_Username);
        params.add("sex", string_Usersex);
        params.add("phoneNumber", string_phoneNumber);
        params.add("password", string_Password);
        params.add("school", string_school);
        params.add("academy", string_academy);
        params.add("class", string_class);
        params.add("studentId", string_studentId);
        Log.e("111",params.toString());
        /*
          注册接口
          status = 1 成功
          status = 2 ，errorCode = 1  userPhoneNumber 重复
          status = 2 ，errorCode = 2  添加到数据库失败
         */
        client.post(String_NetURL.URL_Register, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                Log.e("222", response);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    int status = object.getInt("status");
                    Log.e("stauts",status+"");
                    if(status == 2) //注册失败
                    {
                        int errorCode = object.getInt("errorCode");
                        switch (errorCode){
                            case 1:
                                showToast(mContext,"该手机号已被注册");
                                break;
                            case 2:
                                showToast(mContext,"注册失败");
                                break;
                        }
                        return;
                    }
                    else    //注册成功
                    {
                        showToast(mContext,"注册成功！");
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                showToast(mContext,"请检查网络连接");
                Log.e("111","fail");
            }
        });
    }

    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }




}
