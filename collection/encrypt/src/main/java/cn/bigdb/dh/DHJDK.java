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
 * 基于JDK的DH算法，工作模式采用ECB
 */
public class DHJDK {
    private static final String ENCODING = "UTF-8";
    private static final String FDC_KEY_ALGORITHM = "DH";//非对称加密密钥算法
    private static final String DC_KEY_ALGORITHM = "AES";//产生本地密钥的算法（对称加密密钥算法）
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//加解密算法 格式：算法/工作模式/填充模式 注意：ECB不使用IV参数
    private static final int FDC_KEY_SIZE = 512;//非对称密钥长度（512~1024之间的64的整数倍）
    
    /**
     * 生成甲方密钥对
     */
    public static KeyPair initKey() throws NoSuchAlgorithmException{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(FDC_KEY_ALGORITHM);//密钥对生成器
        keyPairGenerator.initialize(FDC_KEY_SIZE);//指定密钥长度
        KeyPair keyPair = keyPairGenerator.generateKeyPair();//生成密钥对
        return keyPair;
    }
    
    /**
     * 生成乙方密钥对
     * @param key 甲方公钥
     */
    public static KeyPair initKey(byte[] key) throws NoSuchAlgorithmException, 
                                                     InvalidKeySpecException, 
                                                     InvalidAlgorithmParameterException{
        KeyFactory keyFactory = KeyFactory.getInstance(FDC_KEY_ALGORITHM);//密钥工厂
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(key));//还原甲方公钥
        DHParameterSpec dHParameterSpec = ((DHPublicKey)publicKey).getParams();
        
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyFactory.getAlgorithm());//乙方密钥对生成器
        keyPairGenerator.initialize(dHParameterSpec);//使用甲方公钥参数初始化乙方密钥对生成器
        KeyPair keyPair = keyPairGenerator.generateKeyPair();//生成密钥对
        return keyPair;
    }
    
    /**
     * DH加密
     * @param data     带加密数据
     * @param keyByte  本地密钥，由getSecretKey(byte[] publicKey, byte[] privateKey)产生
     */
    public static byte[] encrypt(String data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                     NoSuchPaddingException, 
                                                                     InvalidKeyException, 
                                                                     IllegalBlockSizeException, 
                                                                     BadPaddingException, 
                                                                     UnsupportedEncodingException {
        Key key = new SecretKeySpec(keyByte, DC_KEY_ALGORITHM);//生成本地密钥

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);//设置加密模式并且初始化key
        return cipher.doFinal(data.getBytes(ENCODING));
    }
    
    /**
     * DH解密
     * @param data        待解密数据为字节数组
     * @param keyByte    本地密钥，由getSecretKey(byte[] publicKey, byte[] privateKey)产生
     */
    public static byte[] decrypt(byte[] data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                     NoSuchPaddingException, 
                                                                     InvalidKeyException, 
                                                                     IllegalBlockSizeException, 
                                                                     BadPaddingException {
        Key key = new SecretKeySpec(keyByte, DC_KEY_ALGORITHM);//生成本地密钥
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }
    
    /**
     * 根据本方私钥与对方公钥构建本地密钥（即对称加密的密钥）
     * @param publicKey        对方公钥
     * @param privateKey    本方私钥
     */
    public static byte[] getSecretKey(byte[] publicKey, byte[] privateKey) throws NoSuchAlgorithmException, 
                                                                                  InvalidKeySpecException, 
                                                                                  InvalidKeyException{
        KeyFactory keyFactory = KeyFactory.getInstance(FDC_KEY_ALGORITHM);//密钥工厂
        PublicKey pubkey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));//还原公钥
        PrivateKey prikey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));//还原私钥
        
        KeyAgreement keyAgreement = KeyAgreement.getInstance(keyFactory.getAlgorithm());
        keyAgreement.init(prikey);
        keyAgreement.doPhase(pubkey, true);
        return keyAgreement.generateSecret(DC_KEY_ALGORITHM).getEncoded();//生成本地密钥（对称加密的密钥）
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
                                                  InvalidKeySpecException, 
                                                  InvalidAlgorithmParameterException, 
                                                  InvalidKeyException, 
                                                  NoSuchPaddingException, 
                                                  IllegalBlockSizeException, 
                                                  BadPaddingException, 
                                                  UnsupportedEncodingException {
        byte[] pubKey1;//甲方公钥
        byte[] priKey1;//甲方私钥
        byte[] key1;//甲方本地密钥
        byte[] pubKey2;//乙方公钥
        byte[] priKey2;//乙方私钥
        byte[] key2;//乙方本地密钥
        
        /*********************测试是否可以正确生成以上6个key，以及key1与key2是否相等*********************/
        KeyPair keyPair1 = DHJDK.initKey();//生成甲方密钥对
        pubKey1 = DHJDK.getPublicKey(keyPair1);
        priKey1 = DHJDK.getPrivateKey(keyPair1);
        
        KeyPair keyPair2 = DHJDK.initKey(pubKey1);//根据甲方公钥生成乙方密钥对
        pubKey2 = DHJDK.getPublicKey(keyPair2);
        priKey2 = DHJDK.getPrivateKey(keyPair2);
        
        key1 = DHJDK.getSecretKey(pubKey2, priKey1);//使用对方公钥和自己私钥构建本地密钥
        key2 = DHJDK.getSecretKey(pubKey1, priKey2);//使用对方公钥和自己私钥构建本地密钥
        
        System.out.println("甲方公钥pubKey1-->"+Base64.encodeBase64String(pubKey1)+"@@pubKey1.length-->"+pubKey1.length);
        System.out.println("甲方私钥priKey1-->"+Base64.encodeBase64String(priKey1)+"@@priKey1.length-->"+priKey1.length);
        System.out.println("乙方公钥pubKey2-->"+Base64.encodeBase64String(pubKey2)+"@@pubKey2.length-->"+pubKey2.length);
        System.out.println("乙方私钥priKey2-->"+Base64.encodeBase64String(priKey2)+"@@priKey2.length-->"+priKey2.length);
        System.out.println("甲方密钥key1-->"+Base64.encodeBase64String(key1));
        System.out.println("乙方密钥key2-->"+Base64.encodeBase64String(key2));
        
        /*********************测试甲方使用本地密钥加密数据向乙方发送，乙方使用本地密钥解密数据*********************/
        System.out.println("甲方-->乙方");
        String data = "找一个好姑娘啊！";
        byte[] encodeStr = DHJDK.encrypt(data, key1);
        System.out.println("甲方加密后的数据-->"+Base64.encodeBase64String(encodeStr));
        byte[] decodeStr = DHJDK.decrypt(encodeStr, key2);
        System.out.println("乙方解密后的数据-->"+new String(decodeStr,"UTF-8"));
        
        /*********************测试乙方使用本地密钥加密数据向甲方发送，甲方使用本地密钥解密数据*********************/
        System.out.println("乙方-->甲方");
        String data2 = "找一个好姑娘啊！";
        byte[] encodeStr2 = DHJDK.encrypt(data2, key2);
        System.out.println("乙方加密后的数据-->"+Base64.encodeBase64String(encodeStr2));
        byte[] decodeStr2 = DHJDK.decrypt(encodeStr, key1);
        System.out.println("甲方解密后的数据-->"+new String(decodeStr2,"UTF-8"));
    }
}