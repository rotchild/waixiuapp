package com.cx.myobject;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

public class MyPushReceiver extends XGPushBaseReceiver {

	@Override
	public void onDeleteTagResult(Context arg0, int arg1, String arg2) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void onNotifactionClickedResult(Context context,
			XGPushClickedResult clickResult) {
		// TODO �Զ����ɵķ������
		Intent clickIntent=new Intent();
		clickIntent.setComponent(new ComponentName("com.cx.waixiuapp","com.cx.waixiuapp.currentTaskActivity"));
		context.getApplicationContext().startActivity(clickIntent);
	}

	@Override
	public void onNotifactionShowedResult(Context arg0, XGPushShowedResult arg1) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void onRegisterResult(Context arg0, int arg1,
			XGPushRegisterResult arg2) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void onSetTagResult(Context arg0, int arg1, String arg2) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void onTextMessage(Context arg0, XGPushTextMessage arg1) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void onUnregisterResult(Context arg0, int arg1) {
		// TODO �Զ����ɵķ������
		
	}

}
