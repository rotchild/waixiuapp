package com.cx.myobject;

import java.util.List;





import com.cx.waixiuapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TaskWRecordAdapter extends ArrayAdapter<MyTaskObject>{
	private int resourceId;
	private Context mContext;
	private List<MyTaskObject> mList;
	public TaskWRecordAdapter(Context context, int resource,
			List<MyTaskObject> objects) {
		super(context, resource, objects);
		resourceId=resource;
		mContext=context;
		mList=objects;

	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		if(null!=mList){
			return mList.size();
		}
		return 0;
	}

	@Override
	public MyTaskObject getItem(int position) {
		// TODO 自动生成的方法存根
		if(null!=mList&&position<getCount()){
			return mList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		MyTaskObject taskRecord=getItem(position);
		View view=null;
		ViewHolder viewHolder;
		if(convertView==null){
			view=LayoutInflater.from(mContext).inflate(R.layout.taskw_item2_layout, null);
			viewHolder=new ViewHolder();
			viewHolder.reportNoTv=(TextView)view.findViewById(R.id.item_reportNoTV);
			viewHolder.cardNoTv=(TextView)view.findViewById(R.id.item_carNo);
			viewHolder.receiveTimeTv=(TextView)view.findViewById(R.id.item_receiveTimeTv);
			viewHolder.vipTagTv=(TextView)view.findViewById(R.id.item_vipTag);
			view.setTag(viewHolder);
		}else{
			view=convertView;
			viewHolder=(ViewHolder)view.getTag();
		}
		viewHolder.reportNoTv.setText(taskRecord.getCase_No());
		viewHolder.cardNoTv.setText(taskRecord.getCar_No());
		//isVip????
		viewHolder.receiveTimeTv.setText(taskRecord.getYard_time());
		viewHolder.vipTagTv.setText(taskRecord.getIsvip());
		return view;
	}
	class ViewHolder{
		TextView reportNoTv,cardNoTv,vipTagTv,receiveTimeTv;
		}

}
