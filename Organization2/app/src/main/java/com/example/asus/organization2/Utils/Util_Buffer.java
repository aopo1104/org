package com.example.asus.organization2.Utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.example.asus.organization2.R;


/**
 * 缓冲页面
 * 1、定义  private View bufferView;    //加载页面
 * 2、bufferView = Util_Buffer.CreateBufferActivity(mActivity);    //还在加载时，先显示缓冲页面
 * 3、 bufferView.setVisibility(View.GONE);
 */

public class Util_Buffer {

    /*
     * 动态创建ProgressBar，用于页面还在获取网络数据时，先显示缓冲页面
     */
    public static View CreateBufferActivity(Activity activity) {
        //1.找到activity根部的ViewGroup，类型都为FrameLayout。
        FrameLayout rootContainer = (FrameLayout) activity.findViewById(android.R.id.content);//固定写法，返回根视图
        // 2.初始化控件显示的位置
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER;
        // 3.设置控件显示位置
        final View view = View.inflate(activity, R.layout.activity_buffer, null);
        view.setVisibility(View.VISIBLE);
        // 4.将控件加到根节点下
        rootContainer.addView(view);
        return view;
    }

}
