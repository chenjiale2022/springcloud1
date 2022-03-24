package cn.tedu.sp06.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
public class RibbonController {

    @Autowired
    private RestTemplate rt;

    //为每个方法添加降级方法：方法名FB()
    //添加@HystrixCommand 注解，指定降级方法名


    /**
     * url:  http://localhost:3001/item-service/35
     *
     * */
    @GetMapping("/item-service/{orderId}")
    @HystrixCommand(fallbackMethod = "getItemsFB") //指定降级方法的方法名
    public JsonResult<List<Item>> getItems(@PathVariable String orderId) {
        // 调用远程 02项目，获取商品列表

        //{1},{2}是RestTamplate提供的一种占位符

        //RestTemplate必须给出具体的服务器地址
        //Ribbon对RestTemplate进行了增强，提供负载均衡的功能
        //根据注册表的主机地址列表，做负载均衡访问
        return rt.getForObject(
                "http://item-service/{1}",//给出具体服务器的地址
                JsonResult.class,
                orderId);
    }

    /**
     * url:  http://localhost:3001/item-service/decreaseNumber
     * param:  [{"id":1, "name":"abc", "number":23},{"id":2, "name":"def", "number":11}]
     * */
    @PostMapping("/item-service/decreaseNumber")
    @HystrixCommand(fallbackMethod ="decreaseNumberFB")
    public JsonResult<?> decreaseNumber(@RequestBody List<Item> items) {
        return rt.postForObject(
                "http://item-service/decreaseNumber",
                items,
                JsonResult.class);
    }

    //

    /**
     * url:  http://localhost:3001/user-service/7
     *
     * */
    @GetMapping("/user-service/{userId}")
    @HystrixCommand(fallbackMethod ="getUserFB")
    public JsonResult<User> getUser(@PathVariable Integer userId) {
        return rt.getForObject(
                "http://user-service/{1}",
                JsonResult.class,
                userId);
    }

    /**
     * url:  http://localhost:3001/user-service/7/score?score=100
     *
     * */
    @GetMapping("/user-service/{userId}/score")
    @HystrixCommand(fallbackMethod ="addScoreFB")
    public JsonResult addScore(@PathVariable Integer userId, Integer score) {
        return rt.getForObject(
                "http://user-service/{1}/score?score={2}",
                JsonResult.class,
                userId, score);
    }

    //

    /**
     * url:  http://localhost:3001/order-service/123abc
     *
     * */
    @GetMapping("/order-service/{orderId}")
    @HystrixCommand(fallbackMethod ="getOrderFB")
    public JsonResult<Order> getOrder(@PathVariable String orderId) {
        return rt.getForObject(
                "http://order-service/{1}",
                JsonResult.class,
                orderId);
    }

    /**
     * url:  http://localhost:3001/order-service
     * */
    @GetMapping("/order-service")
    @HystrixCommand(fallbackMethod ="addOrder")
    public JsonResult<?> addOrder() {
        return rt.getForObject("http://order-service/",
                JsonResult.class);
    }

    //

    //降级方法的参数和返回值，需要和原始方法一致，方法名任意
    public JsonResult<List<Item>> getItemsFB(String orderId) {
        return JsonResult.err("获取订单商品列表失败");
    }
    public JsonResult decreaseNumberFB(List<Item> items) {
        return JsonResult.err("更新商品库存失败");
    }
    public JsonResult<User> getUserFB(Integer userId) {
        return JsonResult.err("获取用户信息失败");
    }
    public JsonResult addScoreFB(Integer userId, Integer score) {
        return JsonResult.err("增加用户积分失败");
    }
    public JsonResult<Order> getOrderFB(String orderId) {
        return JsonResult.err("获取订单失败");
    }
    public JsonResult addOrderFB() {
        return JsonResult.err("添加订单失败");
    }



}
