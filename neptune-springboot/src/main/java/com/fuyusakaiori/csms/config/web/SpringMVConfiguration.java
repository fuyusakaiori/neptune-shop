package com.fuyusakaiori.csms.config.web;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class SpringMVConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getCommonsMultipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        resolver.setMaxUploadSize(20971520);
        resolver.setMaxInMemorySize(20971520);
        return resolver;
    }

    @Value("${kaptcha.border}")
    private String border;

    @Value("${kaptcha.image.width}")
    private String width;

    @Value("${kaptcha.image.height}")
    private String height;

    @Value("${kaptcha.textproducer.font.color}")
    private String color;

    @Value("${kaptcha.textproducer.char.string}")
    private String string;

    @Value("${kaptcha.textproducer.char.length}")
    private String length;

    @Value("${kaptcha.noise.color}")
    private String noise;

    @Value("${kaptcha.textproducer.font.names}")
    private String names;

    @Bean
    public ServletRegistrationBean<KaptchaServlet> getServletRegistrationBean(){
        ServletRegistrationBean<KaptchaServlet> kaptcha = new ServletRegistrationBean<>(new KaptchaServlet(), "/kaptcha");
        kaptcha.addInitParameter("kaptcha.border", border);
        kaptcha.addInitParameter("kaptcha.image.width", width);
        kaptcha.addInitParameter("kaptcha.image.height", height);
        kaptcha.addInitParameter("kaptcha.textproducer.font.color", color);
        kaptcha.addInitParameter("kaptcha.textproducer.char.string", string);
        kaptcha.addInitParameter("kaptcha.textproducer.char.length", length);
        kaptcha.addInitParameter("kaptcha.noise.color", noise);
        kaptcha.addInitParameter("kaptcha.textproducer.font.names", names);
        return kaptcha;
    }
}
