package com.example.asus.organization2.Activity.PersonalPage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Adapter.Adapter_FriendsList;
import com.example.asus.organization2.Bean.Info_PeronalMessage;
import com.example.asus.organization2.Message.Message_Local;
import com.example.asus.organization2.Override.ListView_Load;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.asus.organization2.StringData.String_NetURL.URL_findFriendsById;

/**
 * Created by ASUS on 2019/2/8.
 */

public class Activity_FriendsList extends Activity_Base {

        int sum;
        int nowNumber=1;  //目前已经读取到了数组中的哪个人
        private ListView_Load Lv_friends;
        private Adapter_FriendsList friendsAdapter;
        ArrayList<Info_PeronalMessage> info_messagesArray = new ArrayList<>();

        final Handler myHandler = new Handler() {

            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == SUCCESS) {

                    if(Lv_friends.getAdapter()==null)      //第一次加载的时候设置adapter
                    {
                        friendsAdapter = new Adapter_FriendsList(info_messagesArray,mContext);
                        Lv_friends.setAdapter(friendsAdapter);
                    }else{
                        friendsAdapter.setNewsList(info_messagesArray);
                    }
                    //加载完成
                    Lv_friends.loadComplete();
                }
            }
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_friendslist);
            init();
        }

    public void init(){
        setTitle("好友列表");
        sum = Message_Local.friendsArray.size()-1;
        Lv_friends = findViewById(R.id.Lv_friends);
        Lv_friends.setOnItemClickListener(new Activity_FriendsList.MyOnItemClickListener());

        loadMessage();
        myLoadAttention();
    }

    public void loadMessage(){
        int remain = sum / 10;    //若remain = 0 则说明还有多余10个好友， 否则则少于10个
        if(sum == 0)    //如果sum = 0，则直接结束
            return;
        if(remain != 0)
        {
            for(int temp = nowNumber;temp<=nowNumber + 9;temp++){
                loadOnlineById(Message_Local.friendsArray.get(temp));
            }
            nowNumber += 10;
            sum -= 10;
        }
        else
        {
            for(int temp = nowNumber;temp<=nowNumber + sum%10 -1;temp++) {
                loadOnlineById(Message_Local.friendsArray.get(temp));
            }
            sum = 0;
        }
    }

    public void loadOnlineById(int id){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("id",id+"");
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
                    int status = object.getInt("status");

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
                    info_messagesArray.add(new Info_PeronalMessage(id,name,phoneNumber,sex,email,school,academy,className,studentid,birth,
                            headPicture,isreal,organizationId,organizationName,organizationPlace));

                    Message msg = Message.obtain();
                    msg.what = SUCCESS;
                    Bundle bundle = new Bundle();
                    msg.setData(bundle);
                    myHandler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Info_PeronalMessage message = (Info_PeronalMessage) parent.getItemAtPosition(position);
            Intent intent=new Intent(mContext,Activity_PersonalInformation.class);
            intent.putExtra("message",message);
            startActivity(intent);
        }
    }

    //若到达最下面，则继续加载
    private void myLoadAttention() {
        Lv_friends.setInterface(new ListView_Load.ILoadListener() {
            @Override
            public void onLoad() {
                loadMessage();
            }
        });
    }
}

