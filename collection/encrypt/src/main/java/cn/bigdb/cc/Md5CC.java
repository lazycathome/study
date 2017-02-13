package cn.bigdb.cc;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.DigestUtils;
/**
 * ����Commons Codec��md5�㷨
 */
public class Md5CC {
    private static final String ENCODING = "UTF-8";
    
    /**
     * MD5����,���ܺ�Ľ��Ϊ�������ֽ�����
     */
    public static byte[] encode(String data) throws NoSuchAlgorithmException,UnsupportedEncodingException {
        return DigestUtils.md5(data.getBytes(ENCODING));
    }
    
    /**
     *MD5����,���ܺ�Ľ��Ϊ�������ֽ����飬���������ｫ�������ֽ�����תΪ��32λ��16����
     */
    public static String encodeMd5Hex(String data) throws NoSuchAlgorithmException,UnsupportedEncodingException {
        return new String(DigestUtils.md5Hex(data.getBytes(ENCODING)));//����ֱ��ʹ��new String(encodedByte,"UTF-8")����
    }
    
    /**
     * ����
     * @param args
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     */
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        
        String data = "��һ���ù������������ҵ��� ��!";
        /*************����encode()**************/
        System.out.println("ԭ��-->"+data);
        byte[] encodedByte = Md5CC.encode(data);
        System.out.println("���ܺ�-->"+encodedByte);
        byte[] encodedByte2 = Md5CC.encode(data);
        System.out.println("���ܺ�-->"+encodedByte2);
        for(int i=0;i<encodedByte.length;i++){
            System.out.println(encodedByte[i]==encodedByte2[i]);
        }
        /*************����encodeMd5Hex()**************/
        System.out.println("ԭ��-->"+data);
        String encodedStr = Md5CC.encodeMd5Hex(data);
        System.out.println("���ܺ�-->"+encodedStr);
        String encodedStr2 = Md5CC.encodeMd5Hex(data);
        System.out.println("���ܺ�-->"+encodedStr2);
        System.out.println(encodedStr.equals(encodedStr2));
    }
}