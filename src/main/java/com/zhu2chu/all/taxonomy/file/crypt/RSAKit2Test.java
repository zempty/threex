package com.zhu2chu.all.taxonomy.file.crypt;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.UUID;

import org.junit.Test;

import com.xiaoleilu.hutool.lang.Base64;

/**
 * RSA加解密工具测试类
 *
 * @author jensvn@qq.com on 2017/11/3.
 * @version 1.0
 * @date 2017/11/3.
 */
public class RSAKit2Test {

    /**
     * 测试加解密
     */
    @Test
    public void testEncryption() throws Exception {
        String data = "由于RSA加密数据有长度限制，此处利用随机生成的uuid作为明文key，使用RSA加密key得到密文key，使用AES方式加密数据得到密文data，数据传输使用密文key和密文data，解密密文data使用解密后的明文key";
        System.out.println("data.getBytes().length:" + data.getBytes().length);
        String aesKey = UUID.randomUUID().toString();
        String iv = "abcdefghijklmnop";
        // 密钥库
        KeyStore keyStore = RSAKit2.loadKeyStore("test.keystore", "123456");
        // 私钥
        PrivateKey privateKey = RSAKit2.getPrivateKey(keyStore, "test", "123456");
        // 公钥：服务端的公钥是通过KeyStore获得
        PublicKey publicKey_server = keyStore.getCertificate("test").getPublicKey();
        // 公钥：客户端的公钥是通过证书获得
        PublicKey publicKey_client = RSAKit2.loadCertificate("test.crt").getPublicKey();

        // RSA加密Key
        byte[] encryptKeyBytes = RSAKit2.encrypt(privateKey, aesKey.getBytes());
        // Base64编码后传输
        String encryptKey = Base64.encode(encryptKeyBytes);
        // AES加密Data
        byte[] encryptDataBytes = AESKit.encrypt(data.getBytes(), aesKey.getBytes(), iv.getBytes());
        // Base64编码后传输
        String encryptData = Base64.encode(encryptDataBytes);

        // RSA解密Key
        byte[] decryptKey = RSAKit2.decrypt(publicKey_client, Base64.decode(encryptKey));
        byte[] decryptData = AESKit.decrypt(encryptDataBytes, decryptKey, iv.getBytes());

        System.out.println("明文key：\n" + aesKey);
        System.out.println("明文data：\n" + data);
        System.out.println("密文key：\n" + encryptKey);
        System.out.println("密文data：\n" + encryptKey);
        System.out.println("密文key&密文data（用于传输）：\n" + encryptKey + "&" + encryptData);
        System.out.println("解密key：\n" + new String(decryptKey));
        System.out.println("解密data：\n" + new String(decryptData));
    }

}
