package com.imall.note.webService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * @author zhangpengjun
 */
public class WebServiceProducter {

    @WebService
    public interface Songs {
        @WebMethod
        public String getSongs(String songName);
    }

    @WebService
    public static class SongImpl implements Songs {

        @Override
        public String getSongs(String songName) {
            if(null==songName)
                return null;
            if(songName.equals("月")){
                return "寒";
            }else if(songName.equals("阳")){
                return "热";
            }else{
                return "未找到";
            }
        }

    }

    public static void main(String[] args) {
        //定义WebService的发布地址  这个地址就是外界访问WebService的URL地址  保证端口号没有被占用
        String address = "http://localhost:8989/DemoWebService/service";//测试注意加上?wsdl
        Endpoint.publish(address, new SongImpl());
        System.out.println("OK");
    }

}
