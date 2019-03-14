package com.example.asus.organization2.Activity.AddressListPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Activity.PersonalPage.Activity_PersonalInformation;
import com.example.asus.organization2.Bean.Info_PeronalMessage;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.image.SmartImageView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.asus.organization2.StringData.String_NetURL.URL_findFriendsById;


/**
 * Created by ASUS on 2018/11/3.
 */

public class Activity_addFriendsByPersonNumber extends Activity_Base implements View.OnClickListener {

    LayoutInflater inflater;
    LinearLayout lin;
    RelativeLayout layout,layoutEmpty;
    EditText Edt_addfriendsbyPersonNumber_personNumber;
    Button Btn_addfriendsbyPersonNumber_search;
    Integer search_personNumber;
    Info_PeronalMessage message;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriendsbypersonnumber);
        inflater = LayoutInflater.from(this);// 获取需要被添加控件的布局

        Init();
    }

    private void Init(){
        setTitle("搜索");

        Edt_addfriendsbyPersonNumber_personNumber=findViewById(R.id.Edt_addfriendsbyPersonNumber_personNumber);

        Btn_addfriendsbyPersonNumber_search=findViewById(R.id.Btn_addfriendsbyPersonNumber_search);
        Btn_addfriendsbyPersonNumber_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Btn_addfriendsbyPersonNumber_search:
                search_personNumber=Integer.parseInt(Edt_addfriendsbyPersonNumber_personNumber.getText().toString());
                GoSearch();
                break;
            case R.id.friendsfind_layout:               //如果查询到用户时的点击事件
                clickEvent();
                break;
        }
    }

    private void GoSearch(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("id",search_personNumber+"");
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
                    if(status == 1){
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
                        message = new Info_PeronalMessage(id,name,phoneNumber,sex,email,school,academy,className,studentid,birth,
                                headPicture,isreal,organizationId,organizationName,organizationPlace);
                        addView();
                    }else{
                        addEmptyView();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    /*
        先获取到原来的界面，然后往里面添加view，view分为2种，1种为查询成功，1种为查询失败。在添加时，均先把原来的view给清空，再添加。
     */
    //查询到信息时显示的view
    private void addView(){
        if(layout==null){
            lin = findViewById(R.id.Lin_addfriendsbyPersonNumber_needAdd);
            // 获取需要添加的布局
            layout = inflater.inflate(
                    R.layout.view_friendsfind, null).findViewById(R.id.friendsfind_layout);
            // 将布局加入到当前布局中
            lin.addView(layout);
            layout.setOnClickListener(this);
        }
        else{
            lin.removeViewInLayout(layoutEmpty);
            lin.removeViewInLayout(layout);
            lin.addView(layout);
        }
        SmartImageView Iv_friendsfind = findViewById(R.id.Iv_friendsfind);
        TextView Tv_friendsfind_name=findViewById(R.id.Tv_friendsfind_name);
        TextView Tv_friendsfind_phoneNumber=findViewById(R.id.Tv_friendsfind_phoneNumber);
        Iv_friendsfind.setImageUrl(message.getHeadPicture(), R.drawable.ic_launcher_background, R.drawable.ic_launcher_background);
        Tv_friendsfind_name.setText(message.getName());
        Tv_friendsfind_phoneNumber.setText(message.getPhoneNumber()+"");
    }

    //未查询到信息时显示的view
    private void addEmptyView(){
        if(layoutEmpty==null){
            lin = findViewById(R.id.Lin_addfriendsbyPersonNumber_needAdd);
            // 获取需要添加的布局
            layoutEmpty = inflater.inflate(
                    R.layout.view_friendsfindempty, null).findViewById(R.id.friendsfindempty_layout);
            // 将布局加入到当前布局中
            lin.addView(layoutEmpty);
        }
        else{
            lin.removeViewInLayout(layoutEmpty);
            lin.removeViewInLayout(layout);
            lin.addView(layoutEmpty);
        }
    }

    //如果查询到用户时的点击事件，将数据传到下一个页面，并跳转
    private void clickEvent(){
        Intent intent=new Intent(Activity_addFriendsByPersonNumber.this,Activity_PersonalInformation.class);
        intent.putExtra("message",message);
        startActivity(intent);
    }
}
