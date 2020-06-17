package com.test.sate;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

class SateAdapter extends BaseAdapter {
	private Context context = null;
	private List<Sate> list;

	public SateAdapter(Context context, List<Sate> list) {
		this.context = context;
		this.list = list;
	}

	public void clearList() {
		if (list != null) {
			list.clear();
			notifyDataSetInvalidated();
		}
	}

	public void setList(List<Sate> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder;
		if (convertView == null) {
			mHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.item_sate, null, true);
			mHolder.llayout = (LinearLayout) convertView
					.findViewById(R.id.layout);
			mHolder.tvname = (TextView) convertView.findViewById(R.id.tvname);
			mHolder.tvstatus = (TextView) convertView.findViewById(R.id.tvstatus);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		mHolder.tvname.setText(list.get(position).getName());
		mHolder.tvstatus.setText(list.get(position).getStatus());
		return convertView;
	}

	class ViewHolder {
		private TextView tvname;
		private TextView tvstatus;
		private LinearLayout llayout;
	}
	private OnNewDetail onNewDetail;

	public void setOnNewDetail(OnNewDetail onNewDetail) {
		this.onNewDetail = onNewDetail;
	}

	public interface OnNewDetail{
		void setPosition(int id);
	}

}