package com.zhu2chu.all.taxonomy.file.crypt;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

/**
 * RSA加解密工具类
 *
 * @author jensvn@qq.com on 2017/11/3.
 * @version 1.0
 * @date 2017/11/3.
 */
public class RSAKitTest {

    /**
     * 测试生成RSA密钥对
     */
    @Test
    public void testGenKeyPair() throws Exception {
        RSAKit.genKeyPair("test");
    }

    /**
     * 测试加解密
     */
    @Test
    public void testEncryption() throws Exception {
        String data = "RSA加密数据有长度限制，使用AES加密数据得到加密Data和KEY，使用RSA加密KEY得到加密Key，数据传输使用加密Key和加密DataRSA加密数据有长度限制，使" +
                "用AES加密数据得到加密Data和KEY，使用RSA加密KEY得到加密Key，数据传输使用加密Key和加密DataRSA加密数据有长度限制，使用AES加密数据得到加密Data和KEY，使用RS" +
                "A加密KEY得到加密Key，数据传输使用加密Key和加密DataRSA加密数据有长度限制，使用AES加密数据得到加密Data和KEY，使用RSA加密KEY得到加密Key，数据传输使用加密Key和" +
                "加密DataRSA加密数据有长度限制，使用AES加密数据得到加密Data和KEY，使用RSA加密KEY得到加密Key，数据传输使用加密Key和加密DataRSA加密数据有长度限制，使用AES加密数" +
                "Key，数据传输使用加密Key和加密DataRSA加密数据有长度限制，使用AES加密数据得到加密Data和KEY，使用RSA加密KEY得到加密Key，数据传输使用加密Key和加密Data";
        // 加载私钥、公钥
        PrivateKey privateKey = RSAKit.loadPrivateKey("test.prikey");
        PublicKey publicKey = RSAKit.loadPublicKey("test.pubkey");
        // 数据加密
        byte[] encryptData = RSAKit.encrypt(privateKey, data.getBytes());
        // 数据传输使用Base64编码传输字符串
        String encryptDataString = Base64.encodeBase64String(encryptData);
        // 数据解密
        byte[] decryptData = RSAKit.decrypt(publicKey, Base64.decodeBase64(encryptDataString));
        System.out.println("加密前:" + data);
        System.out.println("加密后:" + encryptDataString);
        System.out.println("解密后:" + new String(decryptData));
    }

}
