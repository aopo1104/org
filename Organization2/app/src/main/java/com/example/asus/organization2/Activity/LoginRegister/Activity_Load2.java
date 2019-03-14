package com.example.asus.organization2.Activity.LoginRegister;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_MainMenu_TabGroup;
import com.example.asus.organization2.Message.Message_Local;
import com.example.asus.organization2.Message.Message_SharedPreferences;
import com.example.asus.organization2.Mod.EBmessage;
import com.example.asus.organization2.StringData.String_NetURL;
import com.example.asus.organization2.Utils.Util_Http;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class Activity_Load2 extends AppCompatActivity {

    private static final String TAG = "Activity_Load2";
    int sum = 0;    //因为开了2个线程，所以当2个线程都完毕时才进入首页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        LoadMessage();
        x.view().inject(this);
        EventBus.getDefault().register(this);//初始化xutils与绑定eventbus

        if (Message_Local.myToken.equals("")) {	//如果没有获取过token的话就重新获取
            Util_Http.getToken(Message_Local.id+"", Message_Local.name,Message_Local.headPicture);		//调用函数，但是运行在线程中
            Log.e("Init_Activity","111");
        }
    }

    public void onEventMainThread(EBmessage msg) {   //Eventbus的接收
        Log.e("Init_Activity", "receive");
        if (msg.getFrom().equals("connect")) {
            if (msg.getStatus()) {
                handler.sendEmptyMessage(0x1111);
            } else
                Toast.makeText(Activity_Load2.this, "连接到IM服务器失败", Toast.LENGTH_SHORT).show();
        } else if (msg.getFrom().equals("getToken")) {
            if (msg.getStatus()) {
                Log.e("Init_Activity", "success");
                Util_Http.connect(msg.getMessage(), getApplicationContext());
            } else {
                Toast.makeText(Activity_Load2.this, "获取Token失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void LoadMessage(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("id",Message_Local.id+"");
              /*
         * 查找接口，
         * status=1:查询成功 返回id,nickName,headPicture,phoneNumber,Email,sex,isReal,realName
         * status=2：查询失败，没有此人
         */
        client.post(String_NetURL.URL_findFriendsById, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                Log.e("222", response);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    int status = object.getInt("status");
                    if(status == 2) //获取信息失败
                    {
                        Toast.makeText(Activity_Load2.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
                    }
                    else    //获取成功
                    {
                        int id = object.getInt("id");
                        String name = object.getString("name");
                        String phoneNumber = object.getString("phoneNumber");
                        int sex = object.getInt("sex");
                        String email = object.getString("email");
                        String school = object.getString("school");
                        String academy = object.getString("academy");
                        String className = object.getString("class");
                        String studentid = object.getString("studentid");
                        String birth = object.getString("birth");
                        String headPicture = object.getString("headpicture");
                        int isreal = object.getInt("isreal");
                        String friendsArray = object.getString("friendsArray");

                        ArrayList<Integer> organizationId = new ArrayList<>();
                        ArrayList<String> organizationName = new ArrayList<>();
                        ArrayList<Integer> organizationPlace = new ArrayList<>();
                        JSONArray organizationMessage = object.getJSONArray("organizationMessage");
                        if(organizationMessage.length()!=0){ //如果该人以前有加入过组织
                            for(int temp = 0;temp < organizationMessage.length();temp ++){
                                JSONObject object_organization = (JSONObject) organizationMessage.get(temp);
                                organizationId.add(object_organization.getInt("organizationId"));
                                organizationName.add(object_organization.getString("organizationName"));
                                organizationPlace.add(object_organization.getInt("organizationPlace"));
                            }
                        }
                        Message_Local.saveLocalMessage(1,id,name,phoneNumber,sex,email,school,academy,className,studentid,birth,
                                headPicture,isreal,friendsArray,organizationId,organizationName,organizationPlace);  //将数据传到Local_Message中
                        handler.sendEmptyMessage(0x1112);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(Activity_Load2.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
                Log.e("load2",throwable.toString());
            }
        });
    }

    public Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            Log.e(TAG,"111");
            if ( msg.what == 0x1111 || msg.what == 0x1112) {    //0x1111为成功获得token和连接上im 0x1112为成功从数据库获取到信息
                if (sum == 1){//即上个线程已经完毕
                    Message_SharedPreferences.saveMessage(Activity_Load2.this); //保存登录状态到sharedpreferences
                    Intent intent = new Intent(Activity_Load2.this, Activity_MainMenu_TabGroup.class);
                    finish();
                    startActivity(intent);
                }
                else sum++;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this); //解绑eventbus
    }
}
