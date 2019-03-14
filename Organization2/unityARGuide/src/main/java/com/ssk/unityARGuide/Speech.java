package com.ssk.unityARGuide;

import android.view.KeyEvent;

import com.ssm.ssm.speechrecognizer.MainActivity;


/**
 * Created by Administrator on 2018/6/11.
 */

public class Speech extends MainActivity {
    @Override public boolean onKeyDown(int keyCode, KeyEvent event)   {
        if(keyCode==4){
            mUnityPlayer.quit();
        }
        return mUnityPlayer.injectEvent(event);
    }
}
