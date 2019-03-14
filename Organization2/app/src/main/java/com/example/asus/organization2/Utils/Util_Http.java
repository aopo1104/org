package com.example.asus.organization2.Utils;

import android.content.Context;
import android.util.Log;


import com.example.asus.organization2.APP;
import com.example.asus.organization2.Message.Message_Local;
import com.example.asus.organization2.Mod.EBmessage;
import com.example.asus.organization2.Mod.TokenMod;
import com.google.gson.Gson;

import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Random;

import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;


public class Util_Http {

	private static final String RY_APP_KEY = "n19jmcy5n89i9";
	private static final String RY_APP_SECRET = "0JthXWQxNSv";

	public static RequestParams addHeader(RequestParams params) {
		Random r = new Random();
		String Nonce = (r.nextInt(10000) + 10000) + "";		//获取随机数
		String Timestamp = (System.currentTimeMillis() / 1000) + "";	//获取时间戳
		params.addHeader("App-Key", RY_APP_KEY);
		params.addHeader("Nonce", Nonce);
		params.addHeader("Timestamp", Timestamp);
		params.addHeader("Signature",
				Util_MD5.encryptToSHA(RY_APP_SECRET + Nonce + Timestamp));

		return params;
	}

	public static void getToken(final String id, final String username,final String portraitUri) {
		RequestParams params = new RequestParams(
				"https://api.cn.ronghub.com/user/getToken.json");
		addHeader(params);
		params.addBodyParameter("userId", id);
		params.addBodyParameter("name", username);
		params.addBodyParameter("portraitUri",portraitUri);
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				EBmessage eb = new EBmessage();
				eb.setStatus(false);
				eb.setMessage(arg0.toString());
				eb.setFrom("getToken");
				EventBus.getDefault().post(eb);
			}

			@Override
			public void onFinished() {
			}

			@Override
			public void onSuccess(String s) {
				TokenMod mod = new Gson().fromJson(s, TokenMod.class);
				Message_Local.myToken=mod.getToken();

				EBmessage eb = new EBmessage();
				eb.setStatus(true);
				eb.setMessage(mod.getToken());
				eb.setFrom("getToken");
				EventBus.getDefault().post(eb);
			}
		});
	}

	/**
	 * ���������Ʒ�����������
	 * 
	 * @param token
	 */
	public static void connect(String token, Context context) {

		if (context.getApplicationInfo().packageName.equals(APP
				.getCurProcessName(context.getApplicationContext()))) {

			RongIM.connect(token, new RongIMClient.ConnectCallback() {

				@Override
				public void onTokenIncorrect() {
					Log.d("LoginActivity", "--onTokenIncorrect");
				}

				@Override
				public void onSuccess(String userid) {
					EBmessage eb = new EBmessage();
					eb.setStatus(true);
					eb.setMessage("success");
					eb.setFrom("connect");
					EventBus.getDefault().post(eb);
					Log.e("LoginActivity", "--onSuccess" + userid);
				}

				/**
				 * ��������ʧ��
				 * 
				 * @param errorCode
				 *            �����룬�ɵ����� �鿴�������Ӧ��ע��
				 */
				@Override
				public void onError(RongIMClient.ErrorCode errorCode) {
					Log.e("LoginActivity", "--onError" + errorCode);
				}
			});
		}
	}
}
