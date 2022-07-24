package com.o2o.service;

import com.o2o.BaseTest;
import com.o2o.dto.Message;
import com.o2o.entity.LocalAccount;
import com.o2o.entity.UserInfo;
import com.o2o.utils.enums.State;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class LocalAccountServiceTest extends BaseTest
{
    @Autowired
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
