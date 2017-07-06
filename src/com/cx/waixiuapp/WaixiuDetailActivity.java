package com.cx.waixiuapp;

import org.json.JSONException;
import org.json.JSONObject;

import com.cx.myobject.MHttpParams;
import com.cx.myobject.MHttpStorage;
import com.cx.myobject.MyNoteDialog;
import com.cx.myobject.MyTaskObject;
import com.cx.util.MUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class WaixiuDetailActivity extends Activity {
MyTaskObject selectTask;
String case_id;
//Button toFinishBtn;
TextView reportNo,carNo,carType,isTheCar,biaodiCarNo,chaijiandepart,chaijianconnector,
chaijiandianphone,dingsuner,dingsunerCall,yardtime,waixiudate,waixiudepart,peijianname,
peijianPrice,waixiuPrice,gusunPrice;
//EditText notET;
Button canRepairBtn,cannotRepairBtn;
EditText dialogEt;
TextView waixiuDetailTv;
//RadioGroup canRepairGP;
public static final String SPName="WaiXiuAppSP";
SharedPreferences waixiuDetailSP;
int repair_state=2;//1可修,2不可修
MyNoteDialog mNoteDialog;

String dialogNoteStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waixiutask_detail);
		selectTask=(MyTaskObject)this.getIntent().getSerializableExtra("selectTask");
		initView();
	}
public void initView(){
//	canRepairGP=(RadioGroup)findViewById(R.id.canrepair_gp);
	
/*	canRepairGP.setOnCheckedChangeListener(new OnCheckedChangeListener(){

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO 自动生成的方法存根
			int radioBtnId=group.getCheckedRadioButtonId();
			RadioButton sb=(RadioButton)WaixiuDetailActivity.this.findViewById(radioBtnId);
			String sbStr=sb.getText().toString();
			if(sbStr.equals("可修")){
				repair_state=1;
			}
		}
		
	});*/
	waixiuDetailTv=(TextView)findViewById(R.id.waixiudetail_back);
	waixiuDetailTv.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			WaixiuDetailActivity.this.finish();
		}
		
	});
	
	canRepairBtn=(Button)findViewById(R.id.can_repair_btn);
	canRepairBtn.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			repair_state=1;
			showNoteDialog();
		}
		
		
	});
	
	cannotRepairBtn=(Button)findViewById(R.id.cannot_repair_btn);
	cannotRepairBtn.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			repair_state=2;
			showNoteDialog();
		}
		
		
	});
	
	
	
	waixiuDetailSP=getSharedPreferences(SPName,0);
	
	//toFinishBtn=(Button)findViewById(R.id.finishbtn);
	
/*	toFinishBtn.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			toFinishTask();
		}
		
	});*/
	
	reportNo=(TextView)findViewById(R.id.waixiu_reportNo_tv);
	carNo=(TextView)findViewById(R.id.waixiu_carNo_tv);
	carType=(TextView)findViewById(R.id.waixiu_cartypeNo_tv);
	isTheCar=(TextView)findViewById(R.id.waixiu_isTheCar_tv);
	biaodiCarNo=(TextView)findViewById(R.id.waixiu_theCarNo_tv);
	chaijiandepart=(TextView)findViewById(R.id.waixiu_reportDeport_tv);
	chaijianconnector=(TextView)findViewById(R.id.waixiu_deportConnector_tv);
	chaijiandianphone=(TextView)findViewById(R.id.waixiu_deportConnectorphone_tv);
	dingsuner=(TextView)findViewById(R.id.waixiu_dingsuner_tv);
	dingsunerCall=(TextView)findViewById(R.id.waixiu_dingsunerphone_tv);
	yardtime=(TextView)findViewById(R.id.waixiu_fenpeitime_tv);
	//canRepair=(TextView)findViewById(R.id.waixiu_isCanRepair_tv);//是否可修
	waixiudate=(TextView)findViewById(R.id.waixiu_waixiuDate_tv);
	waixiudepart=(TextView)findViewById(R.id.waixiu_waixiudepart_tv);
	peijianname=(TextView)findViewById(R.id.waixiu_peijianname_tv);
	peijianPrice=(TextView)findViewById(R.id.waixiu_peijianprice_tv);
	waixiuPrice=(TextView)findViewById(R.id.waixiu_xiufuprice_tv);
	gusunPrice=(TextView)findViewById(R.id.waixiu_gusunmoney_tv);
	//notET=(EditText)findViewById(R.id.noteedit_et);
	
	String reportNoStr=selectTask.getCase_No();
	String carNoStr=selectTask.getCar_No();
	String carTypeStr=selectTask.getBrand_name();
	String isTheCarStr=selectTask.getIs_target();
	String biaodiCarNoStr=selectTask.getTarget_No();
	String chaijiandepartStr=selectTask.getParters_name();
	String chaijianconnectorStr=selectTask.getParter_manager();
	String chaijiandianphoneStr=selectTask.getParter_mobile();
	String dingsunerStr=selectTask.getDingsuner_name();
	String dingsunerCallStr=selectTask.getDingsuner_mobile();
	String yardtimeDetailStr=selectTask.getYard_time();//2017/04/25 11:04
	String[] strGroup=yardtimeDetailStr.split(" ");
	String yardtimeStr=strGroup[0];
	//String canRepair=selectTask.ge
	String waixiudateStampStr=selectTask.getWaixiu_time();
	String waixiudateStr=MUtil.getStrTime(waixiudateStampStr);
	String waixiudepartStr=selectTask.getWaixiu_depart();
	String peijiannameStr=selectTask.getWaixu_peijian();
	String peijianPriceStr=selectTask.getPeijian_price();
	String waixiuPriceStr=selectTask.getWaixiu_price();
	String gusunPriceStr=selectTask.getAbout_price();//备注
	//String notTv=selectTask
	
	case_id=selectTask.getCase_id();
	
	reportNo.setText(reportNoStr);
	carNo.setText(carNoStr);
	carType.setText(carTypeStr);
	isTheCar.setText(isTheCarStr);
	biaodiCarNo.setText(biaodiCarNoStr);
	chaijiandepart.setText(chaijiandepartStr);
	chaijianconnector.setText(chaijianconnectorStr);
	chaijiandianphone.setText(chaijiandianphoneStr);
	dingsuner.setText(dingsunerStr);
	dingsunerCall.setText(dingsunerCallStr);
	yardtime.setText(yardtimeStr);
	//canRepair.setText(canRepairStr);
	waixiudate.setText(waixiudateStr);
	waixiudepart.setText(waixiudepartStr);
	peijianname.setText(peijiannameStr);
	peijianPrice.setText(peijianPriceStr);
	waixiuPrice.setText(waixiuPriceStr);
	gusunPrice.setText(gusunPriceStr);
	
}

