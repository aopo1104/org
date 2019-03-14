package com.example.asus.organization2.Activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.asus.organization2.R;


/**
 * Created by ASUS on 2018/12/21.
 */

public class Activity_Base extends AppCompatActivity {

    protected Context mContext;

    private ViewFlipper mContentView;
    protected LinearLayout mHeadLayout;
    protected Button mBtnLeft;
    protected Button mBtnRight;
    protected TextView mTitle;
    protected TextView mHeadRightText;
    private Drawable mBtnBackDrawable;

    protected static int SUCCESS = 0X111;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.layout_base);
        mContext = this;

        // 初始化公共头部
        mContentView = (ViewFlipper) super.findViewById(R.id.layout_container);
        mHeadLayout = (LinearLayout) super.findViewById(R.id.layout_head);
        mHeadRightText = (TextView) findViewById(R.id.text_right);
        mBtnLeft = (Button) super.findViewById(R.id.btn_left);
        mBtnRight = (Button) super.findViewById(R.id.btn_right);
        mTitle = (TextView) super.findViewById(R.id.tv_title);
        mBtnBackDrawable = getResources().getDrawable(R.drawable.icon_back);
        mBtnBackDrawable.setBounds(0, 0, mBtnBackDrawable.getMinimumWidth(),
                mBtnBackDrawable.getMinimumHeight());
    }

    @Override
    public void setContentView(View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        mContentView.addView(view, lp);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(view);
    }

    /**
     * 设置头部是否可见
     *
     * @param visibility
     */
    public void setHeadVisibility(int visibility) {
        mHeadLayout.setVisibility(visibility);
    }

    /**
     * 设置左边是否可见
     * 如果为View.GONE，则不可见
     *
     * @param visibility
     */
    public void setHeadLeftButtonVisibility(int visibility) {
        mBtnLeft.setVisibility(visibility);
    }

    /**
     * 设置右边是否可见
     *
     * @param visibility
     */
    public void setHeadRightButtonVisibility(int visibility) {
        mBtnRight.setVisibility(visibility);
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        setTitle(title, false);
    }

    /**
     * 设置标题
     *如果flag为true，则不存在返回键
     */
    public void setTitle(String title, boolean flag) {
        mTitle.setText(title);
        if (flag) {
            mBtnLeft.setCompoundDrawables(null, null, null, null);
        } else {
            mBtnLeft.setCompoundDrawables(mBtnBackDrawable, null, null, null);
        }
    }

    /**
     * 点击左按钮
     */
    public void onHeadLeftButtonClick(View v) {
        finish();
    }

    /**
     * 点击右按钮
     */
    public void onHeadRightButtonClick(View v) {

    }

    public Button getHeadLeftButton() {
        return mBtnLeft;
    }

    public void setHeadLeftButton(Button leftButton) {
        this.mBtnLeft = leftButton;
    }

    public Button getHeadRightButton() {
        return mBtnRight;
    }

    public void setHeadRightButton(Button rightButton) {
        this.mBtnRight = rightButton;
    }

    public Drawable getHeadBackButtonDrawable() {
        return mBtnBackDrawable;
    }

    public void setBackButtonDrawable(Drawable backButtonDrawable) {
        this.mBtnBackDrawable = backButtonDrawable;
    }

    public void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

}
