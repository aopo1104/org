package com.example.asus.organization2.Activity.WorkPage.onDuty;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.example.asus.organization2.R;
import com.example.asus.organization2.Activity.Activity_Base;
import com.example.asus.organization2.Message.Message_Local;
import com.example.asus.organization2.StringData.String_NetURL;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Activity_onDuty extends Activity_Base implements View.OnClickListener, LocationSource, AMapLocationListener {
    //AMap是地图对象
    private AMap aMap;
    private MapView mapView;
    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null;
    //声明mListener对象，定位监听器
    private LocationSource.OnLocationChangedListener mListener = null;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private MyLocationStyle myLocationStyle;

    private Button Btn_OnDuty_Start;

    private int isOnduty=0;   //判断是否正在值班

    private int dutyNumber; //值班编号

    private LatLng newLatLng;// 经纬度

    private int mood;    //心情(1:好 2：一般 3：不好)
    private String report;  //值班汇报


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onduty);
        initView();
        initEvent();
        initMap(savedInstanceState);  //初始化地图
        getDutyMessageLocal();  //获取本地储存的值班状态
        if(isOnduty==1)
            Btn_OnDuty_Start.setText("结束值班");
    }

    private void initMap(Bundle savedInstanceState){
        mapView = (MapView) findViewById(R.id.Map_OnDuty_Start);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            setUpMap();
        }
        //开始定位
        location();
    }

    //初始化地图
    private void setUpMap() {
        aMap = mapView.getMap();
        //设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        aMap.setLocationSource(this);//设置了定位的监听
        // 是否显示定位按钮
        settings.setMyLocationButtonEnabled(true);

        aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是false
    }

    private void location() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        // mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Sport);
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        //设置地图显示大小
        aMap.moveCamera(CameraUpdateFactory.zoomTo(19));

        //设置定位小蓝点
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        myLocationStyle.anchor(0.5f,0.5f);//设置定位蓝点图标的锚点方法。

    }


    //监听定位变化
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() ==0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点

                double currentLat = aMapLocation.getLatitude();//获取纬度
                double currentLon = aMapLocation.getLongitude();//获取经度
                newLatLng = new LatLng(currentLat, currentLon);  // latlng形式的
                //将地图移动到定位点
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                mListener.onLocationChanged(aMapLocation);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.Btn_OnDuty_Start:
                pressButtonEvent();
                break;
        }
    }

    private void pressButtonEvent(){
        if(isOnduty==1)    //如果正在值班
        {
            EndDuty();
        }
        else
        {
            StartDuty();
        }
    }

    //结束值班,出现弹窗，当点击弹窗中的“提交”后，才实现之后的处理
    private void EndDuty(){

        showEditDialog();
    }

    //开始值班
    private void StartDuty(){
        Btn_OnDuty_Start.setText("结束值班");
        isOnduty=1;

        CreateNewDutyMessage();
    }

    private void CreateNewDutyMessage(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("userbase_id",Message_Local.id+"");
        params.add("organization_id", Message_Local.organizationId.get(Message_Local.selectOrg)+"");
        params.add("startLongitude",newLatLng.longitude+"");
        params.add("startLatitude",newLatLng.latitude+"");
        /*
         * 开始值班签到
         * 传入userbase_id,organization_id,startLongitude,startLatitude
         * 返回 status = 1 , onduty_id
         */
        client.post(String_NetURL.URL_startDuty, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                Log.e("222", response);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    dutyNumber = object.getInt("onduty_id");
                    Toast.makeText(mContext,"开始值班",Toast.LENGTH_SHORT).show();
                    SaveDutyMessageLocal(); //将值班信息保存到本地，方便下次读取
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

    public void showEditDialog() {
        Duty_Dialog  duty_dialog= new Duty_Dialog(this,R.style.Theme_AppCompat_Light_NoActionBar,onClickListener);
        duty_dialog.show();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.Btn_dutySubmit:
                    Btn_OnDuty_Start.setText("开始值班");
                    isOnduty=0;

                    if(Duty_Dialog.RBt_happyMood.isChecked())
                        mood=1;
                    else if(Duty_Dialog.RBt_justsosoMood.isChecked())
                        mood=2;
                    else if(Duty_Dialog.RBt_badMood.isChecked())
                        mood=3;


                    report = Duty_Dialog.Edt_dutyReport.getText().toString().trim();
                    UpdateDutyMessage();
                    SaveDutyMessageLocal();
                    break;
            }
        }
    };

    //点击“结束值班”时，把结束时的数据重新存入
    private void UpdateDutyMessage(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("onduty_id",dutyNumber+"");
        params.add("endLongitude",newLatLng.longitude+"");
        params.add("endLatitude",newLatLng.latitude+"");
        params.add("mood",mood+"");
        params.add("report",report);

        client.post(String_NetURL.URL_endDuty, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                Log.e("222", response);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    Toast.makeText(mContext,"结束值班",Toast.LENGTH_SHORT).show();
                    finish();
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

    //把值班编号和值班状态存到本地
    private void SaveDutyMessageLocal(){
        FileOutputStream fos;
        String content =  dutyNumber+ "##" + isOnduty;
        try {
            fos = openFileOutput("duty.txt", MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //从本地读出值班编号和值班状态
    private void getDutyMessageLocal(){
        String content="";
        FileInputStream fis;
        try{
            fis=openFileInput("duty.txt");
            byte[] buffer=new byte[fis.available()];
            fis.read(buffer);
            content=new String(buffer);
            if(content!=null){
                String[] userInfo=content.split("##");
                dutyNumber=Integer.parseInt(userInfo[0]);
                isOnduty=Integer.parseInt(userInfo[1]);
            }
            else{
                dutyNumber=0;
                isOnduty=0;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView(){
        setTitle("值班签到");

        Btn_OnDuty_Start=findViewById(R.id.Btn_OnDuty_Start);
    }

    private void initEvent(){
        Btn_OnDuty_Start.setOnClickListener(this);
    }


    //地图的生命周期管理
    @Override
    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

}

