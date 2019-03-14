package com.example.asus.organization2;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mob.MobSDK;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

import static com.example.asus.organization2.StringData.String_NetURL.URL_findFriendsById;


/**
 * Created by ASUS on 2018/11/8.
 */

public class APP extends Application implements RongIM.UserInfoProvider{

    private static Context content;
    private UserInfo userInfo;
    private String name,headPicture;

    @Override
    public void onCreate() {

        super.onCreate();

        x.Ext.init(this);   //初始化xutils3框架

        content = getApplicationContext();
        MobSDK.init(this);

        if (getApplicationInfo().packageName
                .equals(getCurProcessName(getApplicationContext()))
                || "io.rong.push"
                .equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK���õ�һ�� ��ʼ��
             */
            RongIM.init(this);
        }


        RongIM.setUserInfoProvider(this, true);//设置用户信息提供者。
    }

    @Override
    public UserInfo getUserInfo(final String userId) {
            //在这里，根据userId，使用同步的请求，去请求服务器，就可以完美做到显示用户的头像，昵称了

            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.add("id",userId);

             /*
     * 查找接口，
     * status=1:查询成功 返回id,nickName,headPicture,phoneNumber,Email,sex,isReal,realName
     * status=2：查询失败，没有此人
     */
        client.post(URL_findFriendsById, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                Log.e("222", response);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    String headPicture = object.getString("headpicture");
                    String name = object.getString("name");
                    userInfo=new UserInfo(userId,name,Uri.parse(headPicture));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
            //userInfo=new UserInfo("4","dmj",Uri.parse("http://134.175.128.249/tp5/public/uploads/upload_picture/init.png"));
            return userInfo;
        }


    public static Context getObjectContext() {
        return content;
    }

    /**
     * ��õ�ǰ���̵�����
     *
     * @param context
     * @return ���̺�
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
