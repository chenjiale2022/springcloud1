package cn.tedu.sp02.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.service.ItemService;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@Slf4j
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Value("${server.port}")
    private int port;

    //获取订单的商品列表
    @GetMapping("/{orderId}")
    public JsonResult<List<Item>> getItems(@PathVariable String orderId) throws InterruptedException {

        //随机延迟
        //Math.random()是令系统随机选取大于等于 0.0 且小于 1.0 的伪随机 double 值
        if (Math.random() < 0.9) {//90%概率执行延迟
            //随机延迟[0,5)秒
            int t = new Random().nextInt(5000);
            log.info("延迟：" + t);
            Thread.sleep(t);

        }

        log.info("获取商品，orderId=" + orderId);
        List<Item> items = itemService.getItems(orderId);
        return JsonResult.ok().data(items).msg("port=" + port);
    }

    /*
     * @RequestBody注解
     * 从客户端提交的"请求协议体"中，完整接收协议体数据(JSON格式)，转换成商品列表*/
    //减少商品库存
    @PostMapping("/decreaseNumber")
    public JsonResult<?> decreaseNumber(@RequestBody List<Item> items) {
        itemService.decreaseNumber(items);
        return JsonResult.ok().msg("减少商品库存成功");
    }
}
