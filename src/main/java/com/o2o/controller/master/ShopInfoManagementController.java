package com.o2o.controller.master;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.dto.ShopMessage;
import com.o2o.entity.ShopInfo;
import com.o2o.entity.UserInfo;
import com.o2o.exception.ShopException;
import com.o2o.service.ShopInfoService;
import com.o2o.utils.ImageUtil;
import com.o2o.utils.RequestUtil;
import com.o2o.utils.enums.ShopState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * <h2>实际负责管理店铺信息</h2>
 * <p>1. 负责店铺的新增、删除、修改、添加</p>
 * <p>2. 管理店铺信息的 URL 地址都是不会显示在地址栏中的</p>
 * <p>2.1 这是因为采用转发机制, 地址栏显示的是跳转前的地址, 所以这里的地址命名其实不太有所谓, 前端看不到</p>
 */
@Controller
@RequestMapping(value = "/shop-management")
public class ShopInfoManagementController
{
    @Autowired
    ShopInfoService shopInfoService;

    private static final Logger logger = LoggerFactory.getLogger(ShopInfoManagementController.class);

    /**
     * <p>负责接收前端发送的店铺信息的表单, 并提取表单中的信息, 然后进行存储</p>
     * <p>1. 这里采用 Request 原生类来获取请求中的参数而没有使用 ModelAndView、Model 类</p>
     * <p>1.1 Request 类是最原始的方式, 无论是 ModelAndView 还是 Model 类处理请求的本质肯定是 Servlet</p>
     * <p>1.2 ModelAnd、Model 都是对 Request 原生类的进一步封装</p>
     * <p>2. 返回数据的方式采用的是 Map 封装数据的方式, 也没有采用 ModelAndView、Model 类的方式</p>
     * <p>2.1 ModelAndView、Model 源码中实际都是采用 Map 的形式封装参数的, 实际使用的时候也会发现</p>
     * <p>2.2 Model 只能够返回后端交给前端的数据, 所以通常方法返回值必须是字符串, 也就是返回给前端数据之后需要跳转的地址</p>
     * <p>2.3 ModelAndView 就非常高级, 不仅可以将后端的数据交付给前端, 还可以将需要跳转的视图也返回给前端</p>
     * <p>3. 总结: Request 原始类通常搭配 Map 使用、Model 类通常搭配字符串使用, ModelAndView 自成一家, 这里采用最原生的方式</p>
     * @param request 客户端发送的请求
     * @return JSON 格式的哈希表
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertShopInfo(HttpServletRequest request){
        // 0. 返回信息
        Map<String, Object> map = new HashMap<>();
        // 1. 根据键值对手动提取请求中存储的数据, 前端传递的数据采用 JSON 格式
        // TODO 前端传递的值对应的键必须是这个名字, 否则之后会取不出来
        String json = RequestUtil.getParameterByString(request, "shopinfo");
        // 2. 利用 Jackson 工具类将 JSON 字符串转换成对应的 POJO 实体类
        ObjectMapper mapper = new ObjectMapper();
        ShopInfo shop;
        try {
            shop = mapper.readValue(json, ShopInfo.class);
        }
        catch (IOException e) {
            // 后端捕获异常后正常结束, 所有的错误信息全部交给前端显示
            // TODO 前端在获取后端传递的信息时, 也必须按照这个名字来获取, 否则也是取不出来的
            map.put("success", false);
            map.put("error", e.getMessage());
            return map;
        }
        // 3. 获取图片流
        CommonsMultipartFile cmf;
        // 3.1 文件流解析器获取请求作用域对应的上下文, 最后从上下文中获取图片流
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 3.2 判断请求是否携带了图片流
        if (cmr.isMultipart(request)){
            // TODO 这个强制转换太迷惑了
            MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
            cmf = (CommonsMultipartFile) mhsr.getFile("shopimage");
        }else{
            map.put("success", false);
            map.put("error", "图片上传不可以为空!");
            return map;
        }
        // 4. 注册店铺
        if (shop != null && cmf != null){
            // TODO 4.1 通过 Session 获取店铺对应的老板
            UserInfo master = new UserInfo();
            // 4.2 店铺设置老板
            shop.setMaster(master);
            // 4.3 新增店铺
            ShopMessage message = null;
            try {
                message = shopInfoService.insertShopInfo(shop, cmf.getInputStream(), cmf.getOriginalFilename());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            // 4.4 根据返回信息进行处理
            if (message.getState() == ShopState.SUCCESS.getState()){
                map.put("success", true);
            }else{
                map.put("success", false);
                map.put("message", message.getInfo());
            }
            return map;
        }else{
            map.put("success", false);
            map.put("error", "还有信息没有填入!");
            return map;
        }

    }

    /**
     * <h3>1. 翔仔在这里通过讲述使用流的方式将 cmf 转换成 file</h3>
     * <h3>2. 其目的是为了讲述 thumbnailator 可以接收流对象并将其转换</h3>
     * <h3>3. 所以我们需要对之前的方法进行重构, 因为需要将流对象传递到后端去</h3>
     */
    @Deprecated
    private File imageToFile(InputStream in){
        return null;
    }

}
