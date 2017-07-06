package com.cx.waixiuapp;

import com.cx.myobject.MyFinishObject;
import com.cx.myobject.MyNoteShowDialog;
import com.cx.myobject.MyTaskObject;
import com.cx.util.MUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WaixiuFinishDetailActivity extends Activity {
	MyFinishObject selectTask;
	
	TextView reportNo,carNo,carType,isTheCar,biaodiCarNo,chaijiandepart,chaijianconnector,
	chaijiandianphone,dingsuner,dingsunerCall,yardtime,canRepair,waixiudate,waixiudepart,peijianname,
	peijianPrice,waixiuPrice;
	//TextView notTv;
	//TextView gusunPrice;
	TextView finishDetailBackTv;
	Button waixiulookBtn;
	MyNoteShowDialog mNoteShowDialog;
	String canRepairStr="2";//默认不可修
	String noteStr="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waixiufinishtask_detail);
		selectTask=(MyFinishObject)this.getIntent().getSerializableExtra("selectFinishTask");
		initView();
	}
public void initView(){
	finishDetailBackTv=(TextView)findViewById(R.id.finishback_tv);
	finishDetailBackTv.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			WaixiuFinishDetailActivity.this.finish();
		}
		
	});
	
	
	reportNo=(TextView)findViewById(R.id.waixiufinish_reportNo_tv);
	carNo=(TextView)findViewById(R.id.waixiufinish_carNo_tv);
	carType=(TextView)findViewById(R.id.waixiufinish_cartypeNo_tv);
	isTheCar=(TextView)findViewById(R.id.waixiufinish_isTheCar_tv);
	biaodiCarNo=(TextView)findViewById(R.id.waixiufinish_theCarNo_tv);
	chaijiandepart=(TextView)findViewById(R.id.waixiufinish_reportDeport_tv);
	chaijianconnector=(TextView)findViewById(R.id.waixiufinish_deportConnector_tv);
	chaijiandianphone=(TextView)findViewById(R.id.waixiufinish_deportConnectorphone_tv);
	dingsuner=(TextView)findViewById(R.id.waixiufinish_dingsuner_tv);
	dingsunerCall=(TextView)findViewById(R.id.waixiufinish_dingsunerphone_tv);
	yardtime=(TextView)findViewById(R.id.waixiufinish_fenpeitime_tv);
	canRepair=(TextView)findViewById(R.id.waixiufinish_isCanRepair_tv);//是否可修
	waixiudate=(TextView)findViewById(R.id.waixiufinish_waixiuDate_tv);
	waixiudepart=(TextView)findViewById(R.id.waixiufinish_waixiudepart_tv);
	peijianname=(TextView)findViewById(R.id.waixiufinish_peijianname_tv);
	peijianPrice=(TextView)findViewById(R.id.waixiufinish_peijianprice_tv);
	waixiuPrice=(TextView)findViewById(R.id.waixiufinish_xiufuprice_tv);
//	gusunPrice=(TextView)findViewById(R.id.waixiufinish_gusunmoney_tv);
	//notTv=(TextView)findViewById(R.id.waixiufinish_note_tv);
	waixiulookBtn=(Button)findViewById(R.id.waixiuw_look_btn);
	
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
	String yardtimeStr=selectTask.getYard_time();
	 canRepairStr=selectTask.getCanRepair();
	String waixiudateStr=selectTask.getWaixiu_time();
	String waixiudepartStr=selectTask.getWaixiu_depart();
	String peijiannameStr=selectTask.getWaixu_peijian();
	String peijianPriceStr=selectTask.getPeijian_price();
	String waixiuPriceStr=selectTask.getWaixiu_price();
	String gusunPriceStr=selectTask.getAbout_price();
	
	noteStr=selectTask.getNote();
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
	String yardtimeSDF=MUtil.getStrTime(yardtimeStr);
	yardtime.setText(yardtimeSDF);
	String canRepairResult="不可修";
	if(canRepairStr.equals("1")){
		canRepairResult="可修";
	}
	canRepair.setText(canRepairResult);
	String waixiuDateSDF=MUtil.getStrTime(waixiudateStr);
	waixiudate.setText(waixiuDateSDF);
	waixiudepart.setText(waixiudepartStr);
	peijianname.setText(peijiannameStr);
	peijianPrice.setText(peijianPriceStr);
	waixiuPrice.setText(waixiuPriceStr);
	//gusunPrice.setText(gusunPriceStr);
	//notTv.setText(noteStr);
	waixiulookBtn.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			showNoteDialog(noteStr);
		}
		
	});
}
public void showNoteDialog(String waixiuRemark){
	TextView noteTv;
	mNoteShowDialog=new MyNoteShowDialog(WaixiuFinishDetailActivity.this,R.style.Dialog);
	mNoteShowDialog.setMyNoteShowDialog();
	String titleStr="备注";
	mNoteShowDialog.setMyTitle(titleStr);;
	noteTv=(TextView)mNoteShowDialog.getNoteText();
	noteTv.setText(waixiuRemark);
	mNoteShowDialog.setCloseBtn(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			if(mNoteShowDialog!=null&&mNoteShowDialog.isShowing()){
				mNoteShowDialog.dismiss();
				mNoteShowDialog=null;
			}	
		}
		
	});
	mNoteShowDialog.show();
}
}
