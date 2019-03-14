package com.example.asus.organization2.Activity.PersonalPage.OrgInformation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Bean.Info_organizationMessage;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * 社团的详细信息activity
 * 需要从上一个页面传来一个Info_organizationMessage类型
 */

public class Activity_organizationInformation extends Activity_Base {

    private ImageView Img_OrgInformation_headPictureBack,Img_OrgInformation_headPicture;
    private TextView Tv_OrgInformation_name;
    private ViewPager vp_OrgInformationList;
    private List<String> list;
    private Intent intent;

    List<Fragment> fragmentlist = new ArrayList<Fragment>();
    private TabLayout tabLayout_OrgInformation_Page;
    Activity_organizationInformation.MyFragmentPagerAdapter myFragmentPagerAdapter;

    private Info_organizationMessage message;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_information);

        vp_OrgInformationList = findViewById(R.id.vp_OrgInformationList);
        tabLayout_OrgInformation_Page = findViewById(R.id.tabLayout_OrgInformation_Page);
        myFragmentPagerAdapter  = new Activity_organizationInformation.MyFragmentPagerAdapter(getSupportFragmentManager());

        init();

        //适配器
        vp_OrgInformationList.setAdapter(myFragmentPagerAdapter);
        //设置TabLayout的模式
        tabLayout_OrgInformation_Page.setTabMode(TabLayout.MODE_FIXED);
        //让tablayout和Viewpager关联;
        tabLayout_OrgInformation_Page.setupWithViewPager(vp_OrgInformationList);
    }

    public void init(){
        setTitle("详情页");
        Button rightButton = getHeadRightButton();
        rightButton.setBackground(getResources().getDrawable(R.drawable.setting));   //设置头部右边按钮

        Intent intent = getIntent();
        message = (Info_organizationMessage) intent.getSerializableExtra("message");

        Img_OrgInformation_headPictureBack = findViewById(R.id.Img_OrgInformation_headPictureBack);
        Img_OrgInformation_headPicture = findViewById(R.id.Img_OrgInformation_headPicture);
        Tv_OrgInformation_name = findViewById(R.id.Tv_OrgInformation_name);

        Tv_OrgInformation_name.setText(message.getName());

        //设置背景磨砂效果
        Glide.with(this).load(message.getHeadPicture())
                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(Img_OrgInformation_headPictureBack);
        //设置圆形图像
        Glide.with(this).load(message.getHeadPicture())
                .bitmapTransform(new CropCircleTransformation(this))
                .into(Img_OrgInformation_headPicture);

        list=new ArrayList<String>();
        list.add("首页");
        fragmentlist.add(new Fragment_organizationInformation_firstPage());
        list.add("成员");
        fragmentlist.add(new Fragment_organizationInformation_members());
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return fragmentlist.get(position);
        }

        @Override
        public int getCount() {
            return fragmentlist.size();
        }

        //需要重写个返回标题的方法;
        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position);
        }
    }

    //标题右边的setting按钮
    @Override
    public void onHeadRightButtonClick(View view){
        intent = new Intent(mContext,Activity_organization_Setting.class);
        startActivity(intent);
    }
}
