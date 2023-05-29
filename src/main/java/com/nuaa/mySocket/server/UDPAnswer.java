package com.nuaa.mySocket.server;

/**
 * @Author YZX
 * @Create 2023-05-29 10:30
 * @Java-version jdk1.8
 */
//根据服务器收到的请求进行应答
public class UDPAnswer {
    public String answer(String request){
        if (request.matches("(.)*今天(.)*")){
            return "是啊是啊";
        }else if (request.matches("(.)*我(.)*")){
            return "哎呀";
        } else if (request.matches("(.)*原来(.)*")) {
            return "没事没事";
        }else {
            return "同意";
        }
    }
}
