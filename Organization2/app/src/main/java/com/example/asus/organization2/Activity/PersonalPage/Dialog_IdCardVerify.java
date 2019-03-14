package com.example.asus.organization2.Activity.PersonalPage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.organization2.R;


/**
 * Created by ASUS on 2018/12/19.
 */

public class Dialog_IdCardVerify extends Dialog implements View.OnClickListener {

    private TextView Tv_dialogIdCardVerify_name,Tv_dialogIdCardVerify_sex,Tv_dialogIdCardVerify_nation,Tv_dialogIdCardVerify_birth,Tv_dialogIdCardVerify_address,Tv_dialogIdCardVerify_title;
    private Button Button_dialogIdCardVerify_OK,Button_dialogIdCardVerify_photoAgain;
    private View.OnClickListener mListener;
    private Context mContext;

    public Dialog_IdCardVerify(@NonNull Context context, View.OnClickListener listener) {
        super(context, R.style.Theme_AppCompat_DayNight_NoActionBar);
        mListener = listener;
        mContext = context;
        init(mContext);
    }

    private void init(Context context){
        View view = View.inflate(context,R.layout.dialog_personal_idcardverify,null);
        setContentView(view);
        initView(view);
    }

    private void initView(View v){
        Tv_dialogIdCardVerify_name = v.findViewById(R.id.Tv_dialogIdCardVerify_name);
        Tv_dialogIdCardVerify_sex = v.findViewById(R.id.Tv_dialogIdCardVerify_sex);
        Tv_dialogIdCardVerify_nation = v.findViewById(R.id.Tv_dialogIdCardVerify_nation);
        Tv_dialogIdCardVerify_birth = v.findViewById(R.id.Tv_dialogIdCardVerify_birth);
        Tv_dialogIdCardVerify_address = v.findViewById(R.id.Tv_dialogIdCardVerify_address);
        Tv_dialogIdCardVerify_title = v.findViewById(R.id.Tv_dialogIdCardVerify_title);
        Button_dialogIdCardVerify_OK = v.findViewById(R.id.Button_dialogIdCardVerify_OK);
        Button_dialogIdCardVerify_photoAgain = v.findViewById(R.id.Button_dialogIdCardVerify_photoAgain);

        Button_dialogIdCardVerify_OK.setOnClickListener(this);
        Button_dialogIdCardVerify_photoAgain.setOnClickListener(this);

        Window window = getWindow();
        WindowManager m = ((Activity)mContext).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams params = window.getAttributes();// 获取对话框当前的参数值
        params.gravity = Gravity.CENTER;
        params.alpha = 1.0f;
        params.height = (int)(d.getHeight() * 0.6);
        params.width = (int)(d.getWidth()*0.8);
        window.setAttributes(params);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Button_dialogIdCardVerify_OK:
                dismiss();
                if(mListener != null)   //如果在原页面有设置点击函数的话，则执行。
                    mListener.onClick(v);
                break;
            case R.id.Button_dialogIdCardVerify_photoAgain:
                dismiss();
                if(mListener != null)
                    mListener.onClick(v);
                break;
        }
    }

    public void setMessage(String title,String name,String sex,String nation,String birth,String address){
        Tv_dialogIdCardVerify_title.setText(title);
        Tv_dialogIdCardVerify_name.setText(name);
        Tv_dialogIdCardVerify_sex.setText(sex);
        Tv_dialogIdCardVerify_nation.setText(nation);
        Tv_dialogIdCardVerify_birth.setText(birth);
        Tv_dialogIdCardVerify_address.setText(address);
    }
}
