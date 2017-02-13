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
 * ����JDK��HmacMD5�㷨
 */
public class HmacMD5JDK {
    private static final String ENCODING = "UTF-8";
    private static final String ALGORITHM = "HmacMD5";//ָ�������㷨HmacMD5,HmacSHA1,HmacSHA256
    
    /**
     * ������Կ���ַ�ʽ 1)����jdk�Լ��������ģ�2)���ǿ����Լ�ָ��һ���ֽ�����
     * ע�⣺��Կ���Զ������ֽ�����洢��
     */
    public static byte[] getKey() throws NoSuchAlgorithmException{
        SecretKey key = KeyGenerator.getInstance(ALGORITHM).generateKey();
        return key.getEncoded();
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
        SecretKey key = new SecretKeySpec(keyByte, ALGORITHM);//��ԭ��Կ
        Mac mac = Mac.getInstance(key.getAlgorithm());
        mac.init(key);//Ϊmacʵ����ʼ����Կ
        return mac.doFinal(data.getBytes(ENCODING));
    }
    
    /**
     * HmacMD5���ܣ���תΪ16����
     */
    public static String encodeHmacMD5Hex(String data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                              UnsupportedEncodingException, 
                                                                              InvalidKeyException, 
                                                                              IllegalStateException {
        byte[] encodedByte = encode(data, keyByte);
        return new String(Hex.encode(encodedByte));//����BC
        //return new String(org.apache.commons.codec.binary.Hex.encodeHexString(encodedByte));//����CC
    }
    
    /**
     * ����
     * @throws IllegalStateException 
     * @throws InvalidKeyException 
     */
    public static void main(String[] args) throws UnsupportedEncodingException, 
                                                  NoSuchAlgorithmException, 
                                                  InvalidKeyException, 
                                                  IllegalStateException {
        String data = "��һ���ù������������ҵ��� ��!";
        /*************����encode()**************/
        System.out.println("ԭ��-->"+data);
        byte[] keyByte = HmacMD5JDK.getKey(); 
        byte[] encodedByte = HmacMD5JDK.encode(data, keyByte);
        System.out.println("���ܺ�-->"+encodedByte);
        byte[] encodedByte2 = HmacMD5JDK.encode(data, keyByte);
        System.out.println("���ܺ�-->"+encodedByte2);
        for(int i=0;i<encodedByte.length;i++){
            System.out.println(encodedByte[i]==encodedByte2[i]);
        }
        /*************����encodeHmacMD5Hex()**************/
        System.out.println("ԭ��-->"+data);
        String encodedStr = HmacMD5JDK.encodeHmacMD5Hex(data, keyByte);
        System.out.println("���ܺ�-->"+encodedStr);
        String encodedStr2 = HmacMD5JDK.encodeHmacMD5Hex(data, keyByte);
        System.out.println("���ܺ�-->"+encodedStr2);
        System.out.println(encodedStr.equals(encodedStr2));
    }
}