package com.fuyusakaiori.csms.util.wechat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <h2>微信请求校验工具</h2>
 */
public class SignUtil
{
    // 微信访问应用程序的令牌
    private static final String token = "o2o";

    /**
     * <h3>验证签名是否合法</h3>
     * @param signature 微信传递的签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce){
        // 1. token、timestamp、nonce 作为计算签名的三个参数
        String[] params = {token, timestamp, nonce};
        // 排序之后再合并（为什么要排序?）
        StringBuilder sb = new StringBuilder();
        for (String param : params) {
            sb.append(param);
        }
        MessageDigest md = null;
        String confirm = null;

        try {
            // 选择加密的算法
            md = MessageDigest.getInstance("SHA-1");
            // 使用加密的算法加密内容
            byte[] digest = md.digest(sb.toString().getBytes());
            // 将加密后的内容转换成十六进制
            confirm = byteToStr(digest);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return confirm != null && confirm.equals(signature);
    }

    /**
     * <h3>将字节数组转换为十六进制字符串</h3>
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * <h3>将字节转换为十六进制字符串</h3>
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        return new String(tempArr);
    }

}
