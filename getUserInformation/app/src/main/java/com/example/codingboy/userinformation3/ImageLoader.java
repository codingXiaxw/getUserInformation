package com.example.codingboy.userinformation3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by codingBoy on 16/4/21.
 */

//    private String mUrl;
//    private ImageView mImageView;
//    private Handler handler=new Handler(){
//        @Override
//    public void handleMessage(Message msg)
//        {
//            super.handleMessage(msg);
//            if (mImageView.getTag().equals(mUrl)) {
//                mImageView.setImageBitmap((Bitmap) msg.obj);
//            }
//        }
//    };

//    public void showImageByThread(ImageView imageView, final String url){
//        mImageView=imageView;
//        mUrl=url;
//
//        new Thread()
//        {
//            @Override
//        public void run()
//            {
//                super.run();
//                Bitmap bitmap=getBitmapFromURL(url);
//                Message message=Message.obtain();
//                message.obj=bitmap;
//                handler.sendMessage(message);
//            }
//        }.start();
//    }
public class ImageLoader  {

    private LruCache<String,Bitmap> mCathes;

    public ImageLoader()
    {
        int maxMemory= (int) Runtime.getRuntime().maxMemory();
        int cacheSize=maxMemory/4;
        mCathes=new LruCache<String ,Bitmap>(cacheSize)
        {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public void addBitmapToCache(String url,Bitmap bitmap)
    {
        if(getBitmapFromCache(url)==null)
        {
            mCathes.put(url,bitmap);
        }
    }
    public Bitmap getBitmapFromCache(String url)
    {
        return mCathes.get(url);
    }

    public Bitmap getBitmapFromURL(String urlString) {

        Bitmap bitmap;
        InputStream in=null;
        try {
            URL url=new URL(urlString);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            in=new BufferedInputStream(connection.getInputStream());
            bitmap= BitmapFactory.decodeStream(in);
            connection.disconnect();
            return  bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void showImageByAsyncTask(ImageView imageView,String url)
    {
        //从缓存中取出对应的图片
        Bitmap bitmap=getBitmapFromCache(url);
        if(bitmap==null) {
            //如果缓存中没有，则必须去网络中下载。
            new MyAsyncTask(imageView,url).execute(url);
        }else {
            imageView.setImageBitmap(bitmap);
        }
    }


    public class MyAsyncTask extends AsyncTask<String,Void,Bitmap>
    {
      private ImageView mImageView;
        private String mUrl;
        public MyAsyncTask(ImageView imageView,String url)
        {
           mImageView=imageView;
            mUrl=url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            //从网络中获取图片
           Bitmap bitmap=getBitmapFromURL(params[0]);
            if(bitmap!=null)
            {//将获取的图片加入缓存中
                addBitmapToCache(params[0],bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(mImageView.getTag().equals(mUrl))
            {
                mImageView.setImageBitmap(bitmap);
            }
        }
    }


}
