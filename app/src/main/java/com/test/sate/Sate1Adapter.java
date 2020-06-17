package com.test.sate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class Sate1Adapter extends BaseAdapter {


     private Context context = null;
        private List<Sate> list;

        public Sate1Adapter(Context context, List<Sate> list) {
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
                convertView = inflater.inflate(R.layout.item_sate1, null, true);
                mHolder.llayout1 = (LinearLayout) convertView
                        .findViewById(R.id.layout1);
                mHolder.tvname1 = (TextView) convertView.findViewById(R.id.tvname1);
                mHolder.tvstatus1 = (TextView) convertView.findViewById(R.id.tvstatus1);
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }
                mHolder.tvname1.setText(list.get(position).getName());
            mHolder.tvstatus1.setText(list.get(position).getStatus());
            return convertView;
        }

        class ViewHolder {
            private TextView tvname1;
            private TextView tvstatus1;
            private LinearLayout llayout1;
        }
        private com.test.sate.Sate1Adapter.OnNewDetail onNewDetail;

        public void setOnNewDetail(com.test.sate.Sate1Adapter.OnNewDetail onNewDetail) {
            this.onNewDetail = onNewDetail;
        }

        public interface OnNewDetail{
            void setPosition(int id);
        }


}
