package com.o2o.utils;

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

/**
 * <h2>处理图片的工具类</h2>
 * <p>1. 采用 Thumbnailator 工具类存储图片</p>
 * <p>2. 图片是存储在服务端的非项目文件夹下, 防止项目每次编译的时候清除上传的文件?</p>
 */
public class ImageUtil
{
    private final static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
    // 格式化日期工具类: 确保不会出现线程安全问题, 最后设置成不可变对象
    private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmm");
    // 生成随机数的工具类
    private final static Random RANDOM = new Random();

    /**
     * <p>1. Service 层单元测试的时候无法直接提供 CommonsMultipartFile</p>
     * <p>2. 而只能够提供 File 类来进行单元测试, 但是前端传递的是 CommonsMultipartFile</p>
     * <p>3. 虽然 File 不能够向 CommonsMultipartFile 转换, 但是反过来却是很容易的</p>
     * <p>4. 注: 另外的解决方案, 可以采用 Mocking</p>
     * @param cmf Spring 提供的传输图片文件的工具类
     * @return CommonsMultipartFile 转换生成的文件类
     */
    public static File transferToFile(CommonsMultipartFile cmf){
        File file = new File(Objects.requireNonNull(cmf.getOriginalFilename()));
        try {
            cmf.transferTo(file);
        }
        catch (IOException e) {
            logger.error(e.getMessage());
        }

        return file;
    }

    /**
     * <p>获取用户上传的图片流, 然后将其存储在目录中, 并且返回存储图片的相对路径</p>
     * <p>图片的相对路径是需要存储在数据库中, 所以需要返回</p>
     * <p>不返回绝对路径的原因是因为程序的可移植性</p>
     * @param file Spring 提供的传输图片文件的工具类
     * @param target 保存图片的用户专属的目录地址
     * @return 返回图片存储的地址以及图片名
     */
    public static String generateThumbnailator(File file, String target){
        // 1. 赋予用户上传的图片随机的名称（有些图床采用了哈希化的方式重命名）
        String filename = generateFileName();
        // 2. 获取用户上传的图片的扩展名
        String extension = getFileExtension(file);
        // 3. 判断存储图片的目标路径是否存在相应的用户文件夹, 如果没有就需要创建
        makeImageDirectory(target);
        // 4. 存储图片的相对路径
        String relative = target + filename + extension;
        logger.debug("相对路径:\t" + relative);
        // 5. 存储图片的绝对路径
        File destination = new File(PathUtil.getImageBasePath() + relative);
        logger.debug("绝对路径:\t" + destination);
        // 6. 图片存储在绝对路径定位的目录下
        try {
            Thumbnails.of(file)
                    .size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("D:\\图片\\watermark.png")), 0.25f)
                    .outputQuality(0.8)
                    .toFile(destination);
        }
        catch (IOException e) {
            logger.error(e.toString());
        }
        return relative;
    }

    /**
     * 判断存储用户图片的目录是否存在, 如果不存在就需要创建相应的目录
     * @param target 存储图片的目录的相对路径
     */
    private static void makeImageDirectory(String target) {
        // 传入的是存储图片目录的绝对路径
        Path path = Paths.get(PathUtil.getImageBasePath() + target);
        if (!Files.exists(path)){
            try {
                // 递归创建
                Files.createDirectories(path);
            }
            catch (IOException e) {
                logger.error(e.toString());
            }
        }
    }

    /**
     * 获取文件扩展名
     */
    private static String getFileExtension(File file) {
        String filename = file.getName();
        // 避免因为各种原因导致传入的文件流为空
        logger.debug("文件名:\t" + filename);
        return filename.substring(filename.lastIndexOf("."));
    }

    /**
     * 随机生成文件的名称: 时间 + 随机数
     * @return 随机名称
     */
    private static String generateFileName() {
        return RANDOM.nextInt(10000) + SDF.format(new Date());
    }


    /**
     * 测试 Thumbnailator 工具类
     */
    public static void main(String[] args) throws IOException
    {
        // 利用类加载获取项目的资源路径
        String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println("资源路径: " + basePath);
        // 1. 传入需要上传的图片的路径
        Thumbnails.of(new File("D:\\图片\\涩图\\1637216699245.jpg"))
                // 2. 调整图片大小
                .size(1000, 1000)
                // 3. 添加水印: 指定位置, 指定水印图片的地址, 指定透明度
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("D:\\图片\\watermark.png")), 0.25f)
                // 4. 设置图片的压缩率
                .outputQuality(0.8).toFile("D:\\图片\\添加了水印的图片.png");
        // TODO 等待解决的问题: 读取水印的路径使用相对路径存在问题, 水印的位置也存在一点问题
        logger.debug(System.getProperty("os.name"));

    }

}
