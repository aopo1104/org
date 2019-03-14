package com.example.asus.organization2.Activity.PersonalPage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Message.Message_Local;
import com.ssk.unityARGuide.Speech;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ASUS on 2019/2/2.
 */

public class Activity_Personal_Page extends Activity_Base implements View.OnClickListener {

    private ImageView Img_personalPage_headPictureBack,Img_personalPage_headPicture;
    private TextView Tv_personalPage_name,Tv_personalPage_phoneNumber,Tv_personalPage_collection,Tv_personalPage_friend,Tv_personalPage_organization;
    private RelativeLayout layout_personalPage_myPublishTask;
    private LinearLayout layout_personalPage_arguide;
    private Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_page);

        init();
    }

    private void init() {
        setTitle("个人");
        setHeadLeftButtonVisibility(View.GONE);
        Button rightButton = getHeadRightButton();
        rightButton.setBackground(getResources().getDrawable(R.drawable.setting));   //设置头部右边按钮

        Img_personalPage_headPictureBack = findViewById(R.id.Img_personalPage_headPictureBack);
        Img_personalPage_headPicture = findViewById(R.id.Img_personalPage_headPicture);
        //Img_personalPage_headPicture.setOnClickListener(this);

        layout_personalPage_myPublishTask = findViewById(R.id.layout_personalPage_myPublishTask);
        layout_personalPage_arguide = findViewById(R.id.layout_personalPage_arguide);
        Tv_personalPage_name = findViewById(R.id.Tv_personalPage_name);
        Tv_personalPage_phoneNumber = findViewById(R.id.Tv_personalPage_phoneNumber);
        Tv_personalPage_collection = findViewById(R.id.Tv_personalPage_collection);
        Tv_personalPage_friend = findViewById(R.id.Tv_personalPage_friend);
        Tv_personalPage_organization = findViewById(R.id.Tv_personalPage_organization);

        Tv_personalPage_name.setText(Message_Local.name);
        Tv_personalPage_phoneNumber.setText(Message_Local.phoneNumber);

        Tv_personalPage_organization.setText(Message_Local.organizationId.size()+"");
        Tv_personalPage_friend.setText(Message_Local.friendsArray.size()-1+"");

        Tv_personalPage_organization.setOnClickListener(this);
        Tv_personalPage_friend.setOnClickListener(this);
        layout_personalPage_myPublishTask.setOnClickListener(this);
        layout_personalPage_arguide.setOnClickListener(this);
        //设置背景磨砂效果
        Glide.with(this).load(Message_Local.headPicture)
                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(Img_personalPage_headPictureBack);
        //设置圆形图像
        Glide.with(this).load(Message_Local.headPicture)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(Img_personalPage_headPicture);
    }

    //标题右边的setting按钮
    @Override
    public void onHeadRightButtonClick(View view){
        intent = new Intent(mContext,Activity_PersonalSetting.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Tv_personalPage_organization:
                intent = new Intent(mContext,Activity_organizationList.class);
                startActivity(intent);
                break;
            case R.id.Tv_personalPage_friend:
                intent = new Intent(mContext,Activity_FriendsList.class);
                startActivity(intent);
                break;
            case R.id.layout_personalPage_myPublishTask:
                intent = new Intent(mContext,Activity_myPublishTask.class);
                startActivity(intent);
                break;
            case R.id.layout_personalPage_arguide:
                new AlertDialog.Builder(this)
                        .setTitle("进入AR导游")
                        .setMessage("确定要进入温州大学AR导游吗")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent=new Intent(mContext, Speech.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
        }
    }
}
