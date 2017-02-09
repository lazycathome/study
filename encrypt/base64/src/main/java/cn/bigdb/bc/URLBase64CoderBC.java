package cn.bigdb.bc;


import java.io.UnsupportedEncodingException;
import org.bouncycastle.util.encoders.UrlBase64;
/**
 * ����BC��URLBase64����
 */
public class URLBase64CoderBC {
    private static final String ENCODING = "UTF-8";
    /**
     * URLBase64����
     */
    public static String encode(String data) throws UnsupportedEncodingException{
        byte[] encodedByte = UrlBase64.encode(data.getBytes(ENCODING));
        return new String(encodedByte, ENCODING);
    }
    /**
     * URLBase64����
     */
    public static String decode(String data) throws UnsupportedEncodingException{
        byte[] decodedByte = UrlBase64.decode(data.getBytes(ENCODING));
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
        String encodedStr = URLBase64CoderBC.encode(data);
        System.out.println("���ܺ�-->"+encodedStr);
        String decodedStr = URLBase64CoderBC.decode(encodedStr);
        System.out.println("���ܺ�-->"+decodedStr);
        System.out.println(data.equals(decodedStr));
    }
}