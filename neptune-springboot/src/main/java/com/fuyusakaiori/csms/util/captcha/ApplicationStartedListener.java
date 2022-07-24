package com.fuyusakaiori.csms.util.captcha;

import cloud.tianai.captcha.slider.SliderCaptchaApplication;
import cloud.tianai.captcha.template.slider.ResourceStore;
import cloud.tianai.captcha.template.slider.SliderCaptchaResourceManager;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

public class ApplicationStartedListener implements ApplicationListener<ApplicationStartedEvent> {

    @Resource
    private SliderCaptchaApplication slider;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        // 1. 获取滑动验证码管理器
        SliderCaptchaResourceManager manager = slider.getSliderCaptchaResourceManager();
        // 2. 获取滑动验证码的资源存储中心
        ResourceStore store = manager.getResourceStore();
        // 3. 清除此前存放的资源
        store.clearResources();
        // 4. 存放图片
        store.addResource(new cloud.tianai.captcha.template.slider.Resource("classpath", "bgimages/a.jpg"));
        store.addResource(new cloud.tianai.captcha.template.slider.Resource("classpath", "bgimages/b.jpg"));
        store.addResource(new cloud.tianai.captcha.template.slider.Resource("classpath", "bgimages/c.jpg"));
        store.addResource(new cloud.tianai.captcha.template.slider.Resource("classpath", "bgimages/d.jpg"));
        store.addResource(new cloud.tianai.captcha.template.slider.Resource("classpath", "bgimages/e.jpg"));
        store.addResource(new cloud.tianai.captcha.template.slider.Resource("classpath", "bgimages/g.jpg"));
        store.addResource(new cloud.tianai.captcha.template.slider.Resource("classpath", "bgimages/h.jpg"));
        store.addResource(new cloud.tianai.captcha.template.slider.Resource("classpath", "bgimages/i.jpg"));
        store.addResource(new cloud.tianai.captcha.template.slider.Resource("classpath", "bgimages/j.jpg"));
    }
}
