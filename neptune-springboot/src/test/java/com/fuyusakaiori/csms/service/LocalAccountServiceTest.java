package com.fuyusakaiori.csms.service;

import com.fuyusakaiori.csms.dto.Message;
import com.fuyusakaiori.csms.entity.LocalAccount;
import com.fuyusakaiori.csms.entity.UserInfo;
import com.fuyusakaiori.csms.util.enums.State;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalAccountServiceTest
{
    @Resource
    private LocalAccountService localAccountService;

    @Test
    public void loginByUsernameAndPasswordServiceTest(){
        Message<LocalAccount> message = localAccountService.loginByUsernameAndPassword("123456", "123456");
        System.out.println(message.getElement());
        Assert.assertEquals(State.SUCCESS.getState(), message.getState());
    }

    @Test
    public void findLocalAccountByUserServiceTest(){
        Message<LocalAccount> message = localAccountService.findLocalAccountByUser(1);
        System.out.println(message.getElement());
        Assert.assertEquals(State.SUCCESS.getState(), message.getState());
    }

    @Test
    public void registerLocalAccountServiceTest(){
        LocalAccount account = new LocalAccount();
        account.setUsername("1234567");
        account.setPassword("1234567");
        UserInfo user = new UserInfo();
        user.setUserId(2);
        account.setUserInfo(user);
        Message<LocalAccount> message = localAccountService.registerLocalAccount(account);
        System.out.println(message.getElement());
        Assert.assertEquals(message.getState(), State.SUCCESS.getState());
    }

    @Test
    public void updateLocalAccountServiceTest(){
        Message<LocalAccount> message = localAccountService.updateLocalAccount(2, "1234567", "1234567", "12345678", new Date());
        System.out.println(message.getElement());
        Assert.assertEquals(State.SUCCESS.getState(), message.getState());
    }
}
