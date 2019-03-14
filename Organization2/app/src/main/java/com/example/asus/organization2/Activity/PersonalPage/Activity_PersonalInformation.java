package com.example.asus.organization2.Activity.PersonalPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Bean.Info_PeronalMessage;
import com.example.asus.organization2.Message.Message_Local;

import java.util.ArrayList;

import io.rong.imkit.RongIM;


/**
 * 用户的详细信息activity
 * 需要从上一个页面传来一个Info_PersonalMessage类型
 */

public class Activity_PersonalInformation extends Activity_Base implements View.OnClickListener {

    //这里的变量如果没有特殊强调，指的都是查询的好友的信息
    int personNumber;
    String phoneNumber,name,studentID;
    ArrayList<String> organizationName;
    TextView Tv_personalInformation_name,Tv_personalInformation_personNumber,Tv_personalInformation_studentID,Tv_personalInformation_phoneNumber,Tv_personalInformation_organizationName;
    Button Btn_personalInformation_sendMessage,Btn_personalInformation_addFriends;
    ImageView Img_personalInformation_headPicture;
    String face_otherpersonUri;
    Boolean isFriend=false; //判断该人与用户是否为好友。
    Info_PeronalMessage message;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        isFriend=false;
        ReceiveDataFromFrontActivity();         //从上一个页面中接受数据
        Init();
        GetData();
        JudgeIsFriend();
        addView();
    }

    private void ReceiveDataFromFrontActivity(){
        Intent intent = getIntent();
        message = (Info_PeronalMessage) intent.getSerializableExtra("message");
    }

    private void Init(){
        setTitle("详细信息");

        Tv_personalInformation_name=findViewById(R.id.Tv_personalInformation_name);
        Tv_personalInformation_personNumber=findViewById(R.id.Tv_personalInformation_personNumber);
        Tv_personalInformation_studentID=findViewById(R.id.Tv_personalInformation_studentID);
        Tv_personalInformation_phoneNumber=findViewById(R.id.Tv_personalInformation_phoneNumber);
        Tv_personalInformation_organizationName=findViewById(R.id.Tv_personalInformation_organizationName);
        Img_personalInformation_headPicture=findViewById(R.id.Img_personalInformation_headPicture);

        Btn_personalInformation_sendMessage=findViewById(R.id.Btn_personalInformation_sendMessage);
        Btn_personalInformation_sendMessage.setOnClickListener(this);
        Btn_personalInformation_addFriends=findViewById(R.id.Btn_personalInformation_addFriends);
        Btn_personalInformation_addFriends.setOnClickListener(this);
    }

    //从数据库中读出数据
    private void GetData(){
        name = message.getName();
        personNumber = message.getId();
        studentID = message.getStudentid();
        phoneNumber = message.getPhoneNumber();
        organizationName = message.getorganizationName();
    }

     private void addView(){
         Tv_personalInformation_name.setText(name);
         Tv_personalInformation_personNumber.setText(personNumber+"");
         Tv_personalInformation_studentID.setText(studentID);
         Tv_personalInformation_phoneNumber.setText(phoneNumber);
         String orgName_String="";
         if(organizationName.size() == 0)
             orgName_String="该同学还未加入组织";
         else{
             for(int i=0;i<organizationName.size();i++)
                 orgName_String += organizationName.get(i)+"\n";
         }

         Tv_personalInformation_organizationName.setText(orgName_String);

         String updateTime = String.valueOf(System.currentTimeMillis());
         Glide.with(this)
                 .load(message.getHeadPicture())
                 .fitCenter().signature(new StringSignature(updateTime))
                 .into(Img_personalInformation_headPicture);
     }

     //判断是否是好友
     public void JudgeIsFriend(){
         if(Message_Local.friendsArray.contains(message.getId())) {   //表示在关注列表中有该人
             isFriend = true;
             Btn_personalInformation_addFriends.setText("已是好友");
         }else{
             isFriend = false;
             Btn_personalInformation_addFriends.setText("添加好友");
         }
     }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.Btn_personalInformation_sendMessage:
                if (RongIM.getInstance()!=null){
                    RongIM.getInstance().startPrivateChat(mContext,personNumber+"",name);
                }
                break;
            case R.id.Btn_personalInformation_addFriends:
                if(isFriend==false){
                    Toast.makeText(mContext,"好友请求已发送",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(mContext,"对方已是您的好友",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
