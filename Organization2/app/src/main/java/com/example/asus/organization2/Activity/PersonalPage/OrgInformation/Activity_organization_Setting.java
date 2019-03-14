package com.example.asus.organization2.Activity.PersonalPage.OrgInformation;

import android.os.Bundle;

import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;

/**
 * Created by ASUS on 2019/2/12.
 */

public class Activity_organization_Setting extends Activity_Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_setting);

        Init();
    }

    private void Init(){
        setTitle("设置");
    }
}
