package cn.bigdb.bc;

import java.io.UnsupportedEncodingException;

import org.bouncycastle.util.encoders.Base64;

/**
 * 基于Bouncy Castle的Base64加密
 */
public class Base64CoderBC {
    private static final String ENCODING = "UTF-8";
    
    /**
     * Base64加密
     */
    public static String encode(String data) throws UnsupportedEncodingException{
        byte[] encodedByte = Base64.encode(data.getBytes(ENCODING));
        return new String(encodedByte, ENCODING);
    }
    
    /**
     * Base64解密
     */
    public static String decode(String data) throws UnsupportedEncodingException{
        byte[] decodedByte = Base64.decode(data.getBytes(ENCODING));
        return new String(decodedByte, ENCODING);
    }
    
    /**
     * 测试
     * @param args
     * @throws UnsupportedEncodingException 
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        String data = " 找一个好姑娘做老婆是我的梦 想！  ";
        System.out.println("原文-->"+data);
        String encodedStr = Base64CoderBC.encode(data);
        System.out.println("加密后-->"+encodedStr);
        String decodedStr = Base64CoderBC.decode(encodedStr);
        System.out.println("解密后-->"+decodedStr);
        System.out.println(data.equals(decodedStr));
    }
}