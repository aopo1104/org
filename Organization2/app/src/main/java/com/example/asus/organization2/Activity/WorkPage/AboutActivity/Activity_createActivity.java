package com.example.asus.organization2.Activity.WorkPage.AboutActivity;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

/**
 * Created by ASUS on 2019/2/13.
 */

public class Activity_createActivity extends Activity_Base implements View.OnClickListener {

    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;

    private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;

    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() +"/"+ Message_Local.id+"photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/"+Message_Local.id+"crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;
    private String img_url;


    EditText Et_createActivity_title,Et_createActivity_content;
    Button Btn_createActivity_create;
    ImageView Iv_CreateActivity_picutre;
    Spinner Spinner_createActivity_type;
    int type;//1；活动 2：比赛
    int organizationId;  //发布的社团id
    String title,content;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_activity);

        init();
    }

    private void init(){
        setTitle("发布");
        Intent i = getIntent();
        organizationId = i.getIntExtra("organizationId",0);
        type = i.getIntExtra("type",0);


        Et_createActivity_title = findViewById(R.id.Et_createActivity_title);
        Et_createActivity_content = findViewById(R.id.Et_createActivity_content);
        Btn_createActivity_create = findViewById(R.id.Btn_createActivity_create);
        Iv_CreateActivity_picutre = findViewById(R.id.Iv_CreateActivity_picutre);
        Spinner_createActivity_type = findViewById(R.id.Spinner_createActivity_type);

        Iv_CreateActivity_picutre.setOnClickListener(this);
        Btn_createActivity_create.setOnClickListener(this);
    }

    private void LoadMessage(){
        title = Et_createActivity_title.getText().toString().trim();
        content = Et_createActivity_content.getText().toString().trim();
    }

    /**************验证数据是不是都输入了内容***************/
    private Boolean CheckCreate(){
        Boolean judge=true;
        if(title.isEmpty()){
            showToast(mContext,"请先输入标题");
            judge = false;
        } else if (content.isEmpty()) {
            showToast(mContext,"请先输入内容");
            judge = false;
        }
        return judge;
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
                                .into(Iv_CreateActivity_picutre);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private void SaveNewActivityOnline(String img_url){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("organizationId",organizationId+"");
        params.add("type",type+"");
        params.add("personId",Message_Local.id+"");
        params.add("title",title);
        params.add("content",content);
        try {
            params.put("uploadFile", new File(img_url));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /**
         * 创建活动/比赛
         * 传入 organizationId,type(1:活动，2：比赛),personId（发布人id），title，content，picture（文件）
         * 返回 成功： status = 1
         * 失败：status = 2
         */
        client.post(String_NetURL.URL_createActivity, params, new AsyncHttpResponseHandler() {
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
                        Toast.makeText(mContext,"发布成功",Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Btn_createActivity_create:
                LoadMessage();
                if(CheckCreate())   //通过验证以后保存入数据库
                {
                    SaveNewActivityOnline(img_url);
                }
                break;
            case R.id.Iv_CreateActivity_picutre:
                autoObtainStoragePermission();
                break;
        }
    }


}
