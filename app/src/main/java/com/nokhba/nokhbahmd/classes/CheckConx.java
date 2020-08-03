package com.nokhba.nokhbahmd.classes;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class CheckConx {
    public static Integer ping(){
        Integer res=0;

        try {
            Socket s = new Socket();
            SocketAddress adr = new InetSocketAddress("8.8.8.8", 53);
            s.connect(adr, 100);
            s.close();
            res = 1;
            //sleep(1000);
        } catch (Exception e) {
            res = 0;
        }


        return res;
    }
    public static boolean isConnected(Context context){
        boolean connected = false;

        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Service.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        if(nInfo != null){
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;

        }else {

            return connected;

        }


    }
}
