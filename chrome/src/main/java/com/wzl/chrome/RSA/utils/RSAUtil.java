package com.wzl.chrome.RSA.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author: wzl
 * @create: 2020/7/7
 * @description: 运用RSA的各种加解密
 */
@Slf4j
public class RSAUtil {
    private static final String SIGN_NAME = "RSA";
    // 这个密钥对本来是应该存在数据库或文件中 为了图方便直接弄成常量
    private static final String PUBLIC_KEY_TEXT = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtrVbWv2a3CA0gKrpU1eOBubcxPtrNtC/\n" +
            "sN1DPp950Qt/rQsboWt/vVDtf6HKwmXax8syFB6FQjZF1kHKGYlDBfYhSrctV/XOEGkCkABRVsRp\n" +
            "3HQ4aL99TEPXe1miD9FiXPStSCblCJ5UVBruAHs1B/Jt9X6giVVagODFtvZck28E5LdLbs7g80v6\n" +
            "AOzmn/GmHh7isf45yW10/f2aq6uiDqJ+uciuaQjLLmjBYPtPf2xnkRxP+Ytu0/Tat4ZxUkBBvgVQ\n" +
            "/OWPIAVJVsAAxO2ox9cN52Jge+bW09NVef5HLtv4oWuP8F6ECXBB0iZjTRhdJRTJlSxmkvzUzHpa\n" +
            "lgkXTwIDAQAB";
    private static final String PRIVATE_KEY_TEXT = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC2tVta/ZrcIDSAqulTV44G5tzE\n" +
            "+2s20L+w3UM+n3nRC3+tCxuha3+9UO1/ocrCZdrHyzIUHoVCNkXWQcoZiUMF9iFKty1X9c4QaQKQ\n" +
            "AFFWxGncdDhov31MQ9d7WaIP0WJc9K1IJuUInlRUGu4AezUH8m31fqCJVVqA4MW29lyTbwTkt0tu\n" +
            "zuDzS/oA7Oaf8aYeHuKx/jnJbXT9/Zqrq6IOon65yK5pCMsuaMFg+09/bGeRHE/5i27T9Nq3hnFS\n" +
            "QEG+BVD85Y8gBUlWwADE7ajH1w3nYmB75tbT01V5/kcu2/iha4/wXoQJcEHSJmNNGF0lFMmVLGaS\n" +
            "/NTMelqWCRdPAgMBAAECggEBAKDusZ8+Dq+h9lz3LjnovwM5SnB/fHp1+MLSwi5gzi3h0P4c8KOI\n" +
            "SqX4qJlpa207xi+BvYSXNY2SvITywg8oJk8T81MacegubOlrdYRdo0eoDg7Ol/k74iPRBfZyhxsj\n" +
            "tV5nblJFYCvtOh4y8jyeSaHMWiqI9OLCucsXMU/tRLHe9Ni9QR5poyt6tpG4U2HDRwTOfRAKA9WO\n" +
            "y8zN3Z/WaKchdIEKoIGSVyyJzqmJguERwkcDRxarNoPqgbuU1wArvugfMwhRKiraNJtd86ki62II\n" +
            "JZhwYTAf8s8bKmbs4539x/ApkUwGtMQpMIY70Am5OpImAc2JbTW60i5SEAUIX1ECgYEA3+d1c9xT\n" +
            "r6I/no9G3xhwdCc1HYdDKCFdze0R7FHW9XSa4vRBaQkHaihqQjnXypkZJIOXwEr+dsLKnxWDqGln\n" +
            "meMLAYvsX/Omnptwa/QH7E3NHMXDJAlUttrIDJG5U9+ES5xdMhpMXUFQfiXhf1lGz15t/QGuour1\n" +
            "64cr1BmPOt0CgYEA0OYm2A+924jq7YpK+q+XgeDSSxE1XK0vVlZ6BEVcHjRAezNc9Oj/7hIq5qCR\n" +
            "84a33M+RATPgHX3EbjcT6wanQblMw8sarqL601RHyhKL+Yew6WK7p9ZJEBLzs/hxCcpI/QFJ7XPn\n" +
            "RdtISTboFDRY6ypi6YvAakhDVJ83NLjyShsCgYAXkDNDIy/HbwrE9A0gU2EaSDoFxoNhyMknb8iQ\n" +
            "FzjJEiFuuwfbNvEJ0gEEwTOdhakot5STzxCijPNn9IqrgZQ+lK9G1WPl4S1qya72HXNsAfARFBrq\n" +
            "QvUgXTvqXrejRQWWM3l3WV8p/UKsU+48SbfzANwQYUnwqUuCIvKQ2rPBHQKBgAux5tjPwMxKlQRp\n" +
            "apswO2YtBPjo5LjkhTLrMneMvoR+XmHHVVvss29LmxVARguBLkfxYufTVKeyA847zSSqQbDfQ9nu\n" +
            "Q/ck7q/oFwDMGwQEQbY/LcxOpdZ9iSE5NnLa3U00xkPB6l+x0BExPrrq11OLEvCKP2Jz2zkglMGh\n" +
            "3QajAoGBALbCpTkt5IHpDOx61w1Dsi9nR16woMpkPvAc6vFJMw7btFsEbAI55XW/K2xizBb4EXIp\n" +
            "7UdiFc3LwGJrovlNb05BhgTX0rv/Pn/azPRhgL1jZYWGYCMZuduxbbPYyi6uod3vbS6f+HAUcbgC\n" +
            "ZJpTKn7BiUHOVvnLyPdrCpuqa20e";

//    public static final String PUBLIC_KEY_TEXT = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhK5CC/0MQqdG9xtMhGP0Cy8qkRw/UK7ktS9cN46V4wsDX5KvlTXyIYDXjiYwKvGVUak2OKZB688Qlz3RRy1E4UNosf8YREGwdNXE5wvG0bf1Qd5RCr/Cr99M3x/Elyp1Z+jFZlFXrbAAQZq/sRh+9l+xn3l8+ChhuTdXKBhpl+8cz4edmGHPWkOuGvoN6lOE8lNzbZtmz5dfbAEtbsOin+/u69ynoIGz4iYL7KnzDrZOOHutZ4++qtiX+Pbhse+h2tPAwgUSOOTKsoEn/zjadOS6XJJ1VeeWnBVSn40jPbYRt3ymApItQcym6mWSnyg+2flLnpdAf7DIQn2Qlqt+VwIDAQAB";
//    public static final String PRIVATE_KEY_TEXT = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCErkIL/QxCp0b3G0yEY/QLLyqRHD9QruS1L1w3jpXjCwNfkq+VNfIhgNeOJjAq8ZVRqTY4pkHrzxCXPdFHLUThQ2ix/xhEQbB01cTnC8bRt/VB3lEKv8Kv30zfH8SXKnVn6MVmUVetsABBmr+xGH72X7GfeXz4KGG5N1coGGmX7xzPh52YYc9aQ64a+g3qU4TyU3Ntm2bPl19sAS1uw6Kf7+7r3KeggbPiJgvsqfMOtk44e61nj76q2Jf49uGx76Ha08DCBRI45MqygSf/ONp05LpcknVV55acFVKfjSM9thG3fKYCki1BzKbqZZKfKD7Z+Uuel0B/sMhCfZCWq35XAgMBAAECggEAGUi/61rwoC8SdfqszTnjAtKdMQQKCM0bZm+9ChVQ+PBbVzYz4aJjHwFXko4ydayOAP7XB1qfi1ltVvT/0amNy8O1yt3K+Q7vmoQ2PrVe0Q5WXZQJK5w5dJyAILEEHK6JqyCPpZzwsXetxNLBnvUFFNxNlA9pwjynys0B/fERBlPTkaBOv91IcHDxEt8l/2YBFxHulMLNzIBrwdMfz/B2QoaRfko0c3uWWt+viiZCOlJvUQcli+zHyqC5O1yii7NGEFqyp93JAq9tGRB/kKdUwwJMx94oZtuTkW+zxefvFOred3RfAb0NTkcFqYjFs2MG2oMsxnmLwH3pWLeQfdbGwQKBgQC6L619DFkDQ1sQ1OBuADY6o0+cbODfRIl0qKSwd2PbdBPUlvUL2EY9uup7vazGKfAt8CM6+kUmjc+57ectoZSHPqDasmMgodgyPljiOiprEJBQDCpU9+fxx+w/t5HKGthW0Ezl3UMg2IvWOjlDivcl8I5Az/NojQ2aNyecBjxKdwKBgQC2bn7M+RVQt6q9n6GHgrjmv4c9S1qmDvmo4J1KNjxqMUZYA16lXHPqnRh2TEOpDxF7+zjuAhR29bA3WfYFbym6oB5rwhmX4Ye2EDeXmEUyhrlkl3mGFABKg548gTXujKhTJ+bz3Cuq/2pDcf5eNAKk0gJAFrmbmc5KYVOs/02DIQKBgQCi/XIG8nRKIwDprzH5mj3e/3CGI9qIGdurQKdLxHiqeOeTR8OlZuO2zpbPVLCXmccksABndQhsQ+EBh37Ft5nq+6ydR2T4ADbfZS0yfnD74Tg0mzHDyHJIexgaf30lTHLvLNLkt9o4OtnP5JCUzGan0/r0ShdwA2tRAc+vCtZk6QKBgFVPcJ57sHsRmJeaKZhMChll4WFJdreG8zsE5qkImdHy/vkzgjQD+vTwx7qySUWqlTuMLIAomtdSZzhLKmA6LqJmNDOiDgPXZHZAOS779wf8tn+S0jJf8g7mY73ZkpXeUuyoETlicU1CqbemfBQjcEURLBo6Rku8bhqcTtvse5vhAoGAfuHzU9bz++EtQgfo2PPTD76erfjsYHGy4SR+S2WwgCBt04WZEWkbympjvpZQAMdD9WCOv3R1fNGLUIcRzrjlUt+HIAxJtW6tWYhL4i+KM4NqUhF+2TesGzYTPP8x8PSaWGT4ruynKrgxR08XaHyH3L/Htd3OMXTasgTPuZNguIw=";

