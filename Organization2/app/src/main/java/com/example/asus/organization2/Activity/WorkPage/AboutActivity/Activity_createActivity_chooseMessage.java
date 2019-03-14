package com.example.asus.organization2.Activity.WorkPage.AboutActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Message.Message_Local;

import java.util.ArrayList;
import java.util.List;

public class Activity_createActivity_chooseMessage extends Activity_Base implements View.OnClickListener {

    Spinner Spinner_createActivity_type,Spinner_createActivity_unit;
    Button Btn_createActivity_next;
    private List<String> spinner_Data_List;
    private ArrayAdapter<String> spinner_Arr_Adapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createactivity_choosemessage);

        initView();
        InitSpinner();  //初始化并加载spinner
    }

    private void initView(){
        setTitle("发布");

        Spinner_createActivity_type = findViewById(R.id.Spinner_createActivity_type);
        Spinner_createActivity_unit = findViewById(R.id.Spinner_createActivity_unit);
        Btn_createActivity_next = findViewById(R.id.Btn_createActivity_next);

        Btn_createActivity_next.setOnClickListener(this);
    }

    private void InitSpinner(){
        spinner_Data_List = new ArrayList<String>();
        if(!Message_Local.organizationName.isEmpty()){
                for(int i=0;i<Message_Local.organizationName.size();i++){
                    if(Message_Local.organizationPlace.get(i) <= 2)  //会长或部长，才有权限
                    spinner_Data_List.add(Message_Local.organizationName.get(i));
                }
        }
        //适配器
        spinner_Arr_Adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinner_Data_List);
        //设置样式
        spinner_Arr_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        Spinner_createActivity_unit.setAdapter(spinner_Arr_Adapter);
        //监听适配器
        //Spinner_createActivity_unit.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Btn_createActivity_next:
                if(spinner_Data_List.isEmpty()){
                    showToast(mContext,"没有权限发布");
                    break;
                }else{
                    int type = (int)Spinner_createActivity_type.getSelectedItemId() + 1;
                    int organizationId = Message_Local.organizationId.get((int)Spinner_createActivity_unit.getSelectedItemId());
                    intent = new Intent(mContext,Activity_createActivity.class);
                    intent.putExtra("type",type);
                    intent.putExtra("organizationId",organizationId);
                    finish();
                    startActivity(intent);
                }
                break;
        }
    }

}
