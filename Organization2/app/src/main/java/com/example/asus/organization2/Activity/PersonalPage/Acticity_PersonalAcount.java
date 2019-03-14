package com.example.asus.organization2.Activity.PersonalPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Message.Message_Local;


public class Acticity_PersonalAcount extends Activity_Base implements View.OnClickListener {
    RelativeLayout PswChange;
    TextView Tv_personalAcount_nickName,Tv_personalAcount_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_acount);
        init();
    }

    private void init() {
        setTitle("帐号与安全");
        PswChange = findViewById(R.id.layout_personalAcount_changePsw);
        Tv_personalAcount_email=findViewById(R.id.Tv_personalAcount_email);
        Tv_personalAcount_nickName=findViewById(R.id.Tv_personalAcount_nickName);
        Tv_personalAcount_email.setText(Message_Local.email);
        Tv_personalAcount_nickName.setText(Message_Local.name);
        PswChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_personalAcount_changePsw:
                pswChange();
                break;

        }
    }
    public void pswChange(){
        Intent pswintent=new Intent(Acticity_PersonalAcount.this,Activity_PersonalAcount_changePsw.class);
        startActivity(pswintent);
    }

}
