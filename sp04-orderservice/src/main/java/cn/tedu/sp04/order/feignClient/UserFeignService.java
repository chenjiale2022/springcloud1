package cn.tedu.sp04.order.feignClient;

import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="user-service",fallback = UserFeignServiceFB.class)
public interface UserFeignService {

    @GetMapping("{userId}")
    public JsonResult<User> getUser(@PathVariable Integer userId);

    @GetMapping("{userId}/score")
    public JsonResult addScore(@PathVariable Integer userId,@RequestParam Integer score);
}
