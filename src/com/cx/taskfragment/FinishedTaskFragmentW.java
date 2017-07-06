		package com.cx.taskfragment;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cx.myobject.MHttpParams;
import com.cx.myobject.MHttpStorage;
import com.cx.myobject.MyFinishObjAdapter;
import com.cx.myobject.MyFinishObject;
import com.cx.myobject.MyTaskObject;
import com.cx.myobject.TaskRecordList;
import com.cx.myobject.TaskWRecordAdapter;
import com.cx.util.MUtil;
import com.cx.waixiuapp.R;
import com.cx.waixiuapp.WaixiuFinishDetailActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class FinishedTaskFragmentW extends Fragment{
	PullToRefreshListView finishTasklv;
	MyFinishObjAdapter mFinishObjAdapter;
	List<MyFinishObject> mList;
	private static String TAG="finishTaskFrag";
	private int start=0;
	private int limit=20;
	SharedPreferences finishTaskSP;
	public static final String SPName="WaiXiuAppSP";
	ProgressDialog finishTaskPd;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onActivityCreated(savedInstanceState);
		finishTaskSP=getActivity().getSharedPreferences(SPName, 0);
	    mList=new ArrayList<MyFinishObject>();
	/*	MyFinishObject mFObj=new MyFinishObject("11111","鄂A11111", "大众",
				"是", "鄂A11111", "拆检点1",
				"cjdcx","15111333", "dscx",
				"151113335455", "1492592949207","是",
				"1492572775562", "外修单位", "配件1",
				"100","300","1000","test");*/
		//mList.add(mFObj);
	mFinishObjAdapter=new MyFinishObjAdapter(this.getActivity(),R.layout.taskw_item2_layout,mList);
	finishTasklv=(PullToRefreshListView)getView().findViewById(R.id.finishlv_current);
	finishTasklv.setAdapter(mFinishObjAdapter);
	finishTasklv.setMode(Mode.BOTH);
	finishTasklv.setOnItemClickListener(new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO 自动生成的方法存根
			//selectFinishTask
			MyFinishObject selectFinishObj=mList.get(position-1);
			Bundle bundle=new Bundle();
			if(selectFinishObj!=null){
				bundle.putSerializable("selectFinishTask", selectFinishObj);	
			}
			Intent toFinshDetail=new Intent(getActivity(),WaixiuFinishDetailActivity.class);
			toFinshDetail.putExtras(bundle);
			startActivity(toFinshDetail);
		}
		
	});
	
	finishTasklv.setOnRefreshListener(new OnRefreshListener(){

		@Override
		public void onRefresh(PullToRefreshBase refreshView) {
			// TODO 自动生成的方法存根
			limit=limit+15;
			getFinishTaskByHttp();//已有clear
		}
		
	});
	
	}
	
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		return inflater.inflate(R.layout.finished_taskfragment, container,false);
	}




	@Override
	public void onAttach(Activity activity) {
		// TODO 自动生成的方法存根
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
	}

	@Override
	public void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
	}

	public void getFinishTaskByHttp(){
		
		finishTaskPd=ProgressDialog.show(getActivity(), "加载中...", "请稍后");
		AsyncHttpClient asyncHttpClient=new AsyncHttpClient();
		asyncHttpClient.addHeader("Charset", MHttpParams.DEFAULT_CHARSET);
		asyncHttpClient.setTimeout(MHttpParams.DEFAULT_TIME_OUT);
		String dUrl=MHttpParams.IP;
        String mUrl=finishTaskSP.getString("IP", dUrl);
        String dPort=MHttpParams.DEFAULT_PORT;
        String mPort=finishTaskSP.getString("Port", dPort);
		String getFinishUrl="http://"+mUrl+":"+mPort+"/"+MHttpParams.FinishTaskUrlW;
		RequestParams params=new RequestParams();
		params.put("start", String.valueOf(start));
		params.put("limit", String.valueOf(limit));
		int id=finishTaskSP.getInt("id", 0);
		params.put("user_id", String.valueOf(id));
		asyncHttpClient.post(getFinishUrl, params, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				// TODO 自动生成的方法存根
				super.onSuccess(statusCode, response);
				if(finishTaskPd!=null&&finishTaskPd.isShowing()){
					finishTaskPd.dismiss();
					finishTaskPd=null;
				}
				boolean success=false;
				try{
					success=response.getBoolean("success");
					if(success){
						JSONArray data=response.getJSONArray("data");
						mList.clear();
						if(data.length()>0){
							for(int i=0;i<data.length();i++){
								JSONObject mData=data.getJSONObject(i);
								String case_no=mData.getString("case_NO");
								int case_idInt=mData.getInt("case_id");
								String case_id=String.valueOf(case_idInt);
								//MHttpStorage.case_id=case_id;
								String car_no=mData.getString("car_NO");
								String brand_name=mData.getString("brand_name");
								int is_target=mData.getInt("is_tartget");
								String is_targetStr="否";
								if(is_target==1){
									is_targetStr="是";	
								}
								
								String target_no=mData.getString("target_NO");
								int is_vip=mData.getInt("isvip");
								String is_vipStr=String.valueOf(is_vip);
								String parters_name=mData.getString("parters_name");
								String parter_manager=mData.getString("parter_manager");
								String parter_mobile=mData.getString("parter_mobile");
								
								String dingsuner_name=mData.getString("decide_name");
								String dingusner_mobile=mData.getString("decide_telephone");
								
								String yard_timeStamp=mData.getString("yard_time");//使用timestamp传递到myobject
								String yard_time=MUtil.getDetailTime(yard_timeStamp);
								String repairState=mData.getString("repair_state");//是否可修
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
								
								//String repair_remark="";
								String repair_remark=mData.getString("repair_remark");
								if(repair_remark==null||repair_remark.equals("")){
									repair_remark="";
								}
								MyFinishObject mfinishTaskObject=new MyFinishObject(case_no,case_id,car_no,brand_name,is_targetStr,target_no
										,parters_name,parter_manager,parter_mobile,dingsuner_name,
										dingusner_mobile,yard_timeStamp,repairState,repair_time,repair_factoryname,repair_parts,parts_price,repair_price,loss_price,repair_remark);
								mList.add(mfinishTaskObject);
							}
							mFinishObjAdapter.notifyDataSetChanged();
						}
						finishTasklv.onRefreshComplete();
					}else{
						
						Toast.makeText(getActivity(), "暂无新任务", Toast.LENGTH_SHORT).show();
					}
				}catch(JSONException e){
					e.printStackTrace();
					
				}finally{
					finishTasklv.onRefreshComplete();
				}
				
			}

			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
				// TODO 自动生成的方法存根
				super.onFailure(e, errorResponse);
				if(finishTaskPd!=null&&finishTaskPd.isShowing()){
					finishTaskPd.dismiss();
					finishTaskPd=null;
				}
			}
			
			@Override
			public void onFailure(Throwable e, String errorResponse) {
				// TODO 自动生成的方法存根
				super.onFailure(e, errorResponse);
				if(finishTaskPd!=null&&finishTaskPd.isShowing()){
					finishTaskPd.dismiss();
					finishTaskPd=null;
				}
			}
			
		});
	}
}
