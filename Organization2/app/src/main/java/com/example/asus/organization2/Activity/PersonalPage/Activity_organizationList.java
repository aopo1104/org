package com.example.asus.organization2.Activity.PersonalPage;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2019/2/8.
 */

public class Activity_organizationList extends Activity_Base{

    private ViewPager vp_organizationList;
    private List<String> list;

    List<Fragment> fragmentlist = new ArrayList<Fragment>();
    private TabLayout tabLayout_organizationList_Page;
    MyFragmentPagerAdapter myFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizationlist_page);

        setTitle("我的组织");

        vp_organizationList = findViewById(R.id.vp_organizationList);
        tabLayout_organizationList_Page = findViewById(R.id.tabLayout_organizationList_Page);
        myFragmentPagerAdapter  = new MyFragmentPagerAdapter(getSupportFragmentManager());

        init();

        //适配器
        vp_organizationList.setAdapter(myFragmentPagerAdapter);
        //设置TabLayout的模式
        tabLayout_organizationList_Page.setTabMode(TabLayout.MODE_FIXED);
        //让tablayout和Viewpager关联;
        tabLayout_organizationList_Page.setupWithViewPager(vp_organizationList);
    }

    public void init(){
        list=new ArrayList<String>();
        list.add("我管理的");
        fragmentlist.add(new Fragment_organizationList_myAdmin());
        list.add("我参加的");
        fragmentlist.add(new Fragment_organizationList_myAttend());
//        Toast.makeText(planPage_Activity.this,my.getPageTitle(0),Toast.LENGTH_LONG).show();
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
}
