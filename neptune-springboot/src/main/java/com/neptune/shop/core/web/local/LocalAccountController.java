package com.neptune.shop.core.web.local;

import com.neptune.shop.core.dto.Message;
import com.neptune.shop.core.entity.LocalAccount;
import com.neptune.shop.core.entity.UserInfo;
import com.neptune.shop.core.exception.LocalAccountException;
import com.neptune.shop.core.service.LocalAccountService;
import com.neptune.shop.core.util.RequestUtil;
import com.neptune.shop.core.util.captcha.CheckKaptcha;
import com.neptune.shop.core.util.enums.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/local", method = {RequestMethod.GET, RequestMethod.POST})
public class LocalAccountController
{
    @Resource
    private LocalAccountService localAccountService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> registerLocalAccount(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        // 1. 验证码检验
        if (!CheckKaptcha.checkVerifyCode(request)){
            map.put("success", false);
            map.put("message", "验证码错误");
            return map;
        }
        // 2. 验证账号名和密码是否为空, 以及是否已经使用微信登陆
        String username = RequestUtil.getParameterByString(request, "username");
        String password = RequestUtil.getParameterByString(request, "password");
        UserInfo user = (UserInfo) request.getSession().getAttribute("user");
        if (username == null || password == null || user == null || user.getUserId() == null){
            map.put("success", false);
            map.put("message", "用户名和密码为空!没有权限注册!");
            return map;
        }
        try {
            // 3. 填充注册信息
            LocalAccount account = new LocalAccount();
            account.setUsername(username);
            account.setPassword(password);
            account.setUserInfo(user);
            // 4. 注册账号
            Message<LocalAccount> message = localAccountService.registerLocalAccount(account);
            // 5. 验证是否注册成功
            if (message.getState() == State.SUCCESS.getState()){
                map.put("success", true);
                map.put("message", message.getState() + ":\t 注册成功");
            }else{
                map.put("success", false);
                map.put("message", message.getInfo());
            }
        }
        catch (Exception e) {
            map.put("success", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changePassword(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        if (!CheckKaptcha.checkVerifyCode(request)){
            map.put("success", false);
            map.put("message", "验证码错误!");
            return map;
        }
        String username = RequestUtil.getParameterByString(request, "username");
        String password = RequestUtil.getParameterByString(request, "password");
        String newPassword = RequestUtil.getParameterByString(request, "newPassword");
        UserInfo user = (UserInfo) request.getSession().getAttribute("user");
        if (username == null || password == null || newPassword == null || password.equals(newPassword) || user == null || user.getUserId() == null){
            map.put("success", false);
            map.put("message", "账号和密码为空或者没有权限修改密码!");
            return map;
        }
        // 开始修改密码
        try {
            // 验证输入的账号密码是否和当前的账号一致
            Message<LocalAccount> account = localAccountService.findLocalAccountByUser(user.getUserId());
            if (!account.getElement().getUsername().equals(username) || !account.getElement().getPassword().equals(password)){
                map.put("success", false);
                map.put("message", "输入的账号或者密码错误!");
                return map;
            }
            Message<LocalAccount> message = localAccountService.updateLocalAccount(user.getUserId(), username, password, newPassword, new Date());
            if (message.getState() == State.SUCCESS.getState()){
                map.put("success", true);
                map.put("message", message.getInfo() + ":\t更新成功!");
            }else{
                map.put("success", false);
                map.put("message", message.getInfo() + ":\t更新失败!");
            }
        }
        catch (LocalAccountException e) {
            map.put("success", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loginLocalAccount(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        if (!CheckKaptcha.checkVerifyCode(request)){
            map.put("success", false);
            map.put("message", "验证码错误!");
            return map;
        }
        String username = RequestUtil.getParameterByString(request, "username");
        String password = RequestUtil.getParameterByString(request, "password");
        if (username == null || password == null){
            map.put("success", false);
            map.put("message", "账号或者密码为空!");
            return map;
        }
        // 登陆账号
        try {
            Message<LocalAccount> message = localAccountService.loginByUsernameAndPassword(username, password);
            if (message.getState() == State.SUCCESS.getState()){
                map.put("success", true);
                map.put("message", message.getInfo() + ":\t登陆成功!");
                // 将登陆账号存入 Session
                request.getSession().setAttribute("user", message.getElement().getUserInfo());
            }else{
                map.put("success", false);
                map.put("message", message.getInfo() + ":\t登陆失败!");
            }
        }
        catch (Exception e) {
            map.put("success", false);
            map.put("message", e.getMessage());
        }

        return map;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> logoutLocalAccount(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        request.setAttribute("user", null);
        map.put("success", true);
        map.put("message", "账号注销成功!");
        return map;
    }
}
