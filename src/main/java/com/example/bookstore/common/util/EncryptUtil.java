package com.example.bookstore.common.util;

import org.apache.commons.codec.binary.Hex;
import org.springframework.util.DigestUtils;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * @author chaoluo
 */
public class EncryptUtil {

    /**
     * MD5方法
     *
     * @param text 明文
     * @return 密文
     */
    public static String md5(String text) {
        if (text == null || text.length() == 0) {
            return "";
        }
        //加密后的字符串
        String encodeStr = DigestUtils.md5DigestAsHex((text).getBytes());
        return encodeStr;
    }

    /**
     * 3Des加密
     *
     * @param key
     * @param data
     * @return
     */
    public static String encrypt3DES(String key, String data) {
        try {
            byte[] bytesKey = Hex.decodeHex(key);
            //2.转换KEY
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(bytesKey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESEde");
            Key convertKey = factory.generateSecret(deSedeKeySpec);

            //3.加密
            Cipher cipher = Cipher.getInstance("DESEde/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, convertKey);
            byte[] result = cipher.doFinal(data.getBytes());
            return Hex.encodeHexString(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 3Des解密
     *
     * @param key
     * @param data
     * @return
     */
    public static String decrypt3DES(String key, String data) {
        try {
            byte[] bytesKey = Hex.decodeHex(key);
            byte[] dataBytes = Hex.decodeHex(data);
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(bytesKey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESEde");
            Key convertKey = factory.generateSecret(deSedeKeySpec);

            Cipher cipher = Cipher.getInstance("DESEde/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, convertKey);
            byte[] result = cipher.doFinal(dataBytes);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 生成DES key
     *
     * @return
     */
    public static String generate3DESKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESEde");
            keyGenerator.init(168);
            keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytesKey = secretKey.getEncoded();
            return Hex.encodeHexString(bytesKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * SHA1加密
     * @param inputData
     */
    public  static String SHA1(String inputData) {
        // 获取指定摘要算法的messageDigest对象
        try {
            // 此处的sha代表sha1
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            // 调用digest方法，进行加密操作
            byte[] cipherBytes = messageDigest.digest(inputData.getBytes());
            String cipherStr = Hex.encodeHexString(cipherBytes);
            return cipherStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
