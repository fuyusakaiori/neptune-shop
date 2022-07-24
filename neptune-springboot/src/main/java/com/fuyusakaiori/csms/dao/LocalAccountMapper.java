package com.fuyusakaiori.csms.dao;


import com.fuyusakaiori.csms.entity.LocalAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface LocalAccountMapper
{
    /**
     * <h3>用户登录</h3>
     * @param username 用户名
     * @param password 密码
     * @return 登录的账号
     */
    LocalAccount loginByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * <h3>通过用户查询相应的账号</h3>
     * @return 账号
     */
    LocalAccount findLocalAccountByUser(@Param("userId") int userId);

    /**
     * <h3>创建新的账户</h3>
     * @param account 账户
     * @return 是否创建成功
     */
    int registerLocalAccount(@Param("localAccount") LocalAccount account);

    int updateLocalAccount(@Param("user") int userId, @Param("username") String username,
                           @Param("password") String password, @Param("newPassword") String newPassword,
                           @Param("updateTime") Date updateTime);
}
