package com.example.asus.organization2.Activity.PersonalPage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Message.Message_Local;

import cn.smssdk.SMSSDK;


public class Activity_Personal_smsVerification extends Activity_Base implements View.OnClickListener{

    private TextView Tv_personal_smsPhone;
    private EditText Et_personal_smsYanzhengma;
    private Button Btn_personal_Message,Btn_personal_smsSumbit;

//    private EditText ET_Register_PhoneNumber, ET_Register_Message;
//    private Button Btn_Register_Message, Btn_Register_Go;
    cn.smssdk.EventHandler eventHandler;
    int i = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_smsverification);

        initView();
        initEvent();
        initSDK();
    }

    /**
     * 初始化短信SDK
     */
    private void initSDK() {
        eventHandler = new cn.smssdk.EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);

    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                Btn_personal_Message.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                Btn_personal_Message.setText("获取验证码");
                Btn_personal_Message.setClickable(true);
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                Log.e("result", result + "");

                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        // TODO 处理成功得到验证码的结果
                        // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                        Toast.makeText(getApplicationContext(), "成功发送验证码",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // TODO 处理错误的结果
                        ((Throwable) data).printStackTrace();
                        Toast.makeText(getApplicationContext(), "验证码发送失败，请检查网络连接",
                                Toast.LENGTH_SHORT).show();
                    }
                } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {

                    if (result == SMSSDK.RESULT_COMPLETE) {
                        // TODO 处理验证码验证通过的结果
                        YanzhengSuccess();
                    } else {
                        // TODO 处理错误的结果
                        Toast.makeText(getApplicationContext(), "验证码输入错误",
                                Toast.LENGTH_SHORT).show();
                        ((Throwable) data).printStackTrace();
                    }
                }

            }
        }
    };

    @Override
    public void onClick(View v) {
        String phoneNumber = Tv_personal_smsPhone.getText().toString();
        switch (v.getId()) {
            case R.id.Btn_personal_Message:
                // 1. 通过规则判断手机号
                if (phoneNumber.length() != 11) {
                    Toast.makeText(this, "请输入11位有效手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 2. 通过sdk发送短信验证
                SMSSDK.getVerificationCode("86", phoneNumber);

                // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                i = 30;
                Btn_personal_Message.setClickable(false);
                Btn_personal_Message.setText("重新发送(" + i-- + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();
                break;

            // 4. 打开广播来接受读取短信
            case R.id.Btn_Register_Go:
                String message = Et_personal_smsYanzhengma.getText().toString();
                SMSSDK.submitVerificationCode("86", phoneNumber, message);
                //createProgressBar();
                // 验证通过之后，将smssdk注册代码注销
                //SMSSDK.unregisterAllEventHandler();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }

    private void initView() {
        setTitle("忘记密码");
        Et_personal_smsYanzhengma=findViewById(R.id.Et_personal_smsYanzhengma);
        Tv_personal_smsPhone=findViewById(R.id.Tv_personal_smsPhone);
        Btn_personal_Message=findViewById(R.id.Btn_personal_Message);
        Btn_personal_smsSumbit=findViewById(R.id.Btn_personal_smsSumbit);

//        ET_Register_PhoneNumber = (EditText) findViewById(R.id.Et_Register_PhoneNumber);
//        ET_Register_Message = (EditText) findViewById(R.id.Et_Register_Message);
//        Btn_Register_Message = (Button) findViewById(R.id.Btn_Register_Message);
//        Btn_Register_Go = (Button) findViewById(R.id.Btn_Register_Go);
    }

    private void initEvent() {
        Tv_personal_smsPhone.setText(Message_Local.phoneNumber);
        Btn_personal_smsSumbit.setOnClickListener(this);
        Btn_personal_Message.setOnClickListener(this);

//        Btn_Register_Message.setOnClickListener(this);
//        Btn_Register_Go.setOnClickListener(this);
    }

    //验证成功后的函数，需要把手机号传到下一个页面中，与下一个页面设置的密码一起保存。
    public void YanzhengSuccess() {
//        String phoneNumber = ET_Register_PhoneNumber.getText().toString();
//        Intent intent = new Intent(mContext, Activity_RegisterDetail.class);
//        intent.putExtra("phoneNumber", phoneNumber);
//        finish();
//        startActivity(intent);
        showToast(mContext, "验证码正确");

    }
}