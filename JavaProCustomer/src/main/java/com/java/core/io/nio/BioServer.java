package com.java.core.io.nio;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/10/25
 */
public class BioServer {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(3333 , 1);
        serverSocket.setSoTimeout(0);
        System.out.println("listening....");
        while (true) {
//            Socket socket = serverSocket.accept();
//            System.out.println(String.format("ip:%s , port:%s" , socket.getRemoteSocketAddress() , socket.getPort()));
        }


    }

}
