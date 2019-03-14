package com.example.asus.organization2.Activity.PersonalPage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.PersonalPage.OrgInformation.Activity_organizationInformation;
import com.example.asus.organization2.Adapter.Adapter_organizationList;
import com.example.asus.organization2.Bean.Info_organizationMessage;
import com.example.asus.organization2.Message.Message_Local;
import com.example.asus.organization2.Override.ListView_Load;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.asus.organization2.StringData.String_NetURL.URL_getOrgMessageById;

/**
 * Created by ASUS on 2019/2/8.
 */

public class Fragment_organizationList_myAttend extends Fragment {

    int sum;
    int nowNumber=0;  //目前已经读取到了数组中的哪个人
    private ListView_Load Lv_organization_admin;
    private Adapter_organizationList Adapter_organization;
    ArrayList<Info_organizationMessage> info_messagesArray = new ArrayList<>();
    ArrayList<Integer> orgAdminId = new ArrayList<>();
    protected static int SUCCESS = 0X111;

    final Handler myHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == SUCCESS) {

                if(Lv_organization_admin.getAdapter()==null)      //第一次加载的时候设置adapter
                {
                    Adapter_organization = new Adapter_organizationList(info_messagesArray,getActivity());
                    Lv_organization_admin.setAdapter(Adapter_organization);
                }else{
                    Adapter_organization.setNewsList(info_messagesArray);
                }
                //加载完成
                Lv_organization_admin.loadComplete();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_organizationlist_myadmin, container, false);

        for(int temp = 0; temp < Message_Local.organizationPlace.size(); temp ++) {
            if(Message_Local.organizationPlace.get(temp) > 2) {
                orgAdminId.add(Message_Local.organizationId.get(temp));
            }
        }
        sum = orgAdminId.size();
        Lv_organization_admin = rootView.findViewById(R.id.Lv_organization_admin);
        Lv_organization_admin.setOnItemClickListener(new Fragment_organizationList_myAttend.MyOnItemClickListener());

        init();
        return rootView;
    }

    public void init(){
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
                loadOnlineById(orgAdminId.get(temp));
            }
            nowNumber += 10;
            sum -= 10;
        }
        else
        {
            for(int temp = nowNumber;temp<=nowNumber + sum%10 -1;temp++) {
                loadOnlineById(orgAdminId.get(temp));
            }
            sum = 0;
        }
    }

    public void loadOnlineById(int id){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("id",id+"");
    /*
     * 通过id查找社团信息接口
     * 传入 社团的 id
     * 返回 status = 1 , name,type,affiliatedUnit,schoolName,star,headPicture,residentName
     */
        client.post(URL_getOrgMessageById, params, new AsyncHttpResponseHandler() {
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
                    int type = object.getInt("type");
                    String affiliatedUnit = object.getString("affiliatedUnit");
                    String schoolName = object.getString("schoolName");
                    int star = object.getInt("star");
                    String headPicutre = object.getString("headPicutre");
                    String presidentName = object.getString("presidentName");

                    info_messagesArray.add(new Info_organizationMessage(id,name,type,affiliatedUnit,schoolName,star,presidentName,headPicutre));

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
            Info_organizationMessage message = (Info_organizationMessage) parent.getItemAtPosition(position);
            Intent intent=new Intent(getActivity(),Activity_organizationInformation.class);
            intent.putExtra("message",message);
            startActivity(intent);
        }
    }

    //若到达最下面，则继续加载
    private void myLoadAttention() {
        Lv_organization_admin.setInterface(new ListView_Load.ILoadListener() {
            @Override
            public void onLoad() {
                loadMessage();
            }
        });
    }
}

