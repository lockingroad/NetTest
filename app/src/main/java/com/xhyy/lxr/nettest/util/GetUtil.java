package com.xhyy.lxr.nettest.util;

import com.xhyy.lxr.nettest.entity.StrNetInfo;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by LXR on 2016/8/17.
 */
public class GetUtil {
    public String getString (StrNetInfo info){
        String result=null;
        try {
            URL url=new URL(info.inter);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setReadTimeout(30*1000);
            connection.setConnectTimeout(30*1000);
            connection.setUseCaches(false);
            if(connection.getResponseCode()==200){
                InputStream is=connection.getInputStream();
                BufferedInputStream bis=new BufferedInputStream(is);

                ByteArrayOutputStream bos=new ByteArrayOutputStream();
                int c=0;
                while (-1!=(c=bis.read())){
                    bos.write(c);
                }
                bis.close();
                is.close();
                bos.flush();
                StringBuilder sb=new StringBuilder(new String(bos.toByteArray()));
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
