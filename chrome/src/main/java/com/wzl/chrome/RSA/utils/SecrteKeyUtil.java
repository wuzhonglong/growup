package com.wzl.chrome.RSA.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.*;
import java.util.HashMap;
import java.util.Map;


/**
 * @author: wzl
 * @create: 2020/7/7
 * @description: 生成公私密钥对 要么放常量中 要么存文件 存数据库都可以
 */
public class SecrteKeyUtil {
    private static Logger log = LoggerFactory.getLogger(SecrteKeyUtil.class);
    // 指定加密算法
    private static final String SIGN_NAME = "RSA";
    // 指定密钥长度 初始化密钥生成器的时候用
    private static final int KEY_SIZE = 2048;
    // 指定公钥存放文件
    private static final String PUBLIC_KEY_FILE = "PublicKey";
    // 指定私钥存放文件
    private static final String PRIVATE_KEY_FILE = "PrivateKey";

    /**
     * @description: 生成密钥对
     * @author: wzl
     * @createTime: 2020/7/7
     */
    public static Map<String, String> getKeys() throws NoSuchAlgorithmException {
        Map<String, String> keyMap = new HashMap<>();
        // RSA 算法要求有一个可信任的随机数源
        SecureRandom secureRandom = new SecureRandom();
        // 创建密钥对生成器 KeyPairGenerator
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(SIGN_NAME);
        // 初始化生成器
        keyPairGenerator.initialize(KEY_SIZE, secureRandom);
        // 创建密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 获取公钥 并转换
        PublicKey publicKey = keyPair.getPublic();
        byte[] publicKeyEncoded = publicKey.getEncoded();
        String publicKeyText = new BASE64Encoder().encode(publicKeyEncoded);
        keyMap.put("publicKeyText", publicKeyText);
        // 获取私钥 并转换
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] privateKeyEncoded = privateKey.getEncoded();
        String privateKeyText = new BASE64Encoder().encode(privateKeyEncoded);
        keyMap.put("privateKeyText", privateKeyText);
        return keyMap;
    }

    /**
     * @description: 将生成的密钥对保存到数据库
     * @author: wzl
     * @createTime: 2020/7/7
     */
    public void saveToDB() {

    }

    /**
     * @description: 将生成的密钥对保存到文件
     * @author: wzl
     * @createTime: 2020/7/7
     */
    public static void saveToFile() throws Exception {
        // RSA 算法要求有一个可信任的随机数源
        SecureRandom secureRandom = new SecureRandom();
        // 创建密钥对生成器 KeyPairGenerator
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(SIGN_NAME);
        // 初始化生成器
        keyPairGenerator.initialize(KEY_SIZE, secureRandom);
        // 创建密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 获取公钥 并转换
        PublicKey publicKey = keyPair.getPublic();
        // 获取私钥 并转换
        PrivateKey privateKey = keyPair.getPrivate();
        try (ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream("E:\\cdzs-wzl-growup\\chrome\\src\\main\\resources\\key\\"+PUBLIC_KEY_FILE));
             ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream("E:\\cdzs-wzl-growup\\chrome\\src\\main\\resources\\key\\"+PRIVATE_KEY_FILE))) {
            oos1.writeObject(publicKey);
            oos2.writeObject(privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
//        saveToFile();
        getKeys();
    }
}
