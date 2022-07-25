package com.neptune.shop.core.service.impl;


import com.neptune.shop.core.dao.LocalAccountMapper;
import com.neptune.shop.core.dto.Message;
import com.neptune.shop.core.entity.LocalAccount;
import com.neptune.shop.core.exception.LocalAccountException;
import com.neptune.shop.core.service.LocalAccountService;
import com.neptune.shop.core.util.MD5;
import com.neptune.shop.core.util.enums.State;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Slf4j
public class LocalAccountServiceImpl implements LocalAccountService
{
    @Autowired
    @Qualifier("localAccountMapper")
    private LocalAccountMapper localAccountMapper;

    @Override
    public Message<LocalAccount> loginByUsernameAndPassword(String username, String password) {
        if (username == null || password == null)
            return new Message<>(State.FAILURE);
        LocalAccount account = localAccountMapper.loginByUsernameAndPassword(username, MD5.getMd5(password));
        return new Message<>(State.SUCCESS, account);
    }

    @Override
    public Message<LocalAccount> findLocalAccountByUser(int userId) {
        if (userId <= 0)
            return new Message<>(State.NULL);
        LocalAccount account = localAccountMapper.findLocalAccountByUser(userId);
        return new Message<>(State.SUCCESS, account);
    }

    @Override
    @Transactional
    public Message<LocalAccount> registerLocalAccount(LocalAccount account) throws LocalAccountException
    {
        // 1. 传递的用户账号及其密码不可以为空
        if (account == null || account.getUsername() == null || account.getPassword() == null)
            return new Message<>(State.NULL);
        // 2. 账号对应的用户不可以为空
        if (account.getUserInfo() == null || account .getUserInfo().getUserId() <= 0)
            return new Message<>(State.FAILURE);
        // 3. 查询注册的账号是否重复
        LocalAccount confirm = localAccountMapper.findLocalAccountByUser(account.getUserInfo().getUserId());
        if (confirm != null)
            return new Message<>(State.REPEAT);
        try {
            account.setCreateTime(new Date());
            account.setUpdateTime(new Date());
            // 密码加密
            account.setPassword(MD5.getMd5(account.getPassword()));
            int result = localAccountMapper.registerLocalAccount(account);
            if (result <= 0)
                throw new LocalAccountException("创建账号失败!");
        }
        catch (Exception e) {
            throw new LocalAccountException(e.getMessage());
        }
        return new Message<>(State.SUCCESS, account);
    }

    @Override
    @Transactional
    public Message<LocalAccount> updateLocalAccount(int userId, String username, String password,
                                                    String newPassword, Date updateTime) throws LocalAccountException
    {
        // 旧密码不可以和新密码相同
        if (userId <= 0 || username == null || password == null || newPassword == null || password.equals(newPassword))
            return new Message<>(State.FAILURE);
        try {
            int result = localAccountMapper.updateLocalAccount(userId, username, MD5.getMd5(password), MD5.getMd5(newPassword), updateTime);
            if (result <= 0)
                throw new LocalAccountException("更新账号密码失败!");
        }
        catch (Exception e) {
            throw new LocalAccountException(e.getMessage());
        }

        return new Message<>(State.SUCCESS);
    }


}
