package cn.tedu.sp06.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;
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

    /**
     * url:  http://localhost:3001/item-service/35
     *
     * */
    @GetMapping("/item-service/{orderId}")
    public JsonResult<List<Item>> getItem(@PathVariable String orderId) {
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
    public JsonResult<?> addScore(@PathVariable Integer userId, Integer score) {
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
    public JsonResult<?> addOrder() {
        return rt.getForObject("http://order-service/",
                JsonResult.class);
    }
}
