package cn.bigdb.dh;


import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
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
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * ����JDK��DH�㷨������ģʽ����ECB
 */
public class DHJDK {
    private static final String ENCODING = "UTF-8";
    private static final String FDC_KEY_ALGORITHM = "DH";//�ǶԳƼ�����Կ�㷨
    private static final String DC_KEY_ALGORITHM = "AES";//����������Կ���㷨���ԳƼ�����Կ�㷨��
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//�ӽ����㷨 ��ʽ���㷨/����ģʽ/���ģʽ ע�⣺ECB��ʹ��IV����
    private static final int FDC_KEY_SIZE = 512;//�ǶԳ���Կ���ȣ�512~1024֮���64����������
    
    /**
     * ���ɼ׷���Կ��
     */
    public static KeyPair initKey() throws NoSuchAlgorithmException{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(FDC_KEY_ALGORITHM);//��Կ��������
        keyPairGenerator.initialize(FDC_KEY_SIZE);//ָ����Կ����
        KeyPair keyPair = keyPairGenerator.generateKeyPair();//������Կ��
        return keyPair;
    }
    
    /**
     * �����ҷ���Կ��
     * @param key �׷���Կ
     */
    public static KeyPair initKey(byte[] key) throws NoSuchAlgorithmException, 
                                                     InvalidKeySpecException, 
                                                     InvalidAlgorithmParameterException{
        KeyFactory keyFactory = KeyFactory.getInstance(FDC_KEY_ALGORITHM);//��Կ����
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(key));//��ԭ�׷���Կ
        DHParameterSpec dHParameterSpec = ((DHPublicKey)publicKey).getParams();
        
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyFactory.getAlgorithm());//�ҷ���Կ��������
        keyPairGenerator.initialize(dHParameterSpec);//ʹ�ü׷���Կ������ʼ���ҷ���Կ��������
        KeyPair keyPair = keyPairGenerator.generateKeyPair();//������Կ��
        return keyPair;
    }
    
    /**
     * DH����
     * @param data     ����������
     * @param keyByte  ������Կ����getSecretKey(byte[] publicKey, byte[] privateKey)����
     */
    public static byte[] encrypt(String data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                     NoSuchPaddingException, 
                                                                     InvalidKeyException, 
                                                                     IllegalBlockSizeException, 
                                                                     BadPaddingException, 
                                                                     UnsupportedEncodingException {
        Key key = new SecretKeySpec(keyByte, DC_KEY_ALGORITHM);//���ɱ�����Կ

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);//���ü���ģʽ���ҳ�ʼ��key
        return cipher.doFinal(data.getBytes(ENCODING));
    }
    
    /**
     * DH����
     * @param data        ����������Ϊ�ֽ�����
     * @param keyByte    ������Կ����getSecretKey(byte[] publicKey, byte[] privateKey)����
     */
    public static byte[] decrypt(byte[] data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                     NoSuchPaddingException, 
                                                                     InvalidKeyException, 
                                                                     IllegalBlockSizeException, 
                                                                     BadPaddingException {
        Key key = new SecretKeySpec(keyByte, DC_KEY_ALGORITHM);//���ɱ�����Կ
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }
    
    /**
     * ���ݱ���˽Կ��Է���Կ����������Կ�����ԳƼ��ܵ���Կ��
     * @param publicKey        �Է���Կ
     * @param privateKey    ����˽Կ
     */
    public static byte[] getSecretKey(byte[] publicKey, byte[] privateKey) throws NoSuchAlgorithmException, 
                                                                                  InvalidKeySpecException, 
                                                                                  InvalidKeyException{
        KeyFactory keyFactory = KeyFactory.getInstance(FDC_KEY_ALGORITHM);//��Կ����
        PublicKey pubkey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));//��ԭ��Կ
        PrivateKey prikey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));//��ԭ˽Կ
        
        KeyAgreement keyAgreement = KeyAgreement.getInstance(keyFactory.getAlgorithm());
        keyAgreement.init(prikey);
        keyAgreement.doPhase(pubkey, true);
        return keyAgreement.generateSecret(DC_KEY_ALGORITHM).getEncoded();//���ɱ�����Կ���ԳƼ��ܵ���Կ��
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
                                                  InvalidKeySpecException, 
                                                  InvalidAlgorithmParameterException, 
                                                  InvalidKeyException, 
                                                  NoSuchPaddingException, 
                                                  IllegalBlockSizeException, 
                                                  BadPaddingException, 
                                                  UnsupportedEncodingException {
        byte[] pubKey1;//�׷���Կ
        byte[] priKey1;//�׷�˽Կ
        byte[] key1;//�׷�������Կ
        byte[] pubKey2;//�ҷ���Կ
        byte[] priKey2;//�ҷ�˽Կ
        byte[] key2;//�ҷ�������Կ
        
        /*********************�����Ƿ������ȷ��������6��key���Լ�key1��key2�Ƿ����*********************/
        KeyPair keyPair1 = DHJDK.initKey();//���ɼ׷���Կ��
        pubKey1 = DHJDK.getPublicKey(keyPair1);
        priKey1 = DHJDK.getPrivateKey(keyPair1);
        
        KeyPair keyPair2 = DHJDK.initKey(pubKey1);//���ݼ׷���Կ�����ҷ���Կ��
        pubKey2 = DHJDK.getPublicKey(keyPair2);
        priKey2 = DHJDK.getPrivateKey(keyPair2);
        
        key1 = DHJDK.getSecretKey(pubKey2, priKey1);//ʹ�öԷ���Կ���Լ�˽Կ����������Կ
        key2 = DHJDK.getSecretKey(pubKey1, priKey2);//ʹ�öԷ���Կ���Լ�˽Կ����������Կ
        
        System.out.println("�׷���ԿpubKey1-->"+Base64.encodeBase64String(pubKey1)+"@@pubKey1.length-->"+pubKey1.length);
        System.out.println("�׷�˽ԿpriKey1-->"+Base64.encodeBase64String(priKey1)+"@@priKey1.length-->"+priKey1.length);
        System.out.println("�ҷ���ԿpubKey2-->"+Base64.encodeBase64String(pubKey2)+"@@pubKey2.length-->"+pubKey2.length);
        System.out.println("�ҷ�˽ԿpriKey2-->"+Base64.encodeBase64String(priKey2)+"@@priKey2.length-->"+priKey2.length);
        System.out.println("�׷���Կkey1-->"+Base64.encodeBase64String(key1));
        System.out.println("�ҷ���Կkey2-->"+Base64.encodeBase64String(key2));
        
        /*********************���Լ׷�ʹ�ñ�����Կ�����������ҷ����ͣ��ҷ�ʹ�ñ�����Կ��������*********************/
        System.out.println("�׷�-->�ҷ�");
        String data = "��һ���ù��ﰡ��";
        byte[] encodeStr = DHJDK.encrypt(data, key1);
        System.out.println("�׷����ܺ������-->"+Base64.encodeBase64String(encodeStr));
        byte[] decodeStr = DHJDK.decrypt(encodeStr, key2);
        System.out.println("�ҷ����ܺ������-->"+new String(decodeStr,"UTF-8"));
        
        /*********************�����ҷ�ʹ�ñ�����Կ����������׷����ͣ��׷�ʹ�ñ�����Կ��������*********************/
        System.out.println("�ҷ�-->�׷�");
        String data2 = "��һ���ù��ﰡ��";
        byte[] encodeStr2 = DHJDK.encrypt(data2, key2);
        System.out.println("�ҷ����ܺ������-->"+Base64.encodeBase64String(encodeStr2));
        byte[] decodeStr2 = DHJDK.decrypt(encodeStr, key1);
        System.out.println("�׷����ܺ������-->"+new String(decodeStr2,"UTF-8"));
    }
}