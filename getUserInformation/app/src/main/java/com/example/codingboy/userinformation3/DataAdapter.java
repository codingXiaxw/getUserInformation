package com.example.codingboy.userinformation3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by codingBoy on 16/4/18.
 */
public class DataAdapter extends BaseAdapter
{
    List<DataBean> mlist;
    private LayoutInflater inflater;

    public DataAdapter(Context context,List<DataBean> data){

        mlist=data;
        inflater=LayoutInflater.from(context);
}

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.item_layout,null);
            viewHolder.icon=(ImageView)convertView.findViewById(R.id.list_icon);
            viewHolder.tvId=(TextView)convertView.findViewById(R.id.list_id);
            viewHolder.tvName=(TextView)convertView.findViewById(R.id.list_name);
            convertView.setTag(viewHolder);

        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.icon.setImageResource(R.drawable.d);
        viewHolder.tvId.setText(mlist.get(position).userId);
        viewHolder.tvName.setText(mlist.get(position).userName);
        return convertView;
    }
    class ViewHolder{
        public TextView tvId,tvName;
        public ImageView icon;
    }
}
