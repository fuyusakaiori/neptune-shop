package com.o2o.controller.wechat;

import com.o2o.utils.RequestUtil;
import com.o2o.utils.wechat.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <h2>解析微信公众号发送的请求</h2>
 */
@Controller
@RequestMapping("/wechat")
public class WechatController
{
    private final static Logger logger = LoggerFactory.getLogger(WechatController.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        logger.debug("开始验证微信号的令牌...");

        String signature = RequestUtil.getParameterByString(request, "signature");
        String timestamp = RequestUtil.getParameterByString(request, "timestamp");
        String nonce = RequestUtil.getParameterByString(request, "nonce");
        String echostr = RequestUtil.getParameterByString(request, "echostr");

        PrintWriter out = null;

        try {
            out = response.getWriter();
            if (SignUtil.checkSignature(signature, timestamp, nonce)){
                logger.debug("微信令牌验证成功...");
                out.println(echostr);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (out != null) out.close();
        }
    }
}
