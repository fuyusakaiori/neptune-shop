package com.o2o.utils;

/**
 * 辅助图片工具类读取路径的工具类
 */
public class PathUtil
{
    // 获取文件系统的路径分隔符
    public static String separator = System.getProperty("file.separator");

    /**
     * <p>获取图片在服务器上存储的主路径</p>
     * <p>路径样例: D:/project/image/</p>
     * <p>1. 不同的操作系统的文件系统生成的文件路径是不同的, 所以为了可移植性, 需要判断操作系统类型</p>
     * <p>2. 路径使用的分隔符也会因为操作系统的不同而不同, 所以也需要做出相应的区分</p>
     * @return 文件存储的主路径
     */
    public static String getImageBasePath(){
        // 1. 获取运行进程的操作系统
        String os = System.getProperty("os.name");
        // 2. 根据不同的操作系统设置不同的图片存储路径
        String basePath;
        if (os.startsWith("Windows"))
            // 2.1 Windows 文件系统的路径是反斜杠（"\"）,但是在语法中这个表示的是转义字符, 所以不能够直接使用
            // 2.2 可以直接将路径再次采用转义字符写死, 也可以采用字符串替换的方式更改, 后者比较合理
            basePath = "D:/Project/Image/";
        else
            // 2.3 类 Unix 的操作系统都是采用斜杠（"/"）
            basePath = "/home/project/image";
        // 3. 将路径分隔符替换为文件系统的分隔符
        basePath = basePath.replace("/", separator);
        return basePath;
    }


    /**
     * 每个商店都有自己专属的存储图片的目录, 这个目录根据商店的编号生成
     * @param shopId 商店编号
     * @return 每个用户存储图片的目录地址
     */
    public static String getShopImagePath(int shopId){
        // 返回存储商店图片的目录地址
        return "/upload/item/shop/" + shopId + "/";
    }
}
