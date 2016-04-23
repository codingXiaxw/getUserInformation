package com.example.codingboy.userinformation3;

import android.content.Context;
import android.media.Image;
import android.text.StaticLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Stack;

/**
 * Created by codingBoy on 16/4/18.
 */
public class DataAdapter extends BaseAdapter
{
    List<DataBean> mlist;
    private LayoutInflater inflater;
    private ImageLoader mImageLoader;

    public DataAdapter(Context context,List<DataBean> data){

        mlist=data;
        inflater=LayoutInflater.from(context);
        mImageLoader=new ImageLoader();


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
        viewHolder.icon.setTag(mlist.get(position).imageIconURL);
//        new ImageLoader().showImageByThread(viewHolder.icon, mlist.get(position).imageIconURL);
        mImageLoader.showImageByAsyncTask(viewHolder.icon, mlist.get(position).imageIconURL);
        viewHolder.tvId.setText(mlist.get(position).userId);
        viewHolder.tvName.setText(mlist.get(position).userName);
        return convertView;
    }




    class ViewHolder{
        public TextView tvId,tvName;
        public ImageView icon;
    }
}
