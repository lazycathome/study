package cn.bigdb.aes;


import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * ����BC��IDEA�㷨������ģʽֻ��ECB
 */
public class IDEABC {
    private static final String ENCODING = "UTF-8";
    private static final String KEY_ALGORITHM = "IDEA";//������Կ���㷨
    private static final String CIPHER_ALGORITHM = "IDEA/ECB/PKCS5Padding";//�ӽ����㷨 ��ʽ���㷨/����ģʽ/���ģʽ
    /**
     * ������Կ
     */
    public static byte[] getKey() throws NoSuchAlgorithmException{
        Security.addProvider(new BouncyCastleProvider());//��BC���ã�JDK��ȥ��
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        keyGenerator.init(128);//��ʼ����Կ����,128
        SecretKey key =keyGenerator.generateKey();//������Կ
        return key.getEncoded();
    }
    
    /**
     * ��ԭ��Կ���������ֽ�����ת��ΪJava����
     */
    public static Key toKey(byte[] keyByte){
        return new SecretKeySpec(keyByte, KEY_ALGORITHM);
    }
    
    /**
     * IDEA����
     * @param data     ����������
     * @param keyByte  ��Կ
     */
    public static byte[] encrypt(String data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                     NoSuchProviderException, 
                                                                     NoSuchPaddingException, 
                                                                     InvalidKeyException, 
                                                                     IllegalBlockSizeException, 
                                                                     BadPaddingException, 
                                                                     UnsupportedEncodingException {
        Key key = toKey(keyByte);//��ԭ��Կ
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM,"BC");//BC����
        cipher.init(Cipher.ENCRYPT_MODE, key);//���ü���ģʽ���ҳ�ʼ��key
        return cipher.doFinal(data.getBytes(ENCODING));
    }
    
    /**
     * IDEA���ܣ���תΪ16�����ַ�����Base64�����ַ���
     */
    public static String encryptIDEAHex(String data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                           NoSuchProviderException, 
                                                                           NoSuchPaddingException, 
                                                                           InvalidKeyException, 
                                                                           IllegalBlockSizeException, 
                                                                           BadPaddingException, 
                                                                           UnsupportedEncodingException {
        byte[] encodedByte = encrypt(data, keyByte);
        //return new String(Hex.encode(encodedByte));//����BC
        //return new String(org.apache.commons.codec.binary.Hex.encodeHexString(encodedByte));//����CC
        return Base64.encodeBase64String(encodedByte);//����CC��Base64����
    }
    
    /**
     * IDEA����
     * @param data        ����������Ϊ�ֽ�����
     * @param keyByte    ��Կ
     */
    public static byte[] decrypt(byte[] data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                     NoSuchProviderException, 
                                                                     NoSuchPaddingException, 
                                                                     InvalidKeyException, 
                                                                     IllegalBlockSizeException, 
                                                                     BadPaddingException {
        Key key = toKey(keyByte);//��ԭ��Կ
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM,"BC");//BC����
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }
    
    /**
     * IDEA����
     * @param data        ����������Ϊ�ַ���
     * @param keyByte    ��Կ
     */
    public static byte[] decrypt(String data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                     NoSuchProviderException, 
                                                                     NoSuchPaddingException, 
                                                                     InvalidKeyException, 
                                                                     IllegalBlockSizeException, 
                                                                     BadPaddingException  {
        Key key = toKey(keyByte);//��ԭ��Կ
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM,"BC");//BC����
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(Base64.decodeBase64(data));//ע��data������ֱ�Ӳ���data.getByte()����ת��Ϊ�ֽ����飬��������쳣
    }
    
    /**
     * ����
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, 
                                                  InvalidKeyException, 
                                                  InvalidKeySpecException, 
                                                  NoSuchPaddingException, 
                                                  IllegalBlockSizeException, 
                                                  BadPaddingException, 
                                                  UnsupportedEncodingException, 
                                                  NoSuchProviderException, 
                                                  InvalidAlgorithmParameterException {
        String data = "��һ���ù������������ҵ��� ��!";
        /*************����encrypt()��decrypt()**************/
        System.out.println("ԭ��-->"+data);
        byte[] keyByte = IDEABC.getKey(); 
        System.out.println("��Կ-->"+Base64.encodeBase64String(keyByte));//���ｫ�����Ƶ���Կʹ��base64���ܱ��棬��Ҳ����ʵ����ʹ�õķ�ʽ
        byte[] encodedByte = IDEABC.encrypt(data, keyByte);
        System.out.println("���ܺ�-->"+encodedByte);
        byte[] encodedByte2 = IDEABC.encrypt(data, keyByte);
        System.out.println("���ܺ�-->"+encodedByte2);
        byte[] decodedByte = IDEABC.decrypt(encodedByte, keyByte);
        System.out.println("���ܺ�-->"+decodedByte);
        for(int i=0;i<encodedByte.length;i++){
            System.out.println(encodedByte[i]==encodedByte2[i]);
        }
        /*************����encryptIDEAHex()��decrypt()**************/
        System.out.println("ԭ��-->"+data);
        byte[] keyByte3 = IDEABC.getKey(); 
        System.out.println("��Կ-->"+Base64.encodeBase64String(keyByte3));//���ｫ�����Ƶ���Կʹ��base64���ܱ��棬��Ҳ����ʵ����ʹ�õķ�ʽ
        String encodedStr = IDEABC.encryptIDEAHex(data, keyByte3);
        System.out.println("���ܺ�-->"+encodedStr);
        String encodedByte4 = IDEABC.encryptIDEAHex(data, keyByte3);
        System.out.println("���ܺ�-->"+encodedByte4);
        byte[] decodedByte3 = IDEABC.decrypt(Base64.decodeBase64(encodedStr), keyByte3);
        System.out.println("����Byte[]��-->"+decodedByte3);
        byte[] decodedByte4 = IDEABC.decrypt(encodedStr, keyByte3);
        System.out.println("����String��-->"+decodedByte4);
    }
}