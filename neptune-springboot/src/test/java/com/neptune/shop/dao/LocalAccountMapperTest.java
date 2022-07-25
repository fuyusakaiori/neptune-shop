package com.neptune.shop.dao;

import com.neptune.shop.core.dao.LocalAccountMapper;
import com.neptune.shop.core.entity.LocalAccount;
import com.neptune.shop.core.entity.UserInfo;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;


@SpringBootTest
public class LocalAccountMapperTest
{
    @Resource
    private LocalAccountMapper localAccountMapper;

    @Test
    public void loginByUsernameAndPasswordMapperTest(){
        LocalAccount localAccount = localAccountMapper.loginByUsernameAndPassword("123456", "12345678");
        System.out.println(localAccount);
        Assert.assertEquals(1, (int)localAccount.getLocalId());
    }

    @Test
    public void findLocalAccountByUserMapperTest(){
        LocalAccount account = localAccountMapper.findLocalAccountByUser(1);
        System.out.println(account);
        Assert.assertEquals(1, (int)account.getLocalId());
    }

    @Test
    @Ignore
    public void registerLocalAccountMapperTest(){
        LocalAccount account = new LocalAccount();
        account.setUsername("123456");
        account.setPassword("123456");
        UserInfo user = new UserInfo();
        user.setUserId(1);
        account.setUserInfo(user);
        account.setCreateTime(new Date());
        account.setUpdateTime(new Date());
        int result = localAccountMapper.registerLocalAccount(account);
        Assert.assertEquals(1, result);
    }

    @Test
    @Ignore
    public void updateLocalAccountMapperTest(){
        int result = localAccountMapper.updateLocalAccount(1, "123456", "123456", "12345678", new Date());

        Assert.assertEquals(result, 1);
    }
}
