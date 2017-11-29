package com.zhu2chu.all.taxonomy.file.crypt;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

/**
 * @author jensvn@qq.com on 2017/11/6.
 */
public class RSAKit2 {
    /**
     * 直接从文件加载证书
     *
     * @param certPath crt路径
     * @return
     * @throws Exception
     */
    public static Certificate loadCertificate(String certPath) throws Exception {
        // 读取证书文件的输入流
        InputStream in = new FileInputStream(certPath);
        // 证书格式为x509
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Certificate certificate = certificateFactory.generateCertificate(in);
        in.close();
        return certificate;
    }

    /**
     * 加载密钥库
     *
     * @param keystorePath 密钥库路径
     * @param password     密钥库密码
     */
    public static KeyStore loadKeyStore(String keystorePath, String password) throws Exception {
        // 提供密钥库类型
        KeyStore keyStore = KeyStore.getInstance("JKS");
        // 读取keystore文件的输入流
        InputStream in = new FileInputStream(keystorePath);
        keyStore.load(in, password.toCharArray());
        return keyStore;
    }

    /**
     * 通过密钥库，密钥对的别名，密钥对的密码获得密钥对中的私钥
     *
     * @param keyStore 密钥库
     * @param alias    密钥对的别名
     * @param password 密钥对的口令
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(KeyStore keyStore, String alias, String password) throws Exception {
        return (PrivateKey) keyStore.getKey(alias, password.toCharArray());
    }

    public static byte[] encrypt(Key key, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        cipher.update(data);
        return cipher.doFinal();
    }

    public static byte[] decrypt(Key key, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        cipher.update(data);
        return cipher.doFinal();
    }

}
