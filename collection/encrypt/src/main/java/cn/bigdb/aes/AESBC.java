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
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * ����BC��AES�㷨������ģʽ����CTR
 */
public class AESBC {
    private static final String ENCODING = "UTF-8";
    private static final String KEY_ALGORITHM = "AES";//������Կ���㷨
    private static final String CIPHER_ALGORITHM = "AES/CTR/PKCS7Padding";//�ӽ����㷨 ��ʽ���㷨/����ģʽ/���ģʽ ע�⣺ECB��ʹ��IV������CTRʹ��
    private static final byte[] IV = "zhaojigangzhaoji".getBytes();//ע�⣺������Ҫ��ʮ�����ַ�,����CTR
    /**
     * ������Կ
     */
    public static byte[] getKey() throws NoSuchAlgorithmException{
        Security.addProvider(new BouncyCastleProvider());//����BCProvider
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        keyGenerator.init(256);//��ʼ����Կ����,128,192,256��ѡ��192��256��ʱ����Ҫ��������������Ȩ���ļ�--JDK6��
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
     * AES����
     * @param data     ����������
     * @param keyByte  ��Կ
     */
    public static byte[] encrypt(String data, byte[] keyByte) throws InvalidKeyException, 
                                                                     NoSuchAlgorithmException, 
                                                                     InvalidKeySpecException, 
                                                                     NoSuchPaddingException, 
                                                                     IllegalBlockSizeException, 
                                                                     BadPaddingException, 
                                                                     UnsupportedEncodingException, 
                                                                     NoSuchProviderException, 
                                                                     InvalidAlgorithmParameterException{
        Key key = toKey(keyByte);//��ԭ��Կ
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM,"BC");//ʹ��BC
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));//���ü���ģʽ���ҳ�ʼ��key,����IV����BC�µ�CTR
        return cipher.doFinal(data.getBytes(ENCODING));
    }
    
    /**
     * AES���ܣ���תΪ16�����ַ�����Base64�����ַ���
     */
    public static String encryptAESHex(String data, byte[] keyByte) throws InvalidKeyException, 
                                                                           NoSuchAlgorithmException, 
                                                                           InvalidKeySpecException, 
                                                                           NoSuchPaddingException, 
                                                                           IllegalBlockSizeException, 
                                                                           BadPaddingException, 
                                                                           UnsupportedEncodingException, 
                                                                           NoSuchProviderException, 
                                                                           InvalidAlgorithmParameterException {
        byte[] encodedByte = encrypt(data, keyByte);
        //return new String(Hex.encode(encodedByte));//����BC
        //return new String(org.apache.commons.codec.binary.Hex.encodeHexString(encodedByte));//����CC
        return Base64.encodeBase64String(encodedByte);//����CC��Base64����
    }
    
    /**
     * AES����
     * @param data        ����������Ϊ�ֽ�����
     * @param keyByte    ��Կ
     */
    public static byte[] decrypt(byte[] data, byte[] keyByte) throws InvalidKeyException, 
                                                                     NoSuchAlgorithmException, 
                                                                     InvalidKeySpecException, 
                                                                     NoSuchPaddingException, 
                                                                     IllegalBlockSizeException, 
                                                                     BadPaddingException, 
                                                                     UnsupportedEncodingException, 
                                                                     NoSuchProviderException, 
                                                                     InvalidAlgorithmParameterException {
        Key key = toKey(keyByte);//��ԭ��Կ
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM,"BC");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));//���ü���ģʽ���ҳ�ʼ��key,����IV����BC�µ�CTR
        return cipher.doFinal(data);
    }
    
    /**
     * AES����
     * @param data        ����������Ϊ�ַ���
     * @param keyByte    ��Կ
     */
    public static byte[] decrypt(String data, byte[] keyByte) throws InvalidKeyException, 
                                                                     NoSuchAlgorithmException, 
                                                                     InvalidKeySpecException, 
                                                                     NoSuchPaddingException, 
                                                                     IllegalBlockSizeException, 
                                                                     BadPaddingException, 
                                                                     UnsupportedEncodingException, 
                                                                     NoSuchProviderException, 
                                                                     InvalidAlgorithmParameterException {
        Key key = toKey(keyByte);//��ԭ��Կ
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM,"BC");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));//���ü���ģʽ���ҳ�ʼ��key,����IV����BC�µ�CTR
        return cipher.doFinal(Base64.decodeBase64(data));
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
        byte[] keyByte = AESBC.getKey(); 
        System.out.println("��Կ-->"+Base64.encodeBase64String(keyByte));//���ｫ�����Ƶ���Կʹ��base64���ܱ��棬��Ҳ����ʵ����ʹ�õķ�ʽ
        byte[] encodedByte = AESBC.encrypt(data, keyByte);
        System.out.println("���ܺ�-->"+encodedByte);
        byte[] encodedByte2 = AESBC.encrypt(data, keyByte);
        System.out.println("���ܺ�-->"+encodedByte2);
        byte[] decodedByte = AESBC.decrypt(encodedByte, keyByte);
        System.out.println("���ܺ�-->"+decodedByte);
        for(int i=0;i<encodedByte.length;i++){
            System.out.println(encodedByte[i]==encodedByte2[i]);
        }
        /*************����encryptAESHex()��decrypt()**************/
        System.out.println("ԭ��-->"+data);
        byte[] keyByte3 = AESBC.getKey(); 
        System.out.println("��Կ-->"+Base64.encodeBase64String(keyByte3));//���ｫ�����Ƶ���Կʹ��base64���ܱ��棬��Ҳ����ʵ����ʹ�õķ�ʽ
        String encodedStr = AESBC.encryptAESHex(data, keyByte3);
        System.out.println("���ܺ�-->"+encodedStr);
        String encodedByte4 = AESBC.encryptAESHex(data, keyByte3);
        System.out.println("���ܺ�-->"+encodedByte4);
        byte[] decodedByte3 = AESBC.decrypt(Base64.decodeBase64(encodedStr), keyByte3);
        System.out.println("����Byte[]��-->"+decodedByte3);
        byte[] decodedByte4 = AESBC.decrypt(encodedStr, keyByte3);
        System.out.println("����String��-->"+decodedByte4);
    }
}