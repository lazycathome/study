package cn.bigdb.cc;


import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Base64;
/**
 * 基于Commons Codec的URLBase64加密
 */
public class URLBase64CoderCC {
    private static final String ENCODING = "UTF-8";
    /**
     * URLBase64加密
     */
    public static String encode(String data) throws UnsupportedEncodingException{
        byte[] encodedByte = Base64.encodeBase64URLSafe(data.getBytes(ENCODING));
        return new String(encodedByte, ENCODING);
    }
    /**
     * URLBase64解密
     */
    public static String decode(String data) throws UnsupportedEncodingException{
        byte[] decodedByte = Base64.decodeBase64(data.getBytes(ENCODING));
        return new String(decodedByte, ENCODING);
    }
    
    /**
     * 测试
     * @param args
     * @throws UnsupportedEncodingException 
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        String data = "找一个好姑娘做老婆是我的梦 想！";
        System.out.println("原文-->"+data);
        String encodedStr = URLBase64CoderCC.encode(data);
        System.out.println("加密后-->"+encodedStr);
        String decodedStr = URLBase64CoderCC.decode(encodedStr);
        System.out.println("解密后-->"+decodedStr);
        System.out.println(data.equals(decodedStr));
    }
}