package com.example.asus.organization2.Activity.AddressListPage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Message.Message_Local;
import com.example.asus.organization2.StringData.String_NetURL;
import com.example.asus.organization2.Utils.Util_Image;
import com.example.asus.organization2.Utils.Util_Photo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class Activity_createOrg extends Activity_Base implements View.OnClickListener {

    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;

    private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;

    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() +"/"+ Message_Local.id+"photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/"+Message_Local.id+"crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;

    private Spinner Spinner_CreateOrg_orgType;
    private Spinner Spinner_CreateOrg_orgStar;

    private ImageView Iv_CreateOrg_headPicutre;
    private EditText ET_CreateOrg_OrgName;
    private EditText ET_CreateOrg_SchoolName;
    private EditText ET_CreateOrg_affiliatedUnitName;
    private Button Btn_CreateOrg_create;
    private LinearLayout layout_CreateOrg_orgStar;

    private String orgName;
    private String schoolName;
    private String affiliatedUnityName;
    private String img_url;
    private Integer orgType;
    private Integer orgStar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createorg);

        initView();
    }

    private void initView(){
        setTitle("创建社团/组织");

        layout_CreateOrg_orgStar = findViewById(R.id.layout_CreateOrg_orgStar);
        Iv_CreateOrg_headPicutre = findViewById(R.id.Iv_CreateOrg_headPicutre);
        ET_CreateOrg_OrgName=findViewById(R.id.ET_CreateOrg_OrgName);
        ET_CreateOrg_SchoolName=findViewById(R.id.ET_CreateOrg_SchoolName);
        ET_CreateOrg_affiliatedUnitName=findViewById(R.id.ET_CreateOrg_affiliatedUnitName);
        Spinner_CreateOrg_orgType=findViewById(R.id.Spinner_CreateOrg_orgType);
        Spinner_CreateOrg_orgType.setOnItemSelectedListener(new spinnerSelectedListenner());
        Spinner_CreateOrg_orgStar=findViewById(R.id.Spinner_CreateOrg_orgStar);
        Btn_CreateOrg_create=findViewById(R.id.Btn_CreateOrg_create);
        Iv_CreateOrg_headPicutre.setOnClickListener(this);
        Btn_CreateOrg_create.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case (R.id.Btn_CreateOrg_create):
                LoadMessage();
                if(CheckCreate())   //通过验证以后保存入数据库和本地
                {
                    SaveNewOrgOnline(img_url);
                }
                break;
            case R.id.Iv_CreateOrg_headPicutre:
                //选择照片按钮
                autoObtainStoragePermission();
                break;
        }
    }

    /**************把editText中的内容更新到变量中***************/
    private void LoadMessage()
    {
        orgName=ET_CreateOrg_OrgName.getText().toString();
        schoolName=ET_CreateOrg_SchoolName.getText().toString();
        affiliatedUnityName=ET_CreateOrg_affiliatedUnitName.getText().toString();
        orgType=(int)Spinner_CreateOrg_orgType.getSelectedItemId()+1;
        if(orgType == 2)
            orgStar =0;
        else
            orgStar=(int)Spinner_CreateOrg_orgStar.getSelectedItemId()+1;
    }

    /**************验证数据是不是都输入了内容***************/
    private Boolean CheckCreate()
    {
        Boolean judge=true;
        if(orgName.isEmpty())
        {
            Toast.makeText(mContext,"请输入社团/组织名字",Toast.LENGTH_SHORT).show();
            judge=false;
        }
        else if(schoolName.isEmpty())
        {
            Toast.makeText(mContext,"请输入学校名",Toast.LENGTH_SHORT).show();
            judge=false;
        }
        else if(affiliatedUnityName.isEmpty())
        {
            Toast.makeText(mContext,"请输入挂靠单位",Toast.LENGTH_SHORT).show();
            judge=false;
        }
        else if(img_url == null)
        {
            Toast.makeText(mContext,"请上传头像",Toast.LENGTH_SHORT).show();
            judge=false;
        }
        return judge;
    }

    /**************将新建的社团内容存到数据库中(要更新Organization和People 2个数据库***************/
    private void SaveNewOrgOnline(String img_url)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("orgName",orgName);
        params.add("schoolName",schoolName);
        params.add("affiliatedUnityName",affiliatedUnityName);
        params.add("orgType",orgType+"");
        params.add("orgStar",orgStar+"");
        params.add("createPersonId",Message_Local.id+"");
        params.add("type",2+"");

        try {
            params.put("uploadFile", new File(img_url));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /*
     * 创建社团接口
     * 需要传入：orgName,schoolName,affiliatedUnityName,orgType(社团1，组织2),orgStar,createPersonId,
     * 返回 成功：status = 1 organization_id
     * 失败：status =2
     */
        client.post(String_NetURL.URL_createOrg, params, new AsyncHttpResponseHandler() {
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
                        Toast.makeText(mContext,"请检查网络连接",Toast.LENGTH_SHORT).show();
                    }
                    else    //获取成功
                    {
                       int organizationId = Integer.parseInt(object.getString("organization_id"));
                        SaveNewOrgLocal(organizationId);
                        Toast.makeText(mContext,"创建成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(mContext,"请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**************将新建的社团内容存到本地***************/
    private void SaveNewOrgLocal(int organizationId)
    {
        Message_Local.organizationId.add(organizationId);
        Message_Local.organizationName.add(orgName);
        Message_Local.organizationPlace.add(1);
    }



    //监听spinner，若类型为社团，则需要选择星级
    private class spinnerSelectedListenner implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {         //望文生义，当列表项被选择时
            // TODO Auto-generated method stub
            String select = parent.getItemAtPosition(position).toString();//取得被选中的列表项的文字
            if (select.equals("社团")){
                layout_CreateOrg_orgStar.setVisibility(View.VISIBLE);
            }else{
                layout_CreateOrg_orgStar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }

    /**
     * 自动获取sdk权限
     */

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            Util_Photo.openPic(this, CODE_GALLERY_REQUEST);
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //访问相册完成回调
                case CODE_GALLERY_REQUEST:
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(Util_Photo.getPath(this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            newUri = FileProvider.getUriForFile(this, "com.zz.fileprovider", new File(newUri.getPath()));
                        }
                        Util_Photo.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    } else {
                        showToast(mContext,"该设备没有SD卡");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    img_url= Util_Image.getImageAbsolutePath(this,cropImageUri);
                    BitmapFactory.Options options = new BitmapFactory.Options();    //压缩，用小图来获取信息,加快运行速度
                    options.inSampleSize = 8;
                    Uri img_Uri = Uri.fromFile(new File(img_url));
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(img_Uri), null, options);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                                new FileOutputStream(img_Uri.getPath()));
                        String updateTime = String.valueOf(System.currentTimeMillis()); // 在需要重新获取更新的图片时调用

                        Glide.with(this)
                                .load(img_Uri)
                                .fitCenter().signature(new StringSignature(updateTime))
                                .into(Iv_CreateOrg_headPicutre);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
