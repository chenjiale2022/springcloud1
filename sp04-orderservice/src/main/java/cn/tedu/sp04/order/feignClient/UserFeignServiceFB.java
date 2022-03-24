package cn.tedu.sp04.order.feignClient;

import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserFeignServiceFB implements UserFeignService{

    //获取用户信息的降级方法，模拟使用缓存数据
    @Override
    public JsonResult<User> getUser(Integer userId) {
        if(Math.random()<0.4){
            return JsonResult.ok().data(new User(userId,"缓存name"+userId,"缓存pwd"+userId));
        }
        return JsonResult.err("无法获取用户信息");
    }

    @Override
    public JsonResult addScore(Integer userId, Integer score) {
        return JsonResult.err("无法增加用户积分");
    }
}
