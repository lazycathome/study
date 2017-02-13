package cn.bigdb.rsa;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * ����BC��RSA����ǩ���㷨
 */
public class RSACoderBC {
    private static final String ENCODING = "UTF-8";
    private static final String KEY_ALGORITHM = "RSA";//�ǶԳƼ�����Կ�㷨
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";//ָ������ǩ���㷨�����Ի���SHA1withRSA��SHA256withRSA��
    private static final int KEY_SIZE = 512;//�ǶԳ���Կ���ȣ�512~1024֮���64����������
    
    /**
     * ���ɷ��ͷ���Կ��
     */
    public static KeyPair initKey() throws NoSuchAlgorithmException{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);//��Կ��������
        keyPairGenerator.initialize(KEY_SIZE);//ָ����Կ����
        KeyPair keyPair = keyPairGenerator.generateKeyPair();//������Կ��
        return keyPair;
    }
    
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
     * ˽Կ����(ǩ��)
     * @param data     ����������
     * @param keyByte  ˽Կ
     */
    public static byte[] encryptPriKey(String data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                           InvalidKeySpecException, 
                                                                           InvalidKeyException, 
                                                                           SignatureException, 
                                                                           UnsupportedEncodingException {
        PrivateKey priKey = toPrivateKey(keyByte);//��ԭ˽Կ

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data.getBytes(ENCODING));
        
        return signature.sign();
    }
    
    /**
     * ��Կ����(��֤)
     * @param data        ԭ�ģ����������ݣ�Ҳ��Ϊ����У�����ݡ���
     * @param keyByte    ��Կ
     * @param sign        ���ģ�Ҳ������ǩ������
     */
    public static boolean decryptPubKey(String data, byte[] keyByte, byte[] sign) throws NoSuchAlgorithmException, 
                                                                                         InvalidKeySpecException, 
                                                                                         InvalidKeyException, 
                                                                                         SignatureException, 
                                                                                         UnsupportedEncodingException {
        PublicKey pubKey = toPublicKey(keyByte);//��ԭ��Կ
        
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data.getBytes(ENCODING));
        
        return signature.verify(sign);
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
                                                  SignatureException, 
                                                  UnsupportedEncodingException {
        byte[] pubKey1;//�׷���Կ
        byte[] priKey1;//�׷�˽Կ
        
        /*********************�����Ƿ������ȷ��������2��key*********************/
        KeyPair keyPair1 = RSACoderBC.initKey();//���ɼ׷���Կ��
        pubKey1 = RSACoderBC.getPublicKey(keyPair1);
        priKey1 = RSACoderBC.getPrivateKey(keyPair1);
        
        System.out.println("�׷���ԿpubKey1-->"+Base64.encodeBase64String(pubKey1)+"@@pubKey1.length-->"+pubKey1.length);
        System.out.println("�׷�˽ԿpriKey1-->"+Base64.encodeBase64String(priKey1)+"@@priKey1.length-->"+priKey1.length);
        
        /*********************���Լ׷�ʹ��˽Կ�����������ҷ����ͣ��ҷ�ʹ�ù�Կ��������*********************/
        System.out.println("�׷�-->�ҷ�");
        String data = "��һ���ù��ﰡ������𣬺���";
        byte[] encodeStr = RSACoderBC.encryptPriKey(data, priKey1);//���ܣ�ǩ����
        System.out.println("�׷����ܺ������-->"+Base64.encodeBase64String(encodeStr)+"@@encodeStr.length-->"+encodeStr.length);
        boolean decodeStr = RSACoderBC.decryptPubKey(data, pubKey1, encodeStr);
        System.out.println("�ҷ�������-->"+decodeStr);
    }
}