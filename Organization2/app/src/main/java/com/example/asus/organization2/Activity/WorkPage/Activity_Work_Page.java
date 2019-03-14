package com.example.asus.organization2.Activity.WorkPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.asus.organization2.Activity.WorkPage.AboutTask.Activity_taskPublish;
import com.example.asus.organization2.Activity.WorkPage.AboutTask.Activity_workPlan;
import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Activity.AddressListPage.Activity_createOrg;
import com.example.asus.organization2.Activity.WorkPage.AboutActivity.Activity_createActivity_chooseMessage;
import com.example.asus.organization2.Activity.WorkPage.onDuty.Activity_onDuty;
import com.example.asus.organization2.Message.Message_Local;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2019/2/2.
 */

public class Activity_Work_Page extends Activity_Base implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Spinner Spinner_workPage_Org;
    List<String> spinner_Data_List;
    ArrayAdapter<String> spinner_Arr_Adapter;
    Button Btn_workPage_createActivity,Btn_workdPage_createOrg;
    Intent intent;
    LinearLayout layout_wordPage_onlyAdmin;
    RelativeLayout layout_workPage_activityPublish,layout_workPage_taskPublish,layout_workPage_onduty,layout_workPage_workPlan;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_page);

        init();
        InitSpinner();
    }

    private void init(){
        setTitle("工作");
        setHeadLeftButtonVisibility(View.GONE);

        Spinner_workPage_Org = findViewById(R.id.Spinner_workPage_Org);


        Btn_workdPage_createOrg = findViewById(R.id.Btn_workdPage_createOrg);
        Btn_workdPage_createOrg.setOnClickListener(this);

        layout_wordPage_onlyAdmin = findViewById(R.id.layout_wordPage_onlyAdmin);
        layout_workPage_onduty = findViewById(R.id.layout_workPage_onduty);
        layout_workPage_activityPublish = findViewById(R.id.layout_workPage_activityPublish);
        layout_workPage_taskPublish = findViewById(R.id.layout_workPage_taskPublish);
        layout_workPage_workPlan = findViewById(R.id.layout_workPage_workPlan);
        layout_workPage_onduty.setOnClickListener(this);
        layout_workPage_activityPublish.setOnClickListener(this);
        layout_workPage_taskPublish.setOnClickListener(this);
        layout_workPage_workPlan.setOnClickListener(this);
    }

    /************初始化并加载spinner*************/
    private void InitSpinner(){
        spinner_Data_List = new ArrayList<String>();
        if(!Message_Local.organizationName.isEmpty()) {
            spinner_Data_List = Message_Local.organizationName;
        }
        else {
            ArrayList<String> emptyOrgName_arrayList=new ArrayList<>();
            emptyOrgName_arrayList.add("请先加入一个组织");
            spinner_Data_List= emptyOrgName_arrayList;
        }

        //适配器
        spinner_Arr_Adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinner_Data_List);
        //设置样式
        spinner_Arr_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        Spinner_workPage_Org.setAdapter(spinner_Arr_Adapter);
        //监听适配器
        Spinner_workPage_Org.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_workPage_activityPublish:  //创建活动
                intent = new Intent(mContext,Activity_createActivity_chooseMessage.class);
                startActivity(intent);
                break;
            case R.id.layout_workPage_taskPublish:
                intent = new Intent(mContext,Activity_taskPublish.class);
                startActivity(intent);
                break;
            case R.id.Btn_workdPage_createOrg:  //创建社团
                intent = new Intent(mContext, Activity_createOrg.class);
                startActivity(intent);
                break;
            case R.id.layout_workPage_onduty:   //值班签到
                intent = new Intent(mContext, Activity_onDuty.class);
                startActivity(intent);
                break;
            case R.id.layout_workPage_workPlan:
                intent = new Intent(mContext, Activity_workPlan.class);
                startActivity(intent);
                break;
        }
    }

    //监听适配器
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Message_Local.selectOrg=i;   //把当前选择操作的组织序号存入本地，方便接下来操作。
        Log.e("org",Message_Local.organizationId.get(Message_Local.selectOrg)+"");
        Log.e("org2",Message_Local.organizationName.get(Message_Local.selectOrg));
        Log.e("org3",Message_Local.organizationPlace.get(Message_Local.selectOrg)+"");
        if(Message_Local.organizationPlace.get(Message_Local.selectOrg) <=2 ){  //如果为管理员权限，则显示“社团管理”
            layout_wordPage_onlyAdmin.setVisibility(View.VISIBLE);
        }else{
            layout_wordPage_onlyAdmin.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
