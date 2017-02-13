package cn.bigdb.bc;


import java.io.UnsupportedEncodingException;

import org.bouncycastle.util.encoders.Base64;

/**
 * ����Bouncy Castle��Base64����
 */
public class Base64CoderBC {
    private static final String ENCODING = "UTF-8";
    
    /**
     * Base64����
     */
    public static String encode(String data) throws UnsupportedEncodingException{
        byte[] encodedByte = Base64.encode(data.getBytes(ENCODING));
        return new String(encodedByte, ENCODING);
    }
    
    /**
     * Base64����
     */
    public static String decode(String data) throws UnsupportedEncodingException{
        byte[] decodedByte = Base64.decode(data.getBytes(ENCODING));
        return new String(decodedByte, ENCODING);
    }
    
    /**
     * ����
     * @param args
     * @throws UnsupportedEncodingException 
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        String data = " ��һ���ù������������ҵ��� �룡  ";
        System.out.println("ԭ��-->"+data);
        String encodedStr = Base64CoderBC.encode(data);
        System.out.println("���ܺ�-->"+encodedStr);
        String decodedStr = Base64CoderBC.decode(encodedStr);
        System.out.println("���ܺ�-->"+decodedStr);
        System.out.println(data.equals(decodedStr));
    }
}