package com.example.codingboy.userinformation3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by codingBoy on 16/4/21.
 */
public class ImageLoader  {
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
        new MyAsyncTask(imageView,url).execute(url);
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
           return  getBitmapFromURL(params[0]);

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(mImageView.getTag().equals(mUrl)) {
                mImageView.setImageBitmap(bitmap);
            }
        }
    }

}
