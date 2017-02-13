package cn.bigdb.cc;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.DigestUtils;
/**
 * 基于Commons Codec的SHA算法
 */
public class SHACC {
    private static final String ENCODING = "UTF-8";
    
    /**
     * SHA加密,加密后的结果为二进制字节数组
     */
    public static byte[] encode(String data) throws NoSuchAlgorithmException,UnsupportedEncodingException {
        //return DigestUtils.sha1(data.getBytes(ENCODING));//SHA-1
        return DigestUtils.sha256(data.getBytes(ENCODING));//SHA-256
    }
    
    /**
     *SHA加密,加密后的结果为二进制字节数组，并且在这里将二进制字节数组转为了16进制字符串
     */
    public static String encodeSHAHex(String data) throws NoSuchAlgorithmException,UnsupportedEncodingException {
        //return new String(DigestUtils.sha1Hex(data.getBytes(ENCODING)));
        return new String(DigestUtils.sha256Hex(data.getBytes(ENCODING)));
    }
    
    /**
     * 测试
     * @param args
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     */
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        
        String data = "找一个好姑娘做老婆是我的梦 想!";
        /*************测试encode()**************/
        System.out.println("原文-->"+data);
        byte[] encodedByte = SHACC.encode(data);
        System.out.println("加密后-->"+encodedByte);
        byte[] encodedByte2 = SHACC.encode(data);
        System.out.println("加密后-->"+encodedByte2);
        for(int i=0;i<encodedByte.length;i++){
            System.out.println(encodedByte[i]==encodedByte2[i]);
        }
        /*************测试encodeSHAHex()**************/
        System.out.println("原文-->"+data);
        String encodedStr = SHACC.encodeSHAHex(data);
        System.out.println("加密后-->"+encodedStr);
        String encodedStr2 = SHACC.encodeSHAHex(data);
        System.out.println("加密后-->"+encodedStr2);
        System.out.println(encodedStr.equals(encodedStr2));
    }
}