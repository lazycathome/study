package cn.bigdb.rsa;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

/**
 * ����JDK��RSA�㷨������ģʽ����ECB
 */
public class RSAJDK {
    private static final String ENCODING = "UTF-8";
    private static final String KEY_ALGORITHM = "RSA";//�ǶԳƼ�����Կ�㷨
    private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";//�ӽ����㷨 ��ʽ���㷨/����ģʽ/���ģʽ
    private static final int KEY_SIZE = 512;//�ǶԳ���Կ���ȣ�512~1024֮���64����������
    
    /**
     * ��ԭ��Կ
     * @param pubKey �����ƹ�Կ
     */
    public static PublicKey toPublicKey(byte[] pubKey) throws NoSuchAlgorithmException, 
                                                              InvalidKeySpecException{
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);//��Կ����
        return keyFactory.generatePublic(new X509EncodedKeySpec(pubKey));//��ԭ��Կ
    }
    
    /**
     * ��ԭ˽Կ
     * @param priKey ������˽Կ
     */
    public static PrivateKey toPrivateKey(byte[] priKey) throws NoSuchAlgorithmException, 
                                                                InvalidKeySpecException{
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);//��Կ����
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(priKey));//��ԭ˽Կ
    }
    
    /**
     * ���ɼ׷���Կ��
     */
    public static KeyPair initKey() throws NoSuchAlgorithmException{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);//��Կ��������
        keyPairGenerator.initialize(KEY_SIZE);//ָ����Կ����
        KeyPair keyPair = keyPairGenerator.generateKeyPair();//������Կ��
        return keyPair;
    }
    
    /**
     * ˽Կ����
     * @param data     ����������
     * @param keyByte  ˽Կ
     */
    public static byte[] encryptPriKey(String data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                           InvalidKeySpecException, 
                                                                           NoSuchPaddingException, 
                                                                           InvalidKeyException, 
                                                                           IllegalBlockSizeException, 
                                                                           BadPaddingException, 
                                                                           UnsupportedEncodingException {
        PrivateKey priKey = toPrivateKey(keyByte);//��ԭ˽Կ

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, priKey);//���ü���ģʽ���ҳ�ʼ��key
        return cipher.doFinal(data.getBytes(ENCODING));
    }
    
    /**
     * ��Կ����
     * @param data        ����������
     * @param keyByte    ��Կ
     */
    public static byte[] encryptPubKey(String data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                           InvalidKeySpecException, 
                                                                           NoSuchPaddingException, 
                                                                           InvalidKeyException, 
                                                                           IllegalBlockSizeException, 
                                                                           BadPaddingException, 
                                                                           UnsupportedEncodingException {
        PublicKey pubKey = toPublicKey(keyByte);//��ԭ��Կ

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);//���ü���ģʽ���ҳ�ʼ��key
        return cipher.doFinal(data.getBytes(ENCODING));
    }
    
    /**
     * ˽Կ����
     * @param data        ����������
     * @param keyByte    ˽Կ
     */
    public static byte[] decryptPriKey(byte[] data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                           InvalidKeySpecException, 
                                                                           NoSuchPaddingException, 
                                                                           InvalidKeyException, 
                                                                           IllegalBlockSizeException, 
                                                                           BadPaddingException {
        PrivateKey priKey = toPrivateKey(keyByte);//��ԭ˽Կ
        
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return cipher.doFinal(data);
    }
    
    /**
     * ��Կ����
     * @param data
     * @param keyByte    ��Կ
     */
    public static byte[] decryptPubKey(byte[] data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                           InvalidKeySpecException, 
                                                                           NoSuchPaddingException, 
                                                                           InvalidKeyException, 
                                                                           IllegalBlockSizeException, 
                                                                           BadPaddingException {
        PublicKey pubKey = toPublicKey(keyByte);//��ԭ��Կ
        
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }
    
    /**
     * ��ȡ��Կ
     */
    public static byte[] getPublicKey(KeyPair keyPair){
        return keyPair.getPublic().getEncoded();
    }
    
    /**
     * ��ȡ˽Կ
     */
    public static byte[] getPrivateKey(KeyPair keyPair){
        return keyPair.getPrivate().getEncoded();
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
                                                  UnsupportedEncodingException{
        byte[] pubKey1;//�׷���Կ
        byte[] priKey1;//�׷�˽Կ
        
        /*********************�����Ƿ������ȷ��������2��key*********************/
        KeyPair keyPair1 = RSAJDK.initKey();//���ɼ׷���Կ��
        pubKey1 = RSAJDK.getPublicKey(keyPair1);
        priKey1 = RSAJDK.getPrivateKey(keyPair1);
        
        System.out.println("�׷���ԿpubKey1-->"+Base64.encodeBase64String(pubKey1)+"@@pubKey1.length-->"+pubKey1.length);
        System.out.println("�׷�˽ԿpriKey1-->"+Base64.encodeBase64String(priKey1)+"@@priKey1.length-->"+priKey1.length);
        
        /*********************���Լ׷�ʹ��˽Կ�����������ҷ����ͣ��ҷ�ʹ�ù�Կ��������*********************/
        System.out.println("�׷�-->�ҷ�");
        String data = "��һ���ù��ﰡ��";
        byte[] encodeStr = RSAJDK.encryptPriKey(data, priKey1);
        System.out.println("�׷����ܺ������-->"+Base64.encodeBase64String(encodeStr));
        byte[] decodeStr = RSAJDK.decryptPubKey(encodeStr, pubKey1);
        System.out.println("�ҷ����ܺ������-->"+new String(decodeStr,"UTF-8"));
        
        /*********************�����ҷ�ʹ��˽Կ����������׷����ͣ��׷�ʹ�ù�Կ��������*********************/
        System.out.println("�ҷ�-->�׷�");
        String data2 = "��һ���ù��ﰡ��";
        byte[] encodeStr2 = RSAJDK.encryptPubKey(data2, pubKey1);
        System.out.println("�ҷ����ܺ������-->"+Base64.encodeBase64String(encodeStr2));
        byte[] decodeStr2 = RSAJDK.decryptPriKey(encodeStr2, priKey1);
        System.out.println("�׷����ܺ������-->"+new String(decodeStr2,"UTF-8"));
    }
}