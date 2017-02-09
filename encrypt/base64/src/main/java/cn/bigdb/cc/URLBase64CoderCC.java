package cn.bigdb.cc;


import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Base64;
/**
 * ����Commons Codec��URLBase64����
 */
public class URLBase64CoderCC {
    private static final String ENCODING = "UTF-8";
    /**
     * URLBase64����
     */
    public static String encode(String data) throws UnsupportedEncodingException{
        byte[] encodedByte = Base64.encodeBase64URLSafe(data.getBytes(ENCODING));
        return new String(encodedByte, ENCODING);
    }
    /**
     * URLBase64����
     */
    public static String decode(String data) throws UnsupportedEncodingException{
        byte[] decodedByte = Base64.decodeBase64(data.getBytes(ENCODING));
        return new String(decodedByte, ENCODING);
    }
    
    /**
     * ����
     * @param args
     * @throws UnsupportedEncodingException 
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        String data = "��һ���ù������������ҵ��� �룡";
        System.out.println("ԭ��-->"+data);
        String encodedStr = URLBase64CoderCC.encode(data);
        System.out.println("���ܺ�-->"+encodedStr);
        String decodedStr = URLBase64CoderCC.decode(encodedStr);
        System.out.println("���ܺ�-->"+decodedStr);
        System.out.println(data.equals(decodedStr));
    }
}