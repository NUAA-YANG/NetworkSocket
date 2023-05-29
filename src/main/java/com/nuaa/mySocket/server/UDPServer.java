package com.nuaa.mySocket.server;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @Author YZX
 * @Create 2023-05-29 10:11
 * @Java-version jdk1.8
 */
//构建UDP协议的服务端
public class UDPServer {
    //构建套接字
    DatagramSocket socket;

    //构建协议对象(参数表示服务器端口号)
    public UDPServer(int serverPort) throws SocketException {
        socket = new DatagramSocket(serverPort);
    }


    //开启服务器进行监听
    public void startListening() throws IOException {
        System.out.println("===============服务器开启监听===================");
        while (true){
            //1.构建数据包用来接受数据
            DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
            //2.等待接受数据(没有返回值，但是已经接收到了数据)
            socket.receive(receivePacket);

            //3.将接受的数据取出
            String receiveData = new String(receivePacket.getData(),0,receivePacket.getLength());
            //设定退出
            if ("ex".equals(receiveData) || "exit".equals(receiveData)){
                System.out.println("===============服务器退出===================");
                break;
            }

            //4.根据请求进行回复
            UDPAnswer udpAnswer = new UDPAnswer();
            String answer = udpAnswer.answer(receiveData);
            byte[] answerBytes = answer.getBytes();

            //5.将回复封装为数据包发送回客户端
            //注意：这里是根据接受的数据包来获取发送方的ip和端口
            DatagramPacket answerPacket = new DatagramPacket(answerBytes, 0, answerBytes.length, receivePacket.getSocketAddress());
            socket.send(answerPacket);

            //6.校验
            System.out.println("**********本次收发信息如下**********\n" +
                    "发送方ip:{"+receivePacket.getAddress()+"}\n" +
                    "发送方端口:{"+receivePacket.getPort()+"}\n"+
                    "接受信息:{"+receiveData+"}\n"+
                    "回复信息:{"+answer+"}");
        }
    }

    public static void main(String[] args) throws IOException {
        UDPServer udpServer = new UDPServer(8899);
        udpServer.startListening();
    }

}
