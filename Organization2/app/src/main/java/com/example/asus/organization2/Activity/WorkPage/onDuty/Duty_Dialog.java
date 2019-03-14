package com.example.asus.organization2.Activity.WorkPage.onDuty;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.asus.organization2.R;


/**
 * Created by ASUS on 2018/10/19.
 */

public class Duty_Dialog extends Dialog {


    /**
     * 上下文对象 *
     */
    Activity context;

    private Button Btn_dutySubmit;

    public static EditText Edt_dutyReport;
    public static RadioGroup RG_mood;
    public static RadioButton RBt_happyMood;
    public static RadioButton RBt_justsosoMood;
    public static RadioButton RBt_badMood;


    private View.OnClickListener mClickListener;

    public Duty_Dialog(Activity context) {
        super(context);
        this.context = context;
    }

    public Duty_Dialog(Activity context, int theme, View.OnClickListener clickListener) {
        super(context, theme);
        this.context = context;
        this.mClickListener = clickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.dialog_duty);

        Edt_dutyReport = (EditText) findViewById(R.id.Edt_dutyReport);
        RG_mood=findViewById(R.id.RG_mood);
        RBt_happyMood=findViewById(R.id.RBt_happyMood);
        RBt_justsosoMood=findViewById(R.id.RBt_justsosoMood);
        RBt_badMood=findViewById(R.id.RBt_badMood);

        Window dialogWindow = this.getWindow();

        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);

        // 根据id在布局中找到控件对象
        Btn_dutySubmit = (Button) findViewById(R.id.Btn_dutySubmit);

        // 为按钮绑定点击事件监听器
        Btn_dutySubmit.setOnClickListener(mClickListener);

        this.setCancelable(true);
    }
}
