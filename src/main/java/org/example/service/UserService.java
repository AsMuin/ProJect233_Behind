package org.example.service;

import org.example.entity.User;

public interface UserService {
    User findByUserName(String username);

    //根据用户名查询用户
    //注册
    void register(String username, String password);

    //更新
    void update(User user);

    //更新头像
    void updateAvatar(String avatarUrl);

    //更新密码
    void updatePwd(String newPwd);
}
