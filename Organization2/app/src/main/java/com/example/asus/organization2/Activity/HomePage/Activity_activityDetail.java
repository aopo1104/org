package com.example.asus.organization2.Activity.HomePage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Bean.Info_ActivityMessage;
import com.loopj.android.image.SmartImageView;

import jp.wasabeef.glide.transformations.CropSquareTransformation;

/**
 * 活动/比赛详情页
 * 需传入 Info_ActivityMessage类型的message
 */

public class Activity_activityDetail extends Activity_Base {

    private Info_ActivityMessage activityMessage;
    private TextView Tv_activityDetailTitle,Tv_activityDetailorganization,Tv_activityDetailContent;
    private SmartImageView Iv_activityDetailPicture;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitydetail);

        init();
    }

    private void init(){
        Intent intent = getIntent();
        activityMessage = (Info_ActivityMessage) intent.getSerializableExtra("message");

        String title = activityMessage.getType() == 1? "活动详情页" : "比赛详情页";
        setTitle(title);

        Tv_activityDetailTitle = findViewById(R.id.Tv_activityDetailTitle);
        Tv_activityDetailorganization = findViewById(R.id.Tv_activityDetailorganization);
        Tv_activityDetailContent = findViewById(R.id.Tv_activityDetailContent);
        Iv_activityDetailPicture = findViewById(R.id.Iv_activityDetailPicture);

        Tv_activityDetailTitle.setText(activityMessage.getTitle());
        Tv_activityDetailorganization.setText("发布单位:  "+activityMessage.getorganizationName());
        Tv_activityDetailContent.setText(activityMessage.getContent());
        Glide.with(mContext).load(activityMessage.getPicture())
                .bitmapTransform(new CropSquareTransformation(mContext))
                .into(Iv_activityDetailPicture);
        //Iv_activityDetailPicture.setImageUrl(activityMessage.getPicture(), R.drawable.ic_launcher_background, R.drawable.ic_launcher_background);
    }
}
