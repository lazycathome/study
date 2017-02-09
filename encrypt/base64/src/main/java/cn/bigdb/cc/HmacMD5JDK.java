package cn.bigdb.cc;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Hex;

/**
 * 基于JDK的HmacMD5算法
 */
public class HmacMD5JDK {
    private static final String ENCODING = "UTF-8";
    private static final String ALGORITHM = "HmacMD5";//指定具体算法HmacMD5,HmacSHA1,HmacSHA256
    
    /**
     * 产生密钥两种方式 1)是由jdk自己来产生的，2)我们可以自己指定一个字节数组
     * 注意：密钥是以二进制字节数组存储的
     */
    public static byte[] getKey() throws NoSuchAlgorithmException{
        SecretKey key = KeyGenerator.getInstance(ALGORITHM).generateKey();
        return key.getEncoded();
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
        SecretKey key = new SecretKeySpec(keyByte, ALGORITHM);//还原密钥
        Mac mac = Mac.getInstance(key.getAlgorithm());
        mac.init(key);//为mac实例初始化密钥
        return mac.doFinal(data.getBytes(ENCODING));
    }
    
    /**
     * HmacMD5加密，并转为16进制
     */
    public static String encodeHmacMD5Hex(String data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                              UnsupportedEncodingException, 
                                                                              InvalidKeyException, 
                                                                              IllegalStateException {
        byte[] encodedByte = encode(data, keyByte);
        return new String(Hex.encode(encodedByte));//借助BC
        //return new String(org.apache.commons.codec.binary.Hex.encodeHexString(encodedByte));//借助CC
    }
    
    /**
     * 测试
     * @throws IllegalStateException 
     * @throws InvalidKeyException 
     */
    public static void main(String[] args) throws UnsupportedEncodingException, 
                                                  NoSuchAlgorithmException, 
                                                  InvalidKeyException, 
                                                  IllegalStateException {
        String data = "找一个好姑娘做老婆是我的梦 想!";
        /*************测试encode()**************/
        System.out.println("原文-->"+data);
        byte[] keyByte = HmacMD5JDK.getKey(); 
        byte[] encodedByte = HmacMD5JDK.encode(data, keyByte);
        System.out.println("加密后-->"+encodedByte);
        byte[] encodedByte2 = HmacMD5JDK.encode(data, keyByte);
        System.out.println("加密后-->"+encodedByte2);
        for(int i=0;i<encodedByte.length;i++){
            System.out.println(encodedByte[i]==encodedByte2[i]);
        }
        /*************测试encodeHmacMD5Hex()**************/
        System.out.println("原文-->"+data);
        String encodedStr = HmacMD5JDK.encodeHmacMD5Hex(data, keyByte);
        System.out.println("加密后-->"+encodedStr);
        String encodedStr2 = HmacMD5JDK.encodeHmacMD5Hex(data, keyByte);
        System.out.println("加密后-->"+encodedStr2);
        System.out.println(encodedStr.equals(encodedStr2));
    }
}