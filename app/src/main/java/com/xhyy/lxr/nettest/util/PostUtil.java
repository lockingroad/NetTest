package com.xhyy.lxr.nettest.util;

import com.xhyy.lxr.nettest.entity.StrNetInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by LXR on 2016/8/17.
 */
public class PostUtil {
    public String getString(StrNetInfo info){

        String result=null;

        try {
            URL url=new URL(info.inter);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000*10);
            connection.setReadTimeout(3000*10);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            OutputStream os=connection.getOutputStream();
            os.write(info.parame.getBytes());
            os.flush();
            os.close();
            if(connection.getResponseCode()==200){
                InputStream inputStream=connection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                StringBuilder sb=new StringBuilder();
                String str=null;
                while ((str=bufferedReader.readLine())!=null){
                    sb.append(str);
                }
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                result=sb.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
