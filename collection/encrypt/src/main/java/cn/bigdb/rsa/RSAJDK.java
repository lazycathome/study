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
 * 基于JDK的RSA算法，工作模式采用ECB
 */
public class RSAJDK {
    private static final String ENCODING = "UTF-8";
    private static final String KEY_ALGORITHM = "RSA";//非对称加密密钥算法
    private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";//加解密算法 格式：算法/工作模式/填充模式
    private static final int KEY_SIZE = 512;//非对称密钥长度（512~1024之间的64的整数倍）
    
    /**
     * 还原公钥
     * @param pubKey 二进制公钥
     */
    public static PublicKey toPublicKey(byte[] pubKey) throws NoSuchAlgorithmException, 
                                                              InvalidKeySpecException{
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);//密钥工厂
        return keyFactory.generatePublic(new X509EncodedKeySpec(pubKey));//还原公钥
    }
    
    /**
     * 还原私钥
     * @param priKey 二进制私钥
     */
    public static PrivateKey toPrivateKey(byte[] priKey) throws NoSuchAlgorithmException, 
                                                                InvalidKeySpecException{
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);//密钥工厂
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(priKey));//还原私钥
    }
    
    /**
     * 生成甲方密钥对
     */
    public static KeyPair initKey() throws NoSuchAlgorithmException{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);//密钥对生成器
        keyPairGenerator.initialize(KEY_SIZE);//指定密钥长度
        KeyPair keyPair = keyPairGenerator.generateKeyPair();//生成密钥对
        return keyPair;
    }
    
    /**
     * 私钥加密
     * @param data     待加密数据
     * @param keyByte  私钥
     */
    public static byte[] encryptPriKey(String data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                           InvalidKeySpecException, 
                                                                           NoSuchPaddingException, 
                                                                           InvalidKeyException, 
                                                                           IllegalBlockSizeException, 
                                                                           BadPaddingException, 
                                                                           UnsupportedEncodingException {
        PrivateKey priKey = toPrivateKey(keyByte);//还原私钥

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, priKey);//设置加密模式并且初始化key
        return cipher.doFinal(data.getBytes(ENCODING));
    }
    
    /**
     * 公钥加密
     * @param data        待加密数据
     * @param keyByte    公钥
     */
    public static byte[] encryptPubKey(String data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                           InvalidKeySpecException, 
                                                                           NoSuchPaddingException, 
                                                                           InvalidKeyException, 
                                                                           IllegalBlockSizeException, 
                                                                           BadPaddingException, 
                                                                           UnsupportedEncodingException {
        PublicKey pubKey = toPublicKey(keyByte);//还原公钥

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);//设置加密模式并且初始化key
        return cipher.doFinal(data.getBytes(ENCODING));
    }
    
    /**
     * 私钥解密
     * @param data        待解密数据
     * @param keyByte    私钥
     */
    public static byte[] decryptPriKey(byte[] data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                           InvalidKeySpecException, 
                                                                           NoSuchPaddingException, 
                                                                           InvalidKeyException, 
                                                                           IllegalBlockSizeException, 
                                                                           BadPaddingException {
        PrivateKey priKey = toPrivateKey(keyByte);//还原私钥
        
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return cipher.doFinal(data);
    }
    
    /**
     * 公钥解密
     * @param data
     * @param keyByte    公钥
     */
    public static byte[] decryptPubKey(byte[] data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                           InvalidKeySpecException, 
                                                                           NoSuchPaddingException, 
                                                                           InvalidKeyException, 
                                                                           IllegalBlockSizeException, 
                                                                           BadPaddingException {
        PublicKey pubKey = toPublicKey(keyByte);//还原公钥
        
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }
    
    /**
     * 获取公钥
     */
    public static byte[] getPublicKey(KeyPair keyPair){
        return keyPair.getPublic().getEncoded();
    }
    
    /**
     * 获取私钥
     */
    public static byte[] getPrivateKey(KeyPair keyPair){
        return keyPair.getPrivate().getEncoded();
    }
    
    /**
     * 测试
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, 
                                                  InvalidKeyException, 
                                                  InvalidKeySpecException, 
                                                  NoSuchPaddingException, 
                                                  IllegalBlockSizeException, 
                                                  BadPaddingException, 
                                                  UnsupportedEncodingException{
        byte[] pubKey1;//甲方公钥
        byte[] priKey1;//甲方私钥
        
        /*********************测试是否可以正确生成以上2个key*********************/
        KeyPair keyPair1 = RSAJDK.initKey();//生成甲方密钥对
        pubKey1 = RSAJDK.getPublicKey(keyPair1);
        priKey1 = RSAJDK.getPrivateKey(keyPair1);
        
        System.out.println("甲方公钥pubKey1-->"+Base64.encodeBase64String(pubKey1)+"@@pubKey1.length-->"+pubKey1.length);
        System.out.println("甲方私钥priKey1-->"+Base64.encodeBase64String(priKey1)+"@@priKey1.length-->"+priKey1.length);
        
        /*********************测试甲方使用私钥加密数据向乙方发送，乙方使用公钥解密数据*********************/
        System.out.println("甲方-->乙方");
        String data = "找一个好姑娘啊！";
        byte[] encodeStr = RSAJDK.encryptPriKey(data, priKey1);
        System.out.println("甲方加密后的数据-->"+Base64.encodeBase64String(encodeStr));
        byte[] decodeStr = RSAJDK.decryptPubKey(encodeStr, pubKey1);
        System.out.println("乙方解密后的数据-->"+new String(decodeStr,"UTF-8"));
        
        /*********************测试乙方使用私钥加密数据向甲方发送，甲方使用公钥解密数据*********************/
        System.out.println("乙方-->甲方");
        String data2 = "找一个好姑娘啊！";
        byte[] encodeStr2 = RSAJDK.encryptPubKey(data2, pubKey1);
        System.out.println("乙方加密后的数据-->"+Base64.encodeBase64String(encodeStr2));
        byte[] decodeStr2 = RSAJDK.decryptPriKey(encodeStr2, priKey1);
        System.out.println("甲方解密后的数据-->"+new String(decodeStr2,"UTF-8"));
    }
}