    /**
     * @description: 根据公钥字符串获取公钥 理论上应该是调用处传参过来 这儿使用的常量
     * @author: wzl
     * @createTime: 2020/7/7
     */
    public static RSAPublicKey loadRSAPublicKeyByPublicKeyText() throws Exception {
        // 这儿是将异常抛到全局处理器中处理
        try {
            // 获取X509编码 PUBLIC_KEY_TEXT编码的时候就是用的 base64 所以这儿也是用base64解码 再获取算法工厂要求的X509编码字节数组
            byte[] base64 = Base64.decodeBase64(PUBLIC_KEY_TEXT);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(base64);
            // 获取算法工厂
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_NAME);
            // 获取公钥
            return (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (NoSuchAlgorithmException e) {
            log.error("没有此算法", e);
            throw new Exception("没有此算法");
        } catch (InvalidKeySpecException e) {
            log.error("公钥非法", e);
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            log.error("公钥数据为空", e);
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * @description: 根据私钥字符串获取私钥 理论上应该是调用处传参过来 这儿使用的常量
     * @author: wzl
     * @createTime: 2020/7/7
     */
    public static RSAPrivateKey loadRSAPrivateKeyByPrivateKeyText() throws Exception {
        try {
            byte[] base64 = Base64.decodeBase64(PRIVATE_KEY_TEXT);
            // 注意这儿用的不是X509
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(base64);
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_NAME);
            return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (NoSuchAlgorithmException e) {
            log.error("没有此算法", e);
            throw new Exception("没有此算法");
        } catch (InvalidKeySpecException e) {
            log.error("私钥非法", e);
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            log.error("私钥数据为空", e);
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * @param publicKey 公钥
     * @param source    需要加密的明文资源
     * @description: 一、公钥加密 — 私钥解密
     * @author: wzl
     * @createTime: 2020/7/7
     */
    public static String encrypy(RSAPublicKey publicKey, String source) throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空");
        }
        Cipher cipher = null;
        try {
            // 获取加密对象Cipher
            cipher = Cipher.getInstance(SIGN_NAME);
            // 初始化加密对象
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 执行加密
            byte[] bytes = cipher.doFinal(source.getBytes());
            // 返回密文
            return new BASE64Encoder().encode(bytes);
        } catch (NoSuchAlgorithmException e) {
            log.error("没有此算法", e);
            throw new Exception("没有此算法");
        } catch (NoSuchPaddingException e) {
            log.error("NoSuchPaddingException", e);
            throw new Exception("NoSuchPaddingException");
        } catch (InvalidKeyException e) {
            log.error("公钥非法", e);
            throw new Exception("公钥非法");
        } catch (BadPaddingException e) {
            log.error("明文数据已损坏", e);
            throw new Exception("明文数据已损坏");
        } catch (IllegalBlockSizeException e) {
            log.error("明文长度非法", e);
            throw new Exception("明文长度非法");
        }
    }

    /**
     * @param privateKey 私钥
     * @param ciphertext 加密后得到的密文
     * @description: 一、公钥加密 — 私钥解密
     * @author: wzl
     * @createTime: 2020/7/7
     */
    public static String decrypt(RSAPrivateKey privateKey, String ciphertext) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空");
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(SIGN_NAME);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = cipher.doFinal(decoder.decodeBuffer(ciphertext));
            return new String(bytes);
        } catch (NoSuchAlgorithmException e) {
            log.error("没有此算法", e);
            throw new Exception("没有此算法");
        } catch (NoSuchPaddingException e) {
            log.error("NoSuchPaddingException", e);
            throw new Exception("NoSuchPaddingException");
        } catch (InvalidKeyException e) {
            log.error("解密私钥非法", e);
            throw new Exception("解密私钥非法");
        } catch (BadPaddingException e) {
            log.error("密文数据已损坏", e);
            throw new Exception("密文数据已损坏");
        } catch (IllegalBlockSizeException e) {
            log.error("密文长度非法", e);
            throw new Exception("密文长度非法");
        }
    }

    /**
     * @param privateKey 私钥
     * @param source     需要加密的明文资源
     * @description: 二、私钥加密 — 公钥解密
     * @author: wzl
     * @createTime: 2020/7/7
     */
    public static String encrypt(RSAPrivateKey privateKey, String source) throws Exception {
        if (privateKey == null) {
            throw new Exception("加密私钥为空");
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(SIGN_NAME);
            // 注意参数 加密是 Cipher.ENCRYPT_MODE 解密是Cipher.DECRYPT_MODE
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] bytes = cipher.doFinal(source.getBytes());
            return new BASE64Encoder().encode(bytes);
        } catch (NoSuchAlgorithmException e) {
            log.error("没有此算法", e);
            throw new Exception("没有此算法");
        } catch (NoSuchPaddingException e) {
            log.error("NoSuchPaddingException", e);
            throw new Exception("NoSuchPaddingException");
        } catch (InvalidKeyException e) {
            log.error("加密私钥非法", e);
            throw new Exception("加密私钥非法");
        } catch (BadPaddingException e) {
            log.error("明文数据已损坏", e);
            throw new Exception("明文数据已损坏");
        } catch (IllegalBlockSizeException e) {
            log.error("明文长度非法", e);
            throw new Exception("明文长度非法");
        }
    }

