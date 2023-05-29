package com.nuaa.mySocket.client;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/**
 * @Author YZX
 * @Create 2023-05-29 10:11
 * @Java-version jdk1.8
 */
//构建UDP协议的客户端
public class UDPClient {
    //服务器端口号
    int serverPort;
    //服务器ip
    String serverIp;
    //构建套接字
    DatagramSocket socket;

    //构建协议对象(参数表示服务器端口号和服务器ip地址)
    public UDPClient(int serverPort, String serverIp) throws SocketException {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.socket = new DatagramSocket();
    }


    public void startSend() throws IOException {
        System.out.println("===============客户端开启接受===================");
        //1.获取输入
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("请输入想要发送的信息:");
            String request = scanner.nextLine();
            byte[] requestBytes = request.getBytes();

            //2.构建数据包
            //InetAddress.getByName()表示根据服务器主机名称解析ip地址
            DatagramPacket requestPacket = new DatagramPacket(requestBytes, 0, requestBytes.length, InetAddress.getByName(serverIp), serverPort);

            //3.发送数据包
            socket.send(requestPacket);
            //设定退出
            if ("ex".equals(request) || "exit".equals(request)){
                System.out.println("===============客户端退出===================");
                break;
            }

            //4.构建数据包用来获取响应
            DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
            socket.receive(receivePacket);

            //5.将获取的数据取出
            String receiveData = new String(receivePacket.getData(),0,receivePacket.getLength());
            System.out.println("收到消息:{"+receiveData+"}");

        }
    }


    public static void main(String[] args) throws IOException {
        UDPClient udpClient = new UDPClient(8899, "127.0.0.1");
        udpClient.startSend();
    }
}
