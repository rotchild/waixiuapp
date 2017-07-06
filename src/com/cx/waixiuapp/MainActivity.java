package com.cx.waixiuapp;


import org.json.JSONException;
import org.json.JSONObject;

import com.cx.myobject.MD5Util;
import com.cx.myobject.MHttpParams;
import com.cx.myobject.MyAlertDialog;
import com.cx.myobject.MyPushReceiver;
import com.cx.set.IPSetActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.android.tpush.XGBasicPushNotificationBuilder;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushNotificationBuilder;
import com.tencent.android.tpush.service.XGPushService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;


public class MainActivity extends Activity {
Button loginBtn,ipsetBtn;
EditText inputUsername,inputPassword;
SharedPreferences mainActSP;
CheckBox isRemember;

ProgressDialog pd;
public XGPushNotificationBuilder xgNotificationBuilder;
public static final int notificationBuilderId=1;
public static String TAG="waixiu";
public static final String SPName="WaiXiuAppSP";

String tokenConfig="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mainActSP=getSharedPreferences(SPName,0);
		int loginState=mainActSP.getInt("state", 0);
		if(loginState==1){
			Intent tocurrent=new Intent(MainActivity.this,currentTaskActivity.class);
			startActivity(tocurrent);
			MainActivity.this.finish();
		}else{
			setContentView(R.layout.activity_main);
			initView();
		}
		
	}
public void initView(){
	xgNotificationBuilder=new XGBasicPushNotificationBuilder();
	isRemember=(CheckBox)findViewById(R.id.remember_password);
	
	isRemember.setOnCheckedChangeListener(new OnCheckedChangeListener(){

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO 自动生成的方法存根
			
		}
		
	});
	
	mainActSP=getSharedPreferences(SPName,0);
	inputUsername=(EditText)findViewById(R.id.username_et);
	inputPassword=(EditText)findViewById(R.id.password_et);
	String username=mainActSP.getString("userNameStr", "");
	inputUsername.setText(username);

	int checkState=mainActSP.getInt("checkstate", 0);
	if(checkState==0){
		isRemember.setChecked(false);
	}else{
		isRemember.setChecked(true);
		String passwordPre=mainActSP.getString("passwordStr", "");
		inputPassword.setText(passwordPre);
	}
	loginBtn=(Button)findViewById(R.id.login_btn);
	ipsetBtn=(Button)findViewById(R.id.ipset_btn);
	
	loginBtn.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			String userNameStr=inputUsername.getText().toString();
			String passwordStr=inputPassword.getText().toString();
			if(userNameStr!=null&&userNameStr.length()>0&&passwordStr!=null&&passwordStr.length()>0){
				tokenConfig=XGPushConfig.getToken(getApplicationContext());
				int length=tokenConfig.length();
				Log.d(TAG, "token"+tokenConfig+"tokenLen"+tokenConfig.length());
				if(tokenConfig==null||tokenConfig.length()<=0){
					//提示框是否继续登录
					showAlertDialog(userNameStr,passwordStr);
				}else{
					loginByHttp(userNameStr,passwordStr);
				}
				
			}else{
				Toast.makeText(MainActivity.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
			}


			
			
			/*Intent toCurrentTask=new Intent(MainActivity.this,currentTaskActivity.class);
			startActivity(toCurrentTask);*/
			
		}
		
	});
	
	ipsetBtn.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			Intent toIPset=new Intent(MainActivity.this,IPSetActivity.class);
			startActivity(toIPset);
		}
		
	});
	
}

public void showAlertDialog(final String usernameStr,final String passwordStr){
	final MyAlertDialog mAlertDialog=new MyAlertDialog(MainActivity.this,R.style.Dialog);
	mAlertDialog.setMyAlertDialog();
	mAlertDialog.setOnPositiveListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			loginByHttp(usernameStr,passwordStr);
		}
		
	});
	mAlertDialog.setOnNegativeListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			if(mAlertDialog!=null&&mAlertDialog.isShowing()){
				mAlertDialog.dismiss();
			}
		}
		
	});
	mAlertDialog.show();
}

public void loginByHttp(final String username,final String password){
	pd=ProgressDialog.show(MainActivity.this, "请等待", "加载中");
	String passwordMD5Str=MD5Util.MD5(password);
	AsyncHttpClient asyncHttpClient=new AsyncHttpClient();
	asyncHttpClient.addHeader("Charset", MHttpParams.DEFAULT_CHARSET);
	asyncHttpClient.setTimeout(MHttpParams.DEFAULT_TIME_OUT);
	String dmUrl=MHttpParams.IP;
	String mUrl=mainActSP.getString("IP", dmUrl);
	String dPort=MHttpParams.DEFAULT_PORT;
	String mPort=mainActSP.getString("Port", dPort);
	String loginUrl="http://"+mUrl+":"+mPort+"/"+MHttpParams.LoginBUrlW;
	


	RequestParams params=new RequestParams();
	params.put("username", username);
	params.put("password", passwordMD5Str);
	params.put("token", tokenConfig);
	asyncHttpClient.post(loginUrl, params, new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(int statusCode, JSONObject response) {
			// TODO 自动生成的方法存根
			super.onSuccess(statusCode, response);
			boolean success=false;
			try{
				success=response.getBoolean("success");
				if(success){
					if(pd!=null){
						pd.dismiss();
						pd=null;
					}
					SharedPreferences.Editor rememberPSEditor=mainActSP.edit();
					rememberPSEditor.putString("userNameStr", username);
					if(isRemember.isChecked()){
						rememberPSEditor.putString("passwordStr", password);
						rememberPSEditor.putInt("checkstate", 1);
					}else{
						rememberPSEditor.putString("passwordStr", "");
						rememberPSEditor.putInt("checkstate", 0);
					}
					rememberPSEditor.commit();
					
					JSONObject data=response.getJSONObject("data");
					if(data!=null){
						SharedPreferences.Editor mainActEditor=mainActSP.edit();
						int id=data.getInt("id");
						mainActEditor.putInt("id", id);
						mainActEditor.putInt("state", 1);
						mainActEditor.commit();
						
						MyPushReceiver myPushReceiver=new MyPushReceiver();
						IntentFilter myFilter=new IntentFilter();
						myFilter.addAction("com.tencent.android.tpush.action.PUSH_MESSAGE");
						myFilter.addAction("com.tencent.android.tpush.action.FEEDBACK");
						registerReceiver(myPushReceiver,myFilter);//unregister????
						
						Context context = getApplicationContext();
						XGPushManager.registerPush(context);
						XGPushManager.setPushNotificationBuilder(context, notificationBuilderId, xgNotificationBuilder); 
						Intent service = new Intent(context, XGPushService.class);
						context.startService(service);

						
						Intent toCurrentTask=new Intent(MainActivity.this,currentTaskActivity.class);
						startActivity(toCurrentTask);
						MainActivity.this.finish();
					}
					
					
				}else{
					if(pd!=null){
						pd.dismiss();
						pd=null;
					}
					Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
					
				}
			}catch(JSONException e){
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(Throwable e, JSONObject errorResponse) {
			// TODO 自动生成的方法存根
			super.onFailure(e, errorResponse);
		}
		
		@Override
		public void onFailure(Throwable e, String errorResponse) {
			// TODO 自动生成的方法存根
			super.onFailure(e, errorResponse);
			if(pd!=null){
				pd.dismiss();
				pd=null;
			}
		}
		
	});
	
}
}
