package com.neptune.shop.core.util.captcha;

import cloud.tianai.captcha.slider.SliderCaptchaApplication;
import cloud.tianai.captcha.template.slider.validator.SliderCaptchaTrack;
import cloud.tianai.captcha.vo.CaptchaResponse;
import cloud.tianai.captcha.vo.SliderCaptchaVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class SliderCaptchaController {

    @Resource
    private SliderCaptchaApplication slider;

    @GetMapping("/generation")
    @ResponseBody
    public CaptchaResponse<SliderCaptchaVO> genCaptcha(HttpServletRequest request) {
        CaptchaResponse<SliderCaptchaVO> response = slider.generateSliderCaptcha();
        log.debug(response.toString());
        return response;
    }

    @PostMapping("/slider-check")
    @ResponseBody
    public boolean checkCaptcha(@RequestParam("id") String id,
                                @RequestBody SliderCaptchaTrack sliderCaptchaTrack,
                                HttpServletRequest request) {
        return slider.matching(id, sliderCaptchaTrack);
    }

}
