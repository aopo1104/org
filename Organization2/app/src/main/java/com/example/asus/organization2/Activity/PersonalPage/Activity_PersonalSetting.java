package com.example.asus.organization2.Activity.PersonalPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Activity.LoginRegister.Activity_Login;
import com.example.asus.organization2.Message.Message_Local;
import com.example.asus.organization2.Message.Message_SharedPreferences;


public class Activity_PersonalSetting extends Activity_Base implements View.OnClickListener {

    private Button Btn_personalSetting_quit;
    private TextView Tv_personalSetting_idCardVerify;
    private RelativeLayout Layout_PersonalAcount,Layout_PersonalPhone,Layout_PersonalRealname;
    private TextView Tv_personalSetting_phoneNumber;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_setting);

        init();
    }

    private void init(){
        setTitle("设置");

        Btn_personalSetting_quit = findViewById(R.id.Btn_personalSetting_quit);
//        Btn_personalSetting_idCardVerify = findViewById(R.id.Btn_personalSetting_idCardVerify);
        Layout_PersonalAcount=findViewById(R.id.layout_PersonalAcount);
        Layout_PersonalPhone=findViewById(R.id.layout_PersonalPhoneNumber);
        Layout_PersonalRealname=findViewById(R.id.layout_PersonalRealname);
        Tv_personalSetting_phoneNumber=findViewById(R.id.Tv_personalSetting_phoneNumber);
        Layout_PersonalRealname.setOnClickListener(this);
        Btn_personalSetting_quit.setOnClickListener(this);
        Layout_PersonalAcount.setOnClickListener(this);
        Layout_PersonalPhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Btn_personalSetting_quit:
                Quit();
                break;
            case R.id.layout_PersonalRealname:
                idCardVerify();
                break;
            case R.id.layout_PersonalAcount:
                acountChange();
                break;
            case R.id.layout_PersonalPhoneNumber:
                phoneChange();
                break;
        }
    }

    private void idCardVerify(){
        if(Message_Local.isReal == 1){  //已经实名认证过

        }
        else {      //未实名认证
            intent = new Intent(mContext, Activity_hasNotVerified.class);
            startActivity(intent);
        }
    }

    private void Quit(){
        Message_Local.clearLocalMessage();
        Message_SharedPreferences.clearMessage(mContext);

        intent = new Intent(mContext, Activity_Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);  //把栈里的activity都清空
        startActivity(intent);
    }
    private void phoneChange(){
        Intent phoneintent=new Intent(Activity_PersonalSetting.this,Activity_Personal_PhoneNumber.class);
        startActivity(phoneintent);
    }

    private void acountChange(){
        Intent acountintent=new Intent(Activity_PersonalSetting.this,Acticity_PersonalAcount.class);
        startActivity(acountintent);
    }

/*    private void personalManage(){
        Intent manageintent=new Intent(Activity_PersonalSetting.this,Activity_PersonalManage.class);
        startActivity(manageintent);
    }*/
    private void setPhoneNumber(){
        String phoneNumber=Message_Local.phoneNumber.trim();
        Tv_personalSetting_phoneNumber.setText(phoneNumber);
    }
}
