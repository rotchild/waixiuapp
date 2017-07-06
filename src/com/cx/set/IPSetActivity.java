package com.cx.set;

import com.cx.myobject.MHttpParams;
import com.cx.util.MRegex;
import com.cx.waixiuapp.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class IPSetActivity extends Activity {
EditText ipSetEt,portSetEt;
Button okBtn;
ImageView back_iv;
SharedPreferences ipSetSP;
public static final String SPName="WaiXiuAppSP";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ipset_layout);
		initView();
	}
public void initView(){
	back_iv=(ImageView)findViewById(R.id.ipset_back);
	back_iv.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			IPSetActivity.this.finish();
		}
		
	});
	
	ipSetSP=getSharedPreferences(SPName,0);
	ipSetEt=(EditText)findViewById(R.id.ipset_et);
	portSetEt=(EditText)findViewById(R.id.portset_et);
	String ipPre=ipSetSP.getString("IP", MHttpParams.IP);
	String portPre=ipSetSP.getString("Port", MHttpParams.DEFAULT_PORT);
	
	ipSetEt.setText(ipPre);
	portSetEt.setText(portPre);
	
/*	exitBtn=(Button)findViewById(R.id.setexit_btn);
	exitBtn.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			IPSetActivity.this.finish();
		}
		
	});*/
	
	okBtn=(Button)findViewById(R.id.ipsetOk_btn);
	okBtn.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			if(ipSetEt==null||ipSetEt.length()<=0||portSetEt==null||portSetEt.length()<=0){
				Toast.makeText(IPSetActivity.this, "IP或端口设置不能为空", Toast.LENGTH_SHORT).show();
			}else{
				String ipsetStr=ipSetEt.getText().toString();
				String portsetStr=portSetEt.getText().toString();
				if(MRegex.isIPV4(ipsetStr)){
					if(MRegex.isPort(portsetStr)){
						SharedPreferences.Editor ipsetEditor=ipSetSP.edit();
						ipsetEditor.putString("IP", ipsetStr);
						ipsetEditor.putString("Port", portsetStr);
						boolean result=ipsetEditor.commit();
						if(result){
							Toast.makeText(IPSetActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
							IPSetActivity.this.finish();
						}	
					}else{
						Toast.makeText(IPSetActivity.this, "端口格式错误", Toast.LENGTH_SHORT).show();	
					}
				}else{
					Toast.makeText(IPSetActivity.this, "IP格式错误", Toast.LENGTH_SHORT).show();	
				}
				
			}

		}
		
	});
}
}
