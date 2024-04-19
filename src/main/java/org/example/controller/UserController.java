package org.example.controller;

import jakarta.validation.constraints.Pattern;
import org.example.entity.Result;
import org.example.entity.User;
import org.example.service.UserService;
import org.example.utils.JwtUtil;
import org.example.utils.Md5Util;
import org.example.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password,Boolean userRole) {
        {
            //查询用户
            User u = userService.findByUserName(username);
            if (u == null) {
                //注册
                userService.register(username, password,userRole);
                return Result.success();
            } else {
                //占用
                return Result.error("用户名已存在");
            }

        }
    }

    @PostMapping("/login")
    public Result login( @Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password) {
        //根据用户名查询用户
        User loginuser = userService.findByUserName(username);
        //判断该用户是否存在
        if (loginuser == null) {
            return Result.error("用户名不存在");//(1,"用户名不存在") --用户名不存在作为Message返回
        } else {
            //判断密码是否正确 loginUser对象中的password是密文
            //提交id，username数据存储到map中，可在后续操作中利用ThreadLocal提取该线程中存储的id，username数据
            if (Md5Util.getMD5String(password).equals(loginuser.getPassword())) {
                //登录成功
                Map<String, Object> claims = new HashMap<>();
                claims.put("id", loginuser.getId());
                claims.put("username", loginuser.getUsername());
                claims.put("userRole", loginuser.getUserRole());
                String token = JwtUtil.genToken(claims);
                return Result.success(token); //(0,"登录成功",token) --0代表操作成功  token作为Data返回
            } else {
                return Result.error("密码错误");
            }
        }

    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name="Authorization")String token*/) {
        //根据用户名查询用户
//    Map<String,Object> map = JwtUtil.parseToken(token);
//    String username =(String) map.get("username");
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePassword")
    public Result updatePwd(@RequestBody Map<String,String> params) {
        //1.校验参数
        String oldPwd = params.get("oriPassword");
        String newPwd = params.get("password");
        String rePwd = params.get("checkPassword");
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要的参数");
        }
        //原密码是否正确
        //调用userService根据用户名拿到原密码,再和old_pwd比对
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUserName(username);
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("原密码填写不正确");
        }
        //newPwd和rePwd是否一样
        if (!rePwd.equals(newPwd)) {
            return Result.error("两次填写的新密码不一样");
        }
        //2.调用service完成密码更新
        userService.updatePwd(newPwd);
        return Result.success();
    }
}
