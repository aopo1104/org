package com.example.asus.organization2.Activity.PersonalPage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Message.Message_Local;
import com.example.asus.organization2.StringData.String_NetURL;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.youtu.Youtu;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/*
        实名认证页面
 */
public class Activity_hasNotVerified extends Activity_Base implements View.OnClickListener{

    public static final String APP_ID = "10161933";
    public static final String SECRET_ID = "AKIDdDk7EA3aaPBrMjuxpBMOL85uZ3NcrfJt";
    public static final String SECRET_KEY = "azyZ0GXfmpnTPCnDRbdWOq9IF1oTx9wa";
    public static final String USER_ID = "517036283";
    public Youtu faceYoutu; //腾讯优图 身份证识别

    public static final int SUCCESS = 0X111;
    public static final int OPEN_PHOTO_REQUEST_CODE = 1;
    private Uri imgUrl = null;  //存放照片的地址
    private ImageView Img_personalHasNotVerified_photoShow;

    public Button Btn_personalHasNotVerified_openPhoto;

    public String name;
    public String sex;
    public String nation;
    public String birth;
    public String address;


    final Handler myHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == SUCCESS) {
                showDialog();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_hasnotverified);
        Init();
    }

    //初始化
    public void Init(){
        setTitle("实名认证");

        faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY,Youtu.API_YOUTU_END_POINT,USER_ID); //初始化腾讯优图，要用到身份证识别
        Btn_personalHasNotVerified_openPhoto = findViewById(R.id.Btn_personalHasNotVerified_openPhoto);
        Btn_personalHasNotVerified_openPhoto.setOnClickListener(this);
    }

    //开启摄像头
    public void openPhoto(){
        File file = new File(getExternalCacheDir(), "imageView.jpg");//打开存储照片位置的file
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (Build.VERSION.SDK_INT >= 24) {//版本高于 7.0
            imgUrl = FileProvider.getUriForFile(mContext, "net.deniro.camera.fileProvider", file);
        } else {
            imgUrl = Uri.fromFile(file);
        }

        //打开摄像头
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUrl);//指定图片输出地址
        startActivityForResult(intent, OPEN_PHOTO_REQUEST_CODE);

        //显示拍摄的照片
        Img_personalHasNotVerified_photoShow = findViewById(R.id.Img_personalHasNotVerified_photoShow);
    }

    //拍完照片的回调函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case OPEN_PHOTO_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    try {//解析图片并显示，压缩并读取身份证上的信息
                        Bitmap bitmap1 =  BitmapFactory.decodeStream(getContentResolver().openInputStream(imgUrl));
                        Img_personalHasNotVerified_photoShow.setImageBitmap(bitmap1);          //在imageview中显示大图
                        BitmapFactory.Options options = new BitmapFactory.Options();    //压缩，用小图来获取信息,加快运行速度
                        options.inSampleSize = 8;
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imgUrl), null, options);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                                new FileOutputStream(imgUrl.getPath()));
                        try {
                            getIdCardMessage();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    public void getIdCardMessage() throws JSONException {

        new Thread() {
            public void run() {
                JSONObject response;
                try {
                    response = faceYoutu.IdCardOcr(imgUrl.getPath(),0);
                    name = response.getString("name");
                    sex = response.getString("sex");
                    nation = response.getString("nation");
                    birth = response.getString("birth");
                    address = response.getString("address");

                    Message msg = Message.obtain();
                    msg.what = SUCCESS;
                    myHandler.sendMessage(msg);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Btn_personalHasNotVerified_openPhoto:
                openPhoto();
                break;
        }
    }

    //识别完身份证显示dialog，让用户查看是否有错
    public void showDialog(){
        Dialog_IdCardVerify  Dialog_IdCardVerify= new Dialog_IdCardVerify(mContext, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.Button_dialogIdCardVerify_OK:
                        Log.e("111","OK");
                        //这里连接数据库 上传数据
                        takeVerifyMessageInDatabase();
                        break;
                    case R.id.Button_dialogIdCardVerify_photoAgain:
                        openPhoto();
                        break;
                }
            }
        });
        Dialog_IdCardVerify.setMessage("请确认信息是否正确",name,sex,nation,birth,address);
        Dialog_IdCardVerify.show();
    }

    private void takeVerifyMessageInDatabase(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.add("Id", Message_Local.id+"");
        params.add("name", name);
        params.add("sex",sex);
        params.add("birth",birth);

        /***
         * 实名认证接口
         * 需要传入Id(int),realName(String),sex(String，“男”或“女”),birth(String)
         * 返回：成功 status = 1
         * 失败：status = 2
         */
        client.post(String_NetURL.URL_realVerify, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    int status = object.getInt("status");
                    if(status == 2) //访问失败
                    {
                        showToast(mContext,"抱歉，认证失败");
                        return;
                    }
                    else    //上传成功
                    {
                        Message_Local.realVerify(1,name,sex,birth);
                        showToast(mContext,"恭喜，认证成功！");
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                showToast(mContext,"请检查网络连接");
            }
        });
    }
}
