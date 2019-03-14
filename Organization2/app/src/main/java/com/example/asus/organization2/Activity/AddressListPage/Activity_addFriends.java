package com.example.asus.organization2.Activity.AddressListPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;

/**
 * Created by ASUS on 2018/10/31.
 */

public class Activity_addFriends extends Activity_Base implements View.OnClickListener {

    Button Btn_addFriendsPage_createOrg,Btn_addFriendsPage_addOrg,Btn_addFriendsPage_addFriendsbyPhone,Btn_addFriendsPage_addFriendsbyOrgNumber;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriends);

        Init();
    }

    private void Init(){
        setTitle("创建/添加");

        Btn_addFriendsPage_addOrg=findViewById(R.id.Btn_addFriendsPage_addOrg);
        Btn_addFriendsPage_addOrg.setOnClickListener(this);
        Btn_addFriendsPage_createOrg=findViewById(R.id.Btn_addFriendsPage_createOrg);
        Btn_addFriendsPage_createOrg.setOnClickListener(this);
        Btn_addFriendsPage_addFriendsbyPhone=findViewById(R.id.Btn_addFriendsPage_addFriendsbyPhone);
        Btn_addFriendsPage_addFriendsbyPhone.setOnClickListener(this);
        Btn_addFriendsPage_addFriendsbyOrgNumber=findViewById(R.id.Btn_addFriendsPage_addFriendsbyOrgNumber);
        Btn_addFriendsPage_addFriendsbyOrgNumber.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Btn_addFriendsPage_createOrg:
                Intent intent1=new Intent(mContext,Activity_createOrg.class);
                startActivity(intent1);
                break;
            /*case R.id.Btn_addFriendsPage_addOrg:
                break;*/
            case R.id.Btn_addFriendsPage_addFriendsbyPhone:
                Intent intent2=new Intent(mContext,Activity_addFriendsByPhone.class);
                startActivity(intent2);
                break;
            case R.id.Btn_addFriendsPage_addFriendsbyOrgNumber:
                Intent intent3=new Intent(mContext,Activity_addFriendsByPersonNumber.class);
                startActivity(intent3);
                break;

        }
    }
}
