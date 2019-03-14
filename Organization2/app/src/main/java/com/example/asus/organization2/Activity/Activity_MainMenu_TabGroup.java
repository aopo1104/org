package com.example.asus.organization2.Activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.organization2.Activity.AddressListPage.Activity_AddressList_Page;
import com.example.asus.organization2.Activity.HomePage.Activity_Home_Page;
import com.example.asus.organization2.Activity.MessagePage.Activity_Message_Page;
import com.example.asus.organization2.Activity.PersonalPage.Activity_Personal_Page;
import com.example.asus.organization2.Activity.WorkPage.Activity_Work_Page;
import com.example.asus.organization2.R;
import com.example.asus.organization2.Message.Message_Local;


/**
 * Created by ASUS on 2018/12/20.
 */

public class Activity_MainMenu_TabGroup extends ActivityGroup implements View.OnClickListener{

    private static final String TAG="TabGroupActicity";
    private Bundle mBundle=new Bundle();
    private LinearLayout ll_container,ll_first,ll_second,ll_third,ll_fourth,ll_fifth;

    private TextView text_homePage,text_messagePage,text_workPage,text_addresslistPage,text_personalPage;
    private ImageView img_homePage,img_messagePage,img_workPage,img_addresslistPage,img_personalPage;

    long exitTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_bottom);

        Log.e(TAG, Message_Local.getMessageString());
        initView();

        ll_first.setOnClickListener(this);
        ll_second.setOnClickListener(this);
        ll_third.setOnClickListener(this);
        ll_fourth.setOnClickListener(this);
        ll_fifth.setOnClickListener(this);

        mBundle.putString("tag",TAG);
        changeContainerView(ll_first);
    }

    /***********************初始化按钮，布局等*****************/
    private void initView()
    {
        ll_container=(LinearLayout)findViewById(R.id.ll_container);
        ll_first=(LinearLayout)findViewById(R.id.ll_first);
        ll_second=(LinearLayout)findViewById(R.id.ll_second);
        ll_third=(LinearLayout)findViewById(R.id.ll_third);
        ll_fourth=(LinearLayout)findViewById(R.id.ll_fourth);
        ll_fifth=(LinearLayout)findViewById(R.id.ll_fifth);

        text_homePage=(TextView)findViewById(R.id.text_homePage);
        text_messagePage=(TextView)findViewById(R.id.text_messagePage);
        text_workPage = findViewById(R.id.text_workPage);
        text_addresslistPage=(TextView)findViewById(R.id.text_addresslistPage);
        text_personalPage=(TextView)findViewById(R.id.text_personalPage);

        img_homePage=findViewById(R.id.img_homePage);
        img_messagePage=findViewById(R.id.img_messagePage);
        img_workPage = findViewById(R.id.img_workPage);
        img_addresslistPage=findViewById(R.id.img_addresslistPage);
        img_personalPage=findViewById(R.id.img_personalPage);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.ll_first||view.getId()==R.id.ll_second||view.getId()==R.id.ll_third||view.getId()==R.id.ll_fourth||view.getId()==R.id.ll_fifth){
            changeContainerView(view);
        }
    }

    private void changeContainerView(View view) {
        resetView();
        view.setSelected(true);
        int lightBlue= Color.argb(255,0,191,255);
        if(view==ll_first){
            toActivity("first",Activity_Home_Page.class);
            text_homePage.setTextColor(lightBlue);
            img_homePage.setImageResource(R.drawable.home_page_check);
        }else if(view==ll_second){
            toActivity("second",Activity_Message_Page.class);
            text_messagePage.setTextColor(lightBlue);
            img_messagePage.setImageResource(R.drawable.message_page_check);
        }else if(view==ll_third){
            toActivity("third",Activity_Work_Page.class);
            text_workPage.setTextColor(lightBlue);
            img_workPage.setImageResource(R.drawable.work_page_check);
        }else if(view==ll_fourth){
            toActivity("fourth",Activity_AddressList_Page.class);
            text_addresslistPage.setTextColor(lightBlue);
            img_addresslistPage.setImageResource(R.drawable.addresslist_page_check);
        }else if(view==ll_fifth){
            toActivity("fifth",Activity_Personal_Page.class);
            text_personalPage.setTextColor(lightBlue);
            img_personalPage.setImageResource(R.drawable.personal_page_check);
        }
    }

    private void toActivity(String label, Class<?>cls) {
        Intent intent=new Intent(this,cls).putExtras(mBundle);
        ll_container.removeAllViews();
        View view=getLocalActivityManager().startActivity(label,intent).getDecorView();
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        ll_container.addView(view);
    }

    private void resetView()
    {
        ll_first.setSelected(false);
        ll_second.setSelected(false);
        ll_third.setSelected(false);
        ll_fourth.setSelected(false);
        ll_fifth.setSelected(false);
        text_homePage.setTextColor(Color.BLACK);
        text_messagePage.setTextColor(Color.BLACK);
        text_workPage.setTextColor(Color.BLACK);
        text_addresslistPage.setTextColor(Color.BLACK);
        text_personalPage.setTextColor(Color.BLACK);
        img_homePage.setImageResource(R.drawable.home_page);
        img_messagePage.setImageResource(R.drawable.message_page);
        img_workPage.setImageResource(R.drawable.work_page);
        img_addresslistPage.setImageResource(R.drawable.addresslist_page);
        img_personalPage.setImageResource(R.drawable.personal_page);
    }
    /*
    重写返回键：按两次退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {

            if((System.currentTimeMillis()-exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
            {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }
            else
            {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