    /**
     * @param publicKey  公钥
     * @param ciphertext 加密后得到的密文
     * @description: 二、私钥加密 — 公钥解密
     * @author: wzl
     * @createTime: 2020/7/7
     */
    public static String decrypt(RSAPublicKey publicKey, String ciphertext) throws Exception {
        if (publicKey == null) {
            throw new Exception("解密公钥为空");
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(SIGN_NAME);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] bytes = new BASE64Decoder().decodeBuffer(ciphertext);
            return new String(cipher.doFinal(bytes));
        } catch (NoSuchAlgorithmException e) {
            log.error("没有此算法", e);
            throw new Exception("没有此算法");
        } catch (NoSuchPaddingException e) {
            log.error("NoSuchPaddingException", e);
            throw new Exception("NoSuchPaddingException");
        } catch (InvalidKeyException e) {
            log.error("解密公钥非法", e);
            throw new Exception("解密公钥非法");
        } catch (BadPaddingException e) {
            log.error("密文数据已损坏", e);
            throw new Exception("密文数据已损坏");
        } catch (IllegalBlockSizeException e) {
            log.error("密文长度非法", e);
            throw new Exception("密文长度非法");
        }
    }

    public static void main(String[] args) throws Exception {
        // 公钥加密 — 私钥解密

        // 明文资源
        String source = "始于脸红、止于眼红";
        // 公钥加密得到的密文
        String encrypyByPublicKey = encrypy(loadRSAPublicKeyByPublicKeyText(), source);
        // 私钥解密得到的明文
        String decryptByPrivateKey = decrypt(loadRSAPrivateKeyByPrivateKeyText(), encrypyByPublicKey);


        // 私钥加密 — 公钥解密

        // 私钥加密得到的密文
        String encryptByPrivateKey = encrypt(loadRSAPrivateKeyByPrivateKeyText(), source);
        // 公钥解密得到的明文
        String decryptByPublicKey = decrypt(loadRSAPublicKeyByPublicKeyText(), encryptByPrivateKey);

        System.out.println("公钥加密 — 私钥解密-----" + source + "-----" + decryptByPrivateKey);
        System.out.println("私钥加密 — 公钥解密-----" + source + "-----" + decryptByPublicKey);
    }

}
