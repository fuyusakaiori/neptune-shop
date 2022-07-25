package com.neptune.shop.core.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/local")
public class LocalAccountPageController
{
    @RequestMapping(value = "/bind")
    public String bindLocalAccountPage(){
        return "local/accountbind";
    }

    @RequestMapping(value = "/cp")
    public String changePasswordPage(){
        return "local/changepsw";
    }

    @RequestMapping(value = "/la")
    public String loginPage(){
        return "local/login";
    }

}
