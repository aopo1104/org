package com.example.asus.organization2.Activity.PersonalPage;

import android.os.Bundle;

import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;


public class Activity_Personal_PhoneNumber extends Activity_Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_phone);

        setTitle("手机号码");
    }
}
