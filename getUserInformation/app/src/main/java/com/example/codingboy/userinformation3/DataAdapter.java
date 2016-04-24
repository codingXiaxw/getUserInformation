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
public class DataAdapter extends BaseAdapter implements AbsListView.OnScrollListener
{
    List<DataBean> mlist;
    private LayoutInflater inflater;
    private ImageLoader mImageLoader;
    private int mStart,mEnd;
    public static String [] URLS;
    private boolean mFirstIn;

    public DataAdapter(Context context,List<DataBean> data,ListView listView){

        mlist=data;
        inflater=LayoutInflater.from(context);
        mImageLoader=new ImageLoader(listView);
        URLS=new String[data.size()];
        for (int i = 0; i <data.size() ; i++) {
            URLS[i]=data.get(i).imageIconURL;
        }
        mFirstIn=true;
        listView.setOnScrollListener(this);
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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState==SCROLL_STATE_IDLE)
        {
            mImageLoader.loadImage(mStart,mEnd);
        }else {
            mImageLoader.cancelAllTasks();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        mStart=firstVisibleItem;
        mEnd=firstVisibleItem+visibleItemCount;
        if (mFirstIn&&visibleItemCount>0)
        {
            mImageLoader.loadImage(mStart,mEnd  );
            mFirstIn=false;
        }
    }


    class ViewHolder{
        public TextView tvId,tvName;
        public ImageView icon;
    }
}
