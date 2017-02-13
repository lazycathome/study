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
 * 基于BC的RSA数字签名算法
 */
public class RSACoderBC {
    private static final String ENCODING = "UTF-8";
    private static final String KEY_ALGORITHM = "RSA";//非对称加密密钥算法
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";//指定数字签名算法（可以换成SHA1withRSA或SHA256withRSA）
    private static final int KEY_SIZE = 512;//非对称密钥长度（512~1024之间的64的整数倍）
    
    /**
     * 生成发送方密钥对
     */
    public static KeyPair initKey() throws NoSuchAlgorithmException{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);//密钥对生成器
        keyPairGenerator.initialize(KEY_SIZE);//指定密钥长度
        KeyPair keyPair = keyPairGenerator.generateKeyPair();//生成密钥对
        return keyPair;
    }
    
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
     * 私钥加密(签名)
     * @param data     待加密数据
     * @param keyByte  私钥
     */
    public static byte[] encryptPriKey(String data, byte[] keyByte) throws NoSuchAlgorithmException, 
                                                                           InvalidKeySpecException, 
                                                                           InvalidKeyException, 
                                                                           SignatureException, 
                                                                           UnsupportedEncodingException {
        PrivateKey priKey = toPrivateKey(keyByte);//还原私钥

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data.getBytes(ENCODING));
        
        return signature.sign();
    }
    
    /**
     * 公钥解密(验证)
     * @param data        原文（待加密数据，也成为“待校验数据”）
     * @param keyByte    公钥
     * @param sign        密文（也称作“签名”）
     */
    public static boolean decryptPubKey(String data, byte[] keyByte, byte[] sign) throws NoSuchAlgorithmException, 
                                                                                         InvalidKeySpecException, 
                                                                                         InvalidKeyException, 
                                                                                         SignatureException, 
                                                                                         UnsupportedEncodingException {
        PublicKey pubKey = toPublicKey(keyByte);//还原公钥
        
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data.getBytes(ENCODING));
        
        return signature.verify(sign);
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
                                                  SignatureException, 
                                                  UnsupportedEncodingException {
        byte[] pubKey1;//甲方公钥
        byte[] priKey1;//甲方私钥
        
        /*********************测试是否可以正确生成以上2个key*********************/
        KeyPair keyPair1 = RSACoderBC.initKey();//生成甲方密钥对
        pubKey1 = RSACoderBC.getPublicKey(keyPair1);
        priKey1 = RSACoderBC.getPrivateKey(keyPair1);
        
        System.out.println("甲方公钥pubKey1-->"+Base64.encodeBase64String(pubKey1)+"@@pubKey1.length-->"+pubKey1.length);
        System.out.println("甲方私钥priKey1-->"+Base64.encodeBase64String(priKey1)+"@@priKey1.length-->"+priKey1.length);
        
        /*********************测试甲方使用私钥加密数据向乙方发送，乙方使用公钥解密数据*********************/
        System.out.println("甲方-->乙方");
        String data = "找一个好姑娘啊！你好吗，孩子";
        byte[] encodeStr = RSACoderBC.encryptPriKey(data, priKey1);//加密（签名）
        System.out.println("甲方加密后的数据-->"+Base64.encodeBase64String(encodeStr)+"@@encodeStr.length-->"+encodeStr.length);
        boolean decodeStr = RSACoderBC.decryptPubKey(data, pubKey1, encodeStr);
        System.out.println("乙方检验结果-->"+decodeStr);
    }
}