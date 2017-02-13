package cn.bigdb.cc;

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Base64;

/**
 * ����Commons Codec��Base64����
 */
public class Base64CoderCC {
    private static final String ENCODING = "UTF-8";
    
    /**
     * һ��Base64����
     */
    public static String encode(String data) throws UnsupportedEncodingException{
        byte[] encodedByte = Base64.encodeBase64(data.getBytes(ENCODING));
        return new String(encodedByte, ENCODING);
    }
    
    /**
     * ��ȫBase64����
     */
    public static String encodeSafe(String data) throws UnsupportedEncodingException{
        /*
         * ע�⣺������õ�encodeBase64(byte[] bytes, boolean arg1)
         * arg1Ϊtrueʱ�����ܺ���ַ���ÿ��Ϊ76���ַ�������ÿ�й�����76���ַ�����Ҫ����β��ӡ�\r\n��
         */
        byte[] encodedByte = Base64.encodeBase64(data.getBytes(ENCODING),true);
        return new String(encodedByte, ENCODING);
    }
    
    /**
     * Base64����
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
        /********************����һ��encode*********************/
        String data = "��һ���ù������������ҵ��� �룡";
        System.out.println("ԭ��-->"+data);
        String encodedStr = Base64CoderCC.encode(data);
        System.out.println("���ܺ�-->"+encodedStr);
        String decodedStr = Base64CoderCC.decode(encodedStr);
        System.out.println("���ܺ�-->"+decodedStr);
        System.out.println(data.equals(decodedStr));
        System.out.println("================================");
        /********************���԰�ȫencode*********************/
        String data2 = "��һ���ù������������ҵ��� �룡��һ���ù������������ҵ��� �룡";
        System.out.println("ԭ��-->"+data2);
        String encodedStr2 = Base64CoderCC.encodeSafe(data2);
        System.out.println("���ܺ�-->"+encodedStr2);
        String decodedStr2 = Base64CoderCC.decode(encodedStr2);
        System.out.println("���ܺ�-->"+decodedStr2);
        System.out.println(data2.equals(decodedStr2));
    }
}