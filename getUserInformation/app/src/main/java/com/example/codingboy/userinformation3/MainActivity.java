package com.example.codingboy.userinformation3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private  ListView mListView;
    private  EditText inputMessage;
    private Button sendMessage;
    private DataBean dataBean;
    public static final String Address="https://api.douban.com/v2/user?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView= (ListView) findViewById(R.id.lv_main);
        inputMessage= (EditText) findViewById(R.id.inputMessage);
        sendMessage= (Button) findViewById(R.id.sendMessage);


        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAsyncTask().execute(Address+inputMessage.getText().toString());

            }
        });

    }

    class MyAsyncTask extends AsyncTask<String,Void,List<DataBean>>
    {

        public List<DataBean> getJsonData(String url)
        {
            List<DataBean> dataBeanList=new ArrayList<>();
            JSONObject jsonObject;
            JSONArray jsonArray;
            String response="";
            try {
                URL add = new URL(url);
                HttpURLConnection connection= (HttpURLConnection) add.openConnection();
                InputStream in=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line=reader.readLine())!=null)
                {
                    response+=line;
                }
                jsonObject=new JSONObject(response);
                jsonArray=jsonObject.getJSONArray("users");
                for(int i=0;i<jsonArray.length();i++)
                {
                    dataBean=new DataBean();
                    dataBean.imageIconURL=jsonArray.getJSONObject(i).getString("avatar");
                    dataBean.userId=jsonArray.getJSONObject(i).getString("id");
                    dataBean.userName=jsonArray.getJSONObject(i).getString("name");
                    dataBeanList.add(dataBean);
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return dataBeanList;
        }



        @Override
        protected List<DataBean> doInBackground(String... params) {

            return getJsonData(params[0]);
        }
        @Override
        protected void onPostExecute(List<DataBean> dataBeanList)
        {
            super.onPostExecute(dataBeanList);
            DataAdapter adapter=new DataAdapter(MainActivity.this,dataBeanList);
            mListView.setAdapter(adapter);
        }
    }
}
