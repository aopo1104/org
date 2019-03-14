package com.example.asus.organization2.Activity.MessagePage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;


/**
 * Created by ASUS on 2018/11/8.
 */

public class Activity_Conversation extends Activity_Base {

    private Intent intent;
    private String mTargetId,mTargetName;   //对方的id与名字
    private Button Btn_conversation_back;
    private TextView TV_conversation_targetName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);

        InitView();
    }

    private void InitView(){
        intent=getIntent();
        mTargetId = intent.getData().getQueryParameter("targetId");
        mTargetName=intent.getData().getQueryParameter("title");

        setTitle(mTargetName);
    }

}
