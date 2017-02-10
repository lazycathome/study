package cn.bigdb.cc;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.DigestUtils;
/**
 * 基于Commons Codec的md5算法
 */
public class Md5CC {
    private static final String ENCODING = "UTF-8";
    
    /**
     * MD5加密,加密后的结果为二进制字节数组
     */
    public static byte[] encode(String data) throws NoSuchAlgorithmException,UnsupportedEncodingException {
        return DigestUtils.md5(data.getBytes(ENCODING));
    }
    
    /**
     *MD5加密,加密后的结果为二进制字节数组，并且在这里将二进制字节数组转为了32位的16进制
     */
    public static String encodeMd5Hex(String data) throws NoSuchAlgorithmException,UnsupportedEncodingException {
        return new String(DigestUtils.md5Hex(data.getBytes(ENCODING)));//这里直接使用new String(encodedByte,"UTF-8")不行
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
        byte[] encodedByte = Md5CC.encode(data);
        System.out.println("加密后-->"+encodedByte);
        byte[] encodedByte2 = Md5CC.encode(data);
        System.out.println("加密后-->"+encodedByte2);
        for(int i=0;i<encodedByte.length;i++){
            System.out.println(encodedByte[i]==encodedByte2[i]);
        }
        /*************测试encodeMd5Hex()**************/
        System.out.println("原文-->"+data);
        String encodedStr = Md5CC.encodeMd5Hex(data);
        System.out.println("加密后-->"+encodedStr);
        String encodedStr2 = Md5CC.encodeMd5Hex(data);
        System.out.println("加密后-->"+encodedStr2);
        System.out.println(encodedStr.equals(encodedStr2));
    }
}