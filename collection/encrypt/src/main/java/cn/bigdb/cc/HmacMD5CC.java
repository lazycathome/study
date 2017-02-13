package cn.bigdb.cc;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacUtils;

/**
 * ����CC��HmacMD5�㷨
 */
public class HmacMD5CC {
    private static final String ENCODING = "UTF-8";
    /**
     * ������Կ
     */
    public static byte[] getKey() throws NoSuchAlgorithmException, DecoderException{
        return Hex.decodeHex(new char[]{'a','b','c','d'}); 
    }
    
    /**
     * HmacMD5����
     * @param data     ����������
     * @param keyByte  ��Կ
     */
    public static byte[] encode(String data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                    InvalidKeyException, 
                                                                    IllegalStateException, 
                                                                    UnsupportedEncodingException {
        return HmacUtils.hmacMd5(keyByte, data.getBytes(ENCODING));
    }
    
    /**
     * HmacMD5���ܣ���תΪ16����
     */
    public static String encodeHmacMD5Hex(String data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                              UnsupportedEncodingException, 
                                                                              InvalidKeyException, 
                                                                              IllegalStateException {
        return HmacUtils.hmacMd5Hex(keyByte, data.getBytes(ENCODING));
    }
    
    /**
     * ����
     * @throws IllegalStateException 
     * @throws InvalidKeyException 
     * @throws DecoderException 
     */
    public static void main(String[] args) throws UnsupportedEncodingException, 
                                                  NoSuchAlgorithmException, 
                                                  InvalidKeyException, 
                                                  IllegalStateException, 
                                                  DecoderException {
        String data = "��һ���ù������������ҵ��� ��!";
        /*************����encode()**************/
        System.out.println("ԭ��-->"+data);
        byte[] keyByte = HmacMD5CC.getKey(); 
        byte[] encodedByte = HmacMD5CC.encode(data, keyByte);
        System.out.println("���ܺ�-->"+encodedByte);
        byte[] encodedByte2 = HmacMD5CC.encode(data, keyByte);
        System.out.println("���ܺ�-->"+encodedByte2);
        for(int i=0;i<encodedByte.length;i++){
            System.out.println(encodedByte[i]==encodedByte2[i]);
        }
        /*************����encodeHmacMD5Hex()**************/
        System.out.println("ԭ��-->"+data);
        String encodedStr = HmacMD5CC.encodeHmacMD5Hex(data, keyByte);
        System.out.println("���ܺ�-->"+encodedStr);
        String encodedStr2 = HmacMD5CC.encodeHmacMD5Hex(data, keyByte);
        System.out.println("���ܺ�-->"+encodedStr2);
        System.out.println(encodedStr.equals(encodedStr2));
    }
}