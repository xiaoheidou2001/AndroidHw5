package com.bytedance.practice5.socket;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSocketThread extends Thread {
    public ClientSocketThread(SocketActivity.SocketCallback callback) {
        this.callback = callback;
    }

    private SocketActivity.SocketCallback callback;

    private boolean stopFlag = false;

    //head请求内容
    private static String content = "HEAD / HTTP/1.1\r\nHost:www.zju.edu.cn\r\n\r\n";


    @Override
    public void run() {
        // TODO 6 用socket实现简单的HEAD请求（发送content）
        //  将返回结果用callback.onresponse(result)进行展示
        String message = content;

        try{
            Socket socket = new Socket("localhost",30000);
            BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());
            BufferedInputStream is = new BufferedInputStream(socket.getInputStream());

            byte[] data = new byte[1024*5];
            while(!stopFlag&&socket.isConnected()){
                if(!message.isEmpty()){
                    os.write(message.getBytes());
                    os.flush();
                    message = "";
                    int receiveLen = is.read(data);
                    String receive = new String(data,0,receiveLen);
                    Log.d("socket","客户端接收"+receive);
                    callback.onResponse(receive);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            Log.d("socket","错误！"+e.toString());
        }
        }
}