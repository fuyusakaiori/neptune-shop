package com.fuyusakaiori.csms.util.encrypt;

// JDK 9 之后 BASE64Decoder 就被移除了
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * <h2>DES 对称加密工具类</h2>
 */
public class DESUtil
{
    // 采用的加密算法
    private final static String ALGORITHM = "DES";
    // 加密算法的种子
    private final static String KEY_STRING = "myKey";
    // 编码格式
    private final static String CHARSETNAME = "UTF-8";

    // 密钥
    private static Key key;

    // Java 自带的对称加密工具类
    static {
        try {
            // 获取加密算法实例
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            // 确定加密算法采用的加密策略
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            // 确定加密策略是基于什么加密的
            random.setSeed(KEY_STRING.getBytes());
            // 设置加密算法的加密策略
            generator.init(random);
            // 生成密钥Z
            key = generator.generateKey();
            System.out.println(Arrays.toString(key.getEncoded()));
            System.out.println(key.getFormat());
            System.out.println();
            // 手动释放静态代码块的对象(有用吗?)
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * <h3>根据密钥加密</h3>
     * @return 加密后的信息
     */
    public static String getEncryption(String str){
        // Java 编码工具类
        Encoder encoder = Base64.getEncoder();
        try {
            // 按照 UTF-8 编码
            byte[] bytes = str.getBytes(CHARSETNAME);
            // 获取加密对象实例
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 初始化加密对象的需要的基本信息
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 加密对象利用密钥开始加密
            byte[] encryption = cipher.doFinal(bytes);
            // 将加密后的字节数组利用工具类生成对应的字符串
            return encoder.encodeToString(encryption);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * <h3>获取解密信息</h3>
     * @return 解密的信息
     */
    public static String getDecryption(String str){
        Decoder decoder = Base64.getDecoder();
        try {
            // 将加密信息变成字节
            byte[] bytes = decoder.decode(str);
            // 获取加密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 解密
            byte[] decryption = cipher.doFinal(bytes);

            return new String(decryption, CHARSETNAME);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String username = getEncryption("work");
        String password = getEncryption("123456");
        System.out.println(username);
        System.out.println(password);
    }
}
