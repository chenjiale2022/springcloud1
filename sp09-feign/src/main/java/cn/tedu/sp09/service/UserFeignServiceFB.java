package cn.tedu.sp09.service;

import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;

public class UserFeignServiceFB implements UserFeignService{
    @Override
    public JsonResult<User> getUser(Integer userId) {
        return JsonResult.err("无法获取用户信息");
    }

    @Override
    public JsonResult addScore(Integer userId, Integer score) {
        return JsonResult.err("无法增加用户积分");
    }
}
