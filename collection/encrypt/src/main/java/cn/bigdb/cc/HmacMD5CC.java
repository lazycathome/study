package cn.bigdb.cc;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacUtils;

/**
 * 基于CC的HmacMD5算法
 */
public class HmacMD5CC {
    private static final String ENCODING = "UTF-8";
    /**
     * 产生密钥
     */
    public static byte[] getKey() throws NoSuchAlgorithmException, DecoderException{
        return Hex.decodeHex(new char[]{'a','b','c','d'}); 
    }
    
    /**
     * HmacMD5加密
     * @param data     带加密数据
     * @param keyByte  密钥
     */
    public static byte[] encode(String data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                    InvalidKeyException, 
                                                                    IllegalStateException, 
                                                                    UnsupportedEncodingException {
        return HmacUtils.hmacMd5(keyByte, data.getBytes(ENCODING));
    }
    
    /**
     * HmacMD5加密，并转为16进制
     */
    public static String encodeHmacMD5Hex(String data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                              UnsupportedEncodingException, 
                                                                              InvalidKeyException, 
                                                                              IllegalStateException {
        return HmacUtils.hmacMd5Hex(keyByte, data.getBytes(ENCODING));
    }
    
    /**
     * 测试
     * @throws IllegalStateException 
     * @throws InvalidKeyException 
     * @throws DecoderException 
     */
    public static void main(String[] args) throws UnsupportedEncodingException, 
                                                  NoSuchAlgorithmException, 
                                                  InvalidKeyException, 
                                                  IllegalStateException, 
                                                  DecoderException {
        String data = "找一个好姑娘做老婆是我的梦 想!";
        /*************测试encode()**************/
        System.out.println("原文-->"+data);
        byte[] keyByte = HmacMD5CC.getKey(); 
        byte[] encodedByte = HmacMD5CC.encode(data, keyByte);
        System.out.println("加密后-->"+encodedByte);
        byte[] encodedByte2 = HmacMD5CC.encode(data, keyByte);
        System.out.println("加密后-->"+encodedByte2);
        for(int i=0;i<encodedByte.length;i++){
            System.out.println(encodedByte[i]==encodedByte2[i]);
        }
        /*************测试encodeHmacMD5Hex()**************/
        System.out.println("原文-->"+data);
        String encodedStr = HmacMD5CC.encodeHmacMD5Hex(data, keyByte);
        System.out.println("加密后-->"+encodedStr);
        String encodedStr2 = HmacMD5CC.encodeHmacMD5Hex(data, keyByte);
        System.out.println("加密后-->"+encodedStr2);
        System.out.println(encodedStr.equals(encodedStr2));
    }
}