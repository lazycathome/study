package cn.bigdb.cc;


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.DigestUtils;
/**
 * ����Commons Codec��SHA�㷨
 */
public class SHACC {
    private static final String ENCODING = "UTF-8";
    
    /**
     * SHA����,���ܺ�Ľ��Ϊ�������ֽ�����
     */
    public static byte[] encode(String data) throws NoSuchAlgorithmException,UnsupportedEncodingException {
        //return DigestUtils.sha1(data.getBytes(ENCODING));//SHA-1
        return DigestUtils.sha256(data.getBytes(ENCODING));//SHA-256
    }
    
    /**
     *SHA����,���ܺ�Ľ��Ϊ�������ֽ����飬���������ｫ�������ֽ�����תΪ��16�����ַ���
     */
    public static String encodeSHAHex(String data) throws NoSuchAlgorithmException,UnsupportedEncodingException {
        //return new String(DigestUtils.sha1Hex(data.getBytes(ENCODING)));
        return new String(DigestUtils.sha256Hex(data.getBytes(ENCODING)));
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
        byte[] encodedByte = SHACC.encode(data);
        System.out.println("���ܺ�-->"+encodedByte);
        byte[] encodedByte2 = SHACC.encode(data);
        System.out.println("���ܺ�-->"+encodedByte2);
        for(int i=0;i<encodedByte.length;i++){
            System.out.println(encodedByte[i]==encodedByte2[i]);
        }
        /*************����encodeSHAHex()**************/
        System.out.println("ԭ��-->"+data);
        String encodedStr = SHACC.encodeSHAHex(data);
        System.out.println("���ܺ�-->"+encodedStr);
        String encodedStr2 = SHACC.encodeSHAHex(data);
        System.out.println("���ܺ�-->"+encodedStr2);
        System.out.println(encodedStr.equals(encodedStr2));
    }
}