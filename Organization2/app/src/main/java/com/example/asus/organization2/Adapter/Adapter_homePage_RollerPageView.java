package com.example.asus.organization2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.HomePage.Activity_activityDetail;
import com.example.asus.organization2.Bean.Info_ActivityMessage;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ASUS on 2019/2/12.
 */

public class Adapter_homePage_RollerPageView extends StaticPagerAdapter {


    private ArrayList<Info_ActivityMessage> activityMessages;
    private Context context;

    public Adapter_homePage_RollerPageView(Context context, ArrayList activityMessages){
        this.activityMessages = activityMessages;
        this.context = context;
    }

    @Override
    public View getView(ViewGroup container, int position) {

        RelativeLayout layout = new RelativeLayout(container.getContext());
        ImageView view = new ImageView(layout.getContext());
        Glide.with(layout.getContext()).load(activityMessages.get(position).getPicture())
                .bitmapTransform(new CropCircleTransformation(layout.getContext()))
                .into(view);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.addView(view);

        View view_type = new View(container.getContext());
        if(activityMessages.get(position).getType() == 1){
            view_type.setBackgroundResource(R.drawable.triangle_shape_brown);
        }
        else{
            view_type.setBackgroundResource(R.drawable.triangle_shape_red);
        }

        ViewGroup.MarginLayoutParams mp = new ViewGroup.MarginLayoutParams(300,300);  //item的宽高
        mp.setMargins(0, 0, 0, 0);//分别是margin_top那四个属性
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mp);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        view_type.setLayoutParams(lp);
        layout.addView(view_type);

        TextView Tv_type = new TextView(container.getContext());
        ViewGroup.MarginLayoutParams mp2 = new ViewGroup.MarginLayoutParams(100,100);  //item的宽高
        mp2.setMargins(0, 8, 0, 0);//分别是margin_top那四个属性
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(mp2);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        Tv_type.setLayoutParams(lp2);
        Tv_type.setRotation(45);
        Tv_type.setTextColor(Color.WHITE);
         if(activityMessages.get(position).getType() == 1){
             Tv_type.setText("活动");
        }
        else{
             Tv_type.setText("比赛");
        }
        layout.addView(Tv_type);


        TextView Tv_title = new TextView(container.getContext());
        ViewGroup.MarginLayoutParams mp3 = new ViewGroup.MarginLayoutParams(1000,100);  //item的宽高
        mp3.setMargins(0, 30, 0, 0);//分别是margin_top那四个属性
        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(mp3);
        lp3.addRule(RelativeLayout.CENTER_HORIZONTAL);
        Tv_title.setLayoutParams(lp3);
        Tv_title.setGravity(Gravity.CENTER);
        Tv_title.setText(activityMessages.get(position).getTitle());
        Tv_title.setTextColor(Color.BLACK);
        Tv_title.setTextSize(20);
        layout.addView(Tv_title);

        viewOnClick(position,layout);




        return layout;
    }


    @Override
    public int getCount() {
        return 4;
    }

    private void viewOnClick(final int position, View view){
        final int img_order = position;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Activity_activityDetail.class);
                intent.putExtra("message",activityMessages.get(position));
                context.startActivity(intent);
            }
        });
    }
}