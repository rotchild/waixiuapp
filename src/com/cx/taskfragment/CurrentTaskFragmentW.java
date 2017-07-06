package com.cx.taskfragment;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cx.myobject.MHttpParams;
import com.cx.myobject.MHttpStorage;
import com.cx.myobject.MyTaskObject;
import com.cx.myobject.TaskRecordList;
import com.cx.myobject.TaskWRecordAdapter;
import com.cx.util.MUtil;
import com.cx.waixiuapp.R;
import com.cx.waixiuapp.WaixiuDetailActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class CurrentTaskFragmentW extends Fragment{
	PullToRefreshListView currentTasklv;
	TaskWRecordAdapter taskRecordAdapter;
	private static String TAG="currentTaskFrag";
	private int start=0;
	public int limit=5;
	SharedPreferences currentTaskSP;
	public static final String SPName="WaiXiuAppSP";
	ProgressDialog currentTaskPd;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onActivityCreated(savedInstanceState);
		currentTaskSP=getActivity().getSharedPreferences(SPName,0);
		if(TaskRecordList.mTaskList==null){
			TaskRecordList.mTaskList=new ArrayList<MyTaskObject>();
		//	TaskRecordList.mTaskList.add(mTaskObject);
		}
		
		/*final MyTaskObject mTaskObject=new MyTaskObject("201702121111","33","��A SQ880","�µ�","��","��A SQ880"
				,"��","����test","cjdren","123456789","dsren",
				"12354566","1490691230575","1490691230575","waixiubu1","peijian1","100","200","1000");*/
		currentTasklv=(PullToRefreshListView)getView().findViewById(R.id.lv_current);
		currentTasklv.setMode(Mode.BOTH);
		//TaskRecordList.mTaskList.add(mTaskObject);
		taskRecordAdapter=new TaskWRecordAdapter(this.getActivity(),R.layout.taskw_item2_layout,TaskRecordList.mTaskList);
		currentTasklv.setAdapter(taskRecordAdapter);
		
		currentTasklv.setOnRefreshListener(new OnRefreshListener<ListView>(){

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO �Զ����ɵķ������
				limit=limit+5;
				getCurrentTask();
				
			}
			
		});
		
		
		currentTasklv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO �Զ����ɵķ������
				Log.d(TAG, "position"+position);
				MyTaskObject selectTaskObject=TaskRecordList.mTaskList.get(position-1);
				Bundle bundle=new Bundle();
				if(selectTaskObject!=null){
					bundle.putSerializable("selectTask", selectTaskObject);	
				}
				Intent toWaiXiuDetail=new Intent(getActivity(),WaixiuDetailActivity.class);
				toWaiXiuDetail.putExtras(bundle);
				int requestCode=3;
				startActivityForResult(toWaiXiuDetail,requestCode);
			}
			
		});
		getCurrentTask();
		
	}
	
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		return inflater.inflate(R.layout.current_taskfragment, container,false);
	}




	@Override
	public void onAttach(Activity activity) {
		// TODO �Զ����ɵķ������
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// TODO �Զ����ɵķ������
		super.onDestroy();
	}

	@Override
	public void onPause() {
		// TODO �Զ����ɵķ������
		super.onPause();
	}
	
	

	public void getCurrentTask(){
		currentTaskPd=ProgressDialog.show(getActivity(), "������", "���Ժ�...");
		AsyncHttpClient asyncHttpClient=new AsyncHttpClient();
		asyncHttpClient.addHeader("Charset", MHttpParams.DEFAULT_CHARSET);
		asyncHttpClient.setTimeout(MHttpParams.DEFAULT_TIME_OUT);
		String dUrl=MHttpParams.IP;
        String mUrl=currentTaskSP.getString("IP", dUrl);
        String dPort=MHttpParams.DEFAULT_PORT;
        String mPort=currentTaskSP.getString("Port", dPort);
		String getCurrentUrl="http://"+mUrl+":"+mPort+"/"+MHttpParams.CurrentTaskUrlW;
		RequestParams params=new RequestParams();
		int user_id=currentTaskSP.getInt("id", -1);
		params.put("start", String.valueOf(start));
		params.put("limit", String.valueOf(limit));
		params.put("user_id",String.valueOf(user_id));
		asyncHttpClient.post(getCurrentUrl, params, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				// TODO �Զ����ɵķ������
				super.onSuccess(statusCode, response);
				if(currentTaskPd!=null&&currentTaskPd.isShowing()){
					currentTaskPd.dismiss();
					currentTaskPd=null;
				}
				boolean success=false;
				try{
					success=response.getBoolean("success");
					if(success){
						JSONArray data=response.getJSONArray("data");
						TaskRecordList.mTaskList.clear();
						Log.d(TAG, "mTaskList"+TaskRecordList.mTaskList.size());
						if(data.length()>0){
							for(int i=0;i<data.length();i++){
								JSONObject mData=data.getJSONObject(i);
								String case_no=mData.getString("case_NO");
								int case_idInt=mData.getInt("case_id");
								String case_id=String.valueOf(case_idInt);
								MHttpStorage.case_id=case_id;
								String car_no=mData.getString("car_NO");
								String brand_name=mData.getString("brand_name");
								int is_target=mData.getInt("is_tartget");
								String i_targetStr="��";
								if(is_target==1){
									i_targetStr="��";
								}
								
								String target_no=mData.getString("target_NO");
								String is_vipStr="";
								int is_vip=mData.getInt("isvip");
								if(is_vip==1){
									is_vipStr="VIP";
								}
								
								String parters_name=mData.getString("parters_name");
								String parter_manager=mData.getString("parter_manager");
								String parter_mobile=mData.getString("parter_mobile");
								//String dingsuner_name=//����Ա����
								//String dingusner_mobile
								String yard_timeStamp=mData.getString("yard_time");//����mytaskobject ʹ��stamp
								//String canrepair
								String yard_time=MUtil.getDetailTime(yard_timeStamp);
								String repair_time=mData.getString("repair_time");
								String repair_factoryname=mData.getString("repair_factoryname");
								String repair_parts=mData.getString("repair_parts");
								double parts_priceD=mData.getDouble("parts_price");
								double repair_priceD=mData.getDouble("repair_price");
								double loss_priceD=mData.getDouble("loss_price");
								String parts_price=String.valueOf(parts_priceD);
								parts_price=mData.getString("parts_price");
								
								String repair_price=String.valueOf(repair_priceD);
								repair_price=mData.getString("repair_price");
								
								String loss_price=String.valueOf(loss_priceD);
								loss_price=mData.getString("loss_price");
								
								String repair_remark=mData.getString("repair_remark");
								String decider_name=mData.getString("decide_name");
								String decider_tel=mData.getString("decide_telephone");
								MyTaskObject mTaskObject=new MyTaskObject(case_no,case_id,car_no,brand_name,i_targetStr,target_no
										,is_vipStr,parters_name,parter_manager,parter_mobile,decider_name,
										decider_tel,yard_time,repair_time,repair_factoryname,repair_parts,parts_price,repair_price,repair_remark);
								TaskRecordList.mTaskList.add(mTaskObject);
							}
							taskRecordAdapter.notifyDataSetChanged();
							currentTasklv.onRefreshComplete();
						}
					}else{
						Toast.makeText(getActivity(), "����ʧ��", Toast.LENGTH_SHORT).show();//success=false
						currentTasklv.onRefreshComplete();
					}
				}catch(JSONException e){
					e.printStackTrace();
				}finally{
					currentTasklv.onRefreshComplete();
				}
			}

			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
				// TODO �Զ����ɵķ������
				super.onFailure(e, errorResponse);
				if(currentTaskPd!=null&&currentTaskPd.isShowing()){
					currentTaskPd.dismiss();
					currentTaskPd=null;
				}
				currentTasklv.onRefreshComplete();
			}
			
			@Override
			public void onFailure(Throwable e, String errorResponse) {
				// TODO �Զ����ɵķ������
				super.onFailure(e, errorResponse);
				
				if(currentTaskPd!=null&&currentTaskPd.isShowing()){
					currentTaskPd.dismiss();
					currentTaskPd=null;
				}
				currentTasklv.onRefreshComplete();
			}
			
		});
		
	}




	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO �Զ����ɵķ������
		super.onActivityResult(requestCode, resultCode, data);
		switch(resultCode){
		case 3://����ʱˢ��
			
			getCurrentTask();//����״̬��getCurrent�Զ�����
			taskRecordAdapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
	}
	
}