public void toFinishTask(){
	AsyncHttpClient asyncHttpClient=new AsyncHttpClient();
	asyncHttpClient.addHeader("Charset", MHttpParams.DEFAULT_CHARSET);
	asyncHttpClient.setTimeout(MHttpParams.DEFAULT_TIME_OUT);
	String dUrl=MHttpParams.IP;
	String mUrl=waixiuDetailSP.getString("IP", dUrl);
	String dPort=MHttpParams.DEFAULT_PORT;
	String mPort=waixiuDetailSP.getString("Port", dPort);
	String toFinishUrl="http://"+mUrl+":"+mPort+"/"+MHttpParams.ToFinishTaskUrlW;
	RequestParams params=new RequestParams();
	int user_id=waixiuDetailSP.getInt("id", 0);
	//String case_id=MHttpStorage.case_id;
	if(case_id.equals("")){
		case_id="-1";
	}
	
	//String repair_remark="";
	//repair_remark=notET.getText().toString();
	params.put("user_id", String.valueOf(user_id));
	params.put("case_id", case_id);
	params.put("repair_remark", dialogNoteStr);
	params.put("repair_state", String.valueOf(repair_state));
	asyncHttpClient.post(toFinishUrl, params, new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(int statusCode, JSONObject response) {
			// TODO 自动生成的方法存根
			super.onSuccess(statusCode, response);
			boolean success=false;
			try{
				success=response.getBoolean("success");
				if(success){
					Toast.makeText(WaixiuDetailActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
					int resultCode=3;
					WaixiuDetailActivity.this.setResult(resultCode);
					WaixiuDetailActivity.this.finish();
				}else{
					
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
		}
		
	});
}

public void showNoteDialog(){
	mNoteDialog=new MyNoteDialog(WaixiuDetailActivity.this,R.style.Dialog);
	mNoteDialog.setDialog();
	String dialogTitle="";
	if(repair_state==1){
		dialogTitle="备注-可修";
	}else if(repair_state==2){
		dialogTitle="备注-不可修";
	}
	mNoteDialog.setTitle(dialogTitle);
	dialogEt=(EditText) mNoteDialog.getEditText();
	
	mNoteDialog.setSaveBtn(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			dialogNoteStr=dialogEt.getText().toString();
			toFinishTask();
		}
		
	});
	mNoteDialog.setCancelBtn(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm.isActive()){
                 imm.hideSoftInputFromWindow(mNoteDialog.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
			if(mNoteDialog!=null&&mNoteDialog.isShowing()){
				mNoteDialog.dismiss();
				mNoteDialog=null;
			}

	
		}
		
	});
	mNoteDialog.show();
}

}
