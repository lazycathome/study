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
 * 基于BC的AES算法，工作模式采用CTR
 */
public class AESBC {
    private static final String ENCODING = "UTF-8";
    private static final String KEY_ALGORITHM = "AES";//产生密钥的算法
    private static final String CIPHER_ALGORITHM = "AES/CTR/PKCS7Padding";//加解密算法 格式：算法/工作模式/填充模式 注意：ECB不使用IV参数，CTR使用
    private static final byte[] IV = "zhaojigangzhaoji".getBytes();//注意：这里需要是十六个字符,用于CTR
    /**
     * 产生密钥
     */
    public static byte[] getKey() throws NoSuchAlgorithmException{
        Security.addProvider(new BouncyCastleProvider());//加入BCProvider
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        keyGenerator.init(256);//初始化密钥长度,128,192,256（选用192和256的时候需要配置无政策限制权限文件--JDK6）
        SecretKey key =keyGenerator.generateKey();//产生密钥
        return key.getEncoded();
    }
    
    /**
     * 还原密钥：二进制字节数组转换为Java对象
     */
    public static Key toKey(byte[] keyByte){
        return new SecretKeySpec(keyByte, KEY_ALGORITHM);
    }
    
    /**
     * AES加密
     * @param data     带加密数据
     * @param keyByte  密钥
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
        Key key = toKey(keyByte);//还原密钥
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM,"BC");//使用BC
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));//设置加密模式并且初始化key,加入IV用于BC下的CTR
        return cipher.doFinal(data.getBytes(ENCODING));
    }
    
    /**
     * AES加密，并转为16进制字符串或Base64编码字符串
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
        //return new String(Hex.encode(encodedByte));//借助BC
        //return new String(org.apache.commons.codec.binary.Hex.encodeHexString(encodedByte));//借助CC
        return Base64.encodeBase64String(encodedByte);//借助CC的Base64编码
    }
    
    /**
     * AES解密
     * @param data        待解密数据为字节数组
     * @param keyByte    密钥
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
        Key key = toKey(keyByte);//还原密钥
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM,"BC");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));//设置加密模式并且初始化key,加入IV用于BC下的CTR
        return cipher.doFinal(data);
    }
    
    /**
     * AES解密
     * @param data        待解密数据为字符串
     * @param keyByte    密钥
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
        Key key = toKey(keyByte);//还原密钥
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM,"BC");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));//设置加密模式并且初始化key,加入IV用于BC下的CTR
        return cipher.doFinal(Base64.decodeBase64(data));
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
                                                  UnsupportedEncodingException, 
                                                  NoSuchProviderException, 
                                                  InvalidAlgorithmParameterException {
        String data = "找一个好姑娘做老婆是我的梦 想!";
        /*************测试encrypt()、decrypt()**************/
        System.out.println("原文-->"+data);
        byte[] keyByte = AESBC.getKey(); 
        System.out.println("密钥-->"+Base64.encodeBase64String(keyByte));//这里将二进制的密钥使用base64加密保存，这也是在实际中使用的方式
        byte[] encodedByte = AESBC.encrypt(data, keyByte);
        System.out.println("加密后-->"+encodedByte);
        byte[] encodedByte2 = AESBC.encrypt(data, keyByte);
        System.out.println("加密后-->"+encodedByte2);
        byte[] decodedByte = AESBC.decrypt(encodedByte, keyByte);
        System.out.println("解密后-->"+decodedByte);
        for(int i=0;i<encodedByte.length;i++){
            System.out.println(encodedByte[i]==encodedByte2[i]);
        }
        /*************测试encryptAESHex()、decrypt()**************/
        System.out.println("原文-->"+data);
        byte[] keyByte3 = AESBC.getKey(); 
        System.out.println("密钥-->"+Base64.encodeBase64String(keyByte3));//这里将二进制的密钥使用base64加密保存，这也是在实际中使用的方式
        String encodedStr = AESBC.encryptAESHex(data, keyByte3);
        System.out.println("加密后-->"+encodedStr);
        String encodedByte4 = AESBC.encryptAESHex(data, keyByte3);
        System.out.println("加密后-->"+encodedByte4);
        byte[] decodedByte3 = AESBC.decrypt(Base64.decodeBase64(encodedStr), keyByte3);
        System.out.println("解密Byte[]后-->"+decodedByte3);
        byte[] decodedByte4 = AESBC.decrypt(encodedStr, keyByte3);
        System.out.println("解密String后-->"+decodedByte4);
    }
}