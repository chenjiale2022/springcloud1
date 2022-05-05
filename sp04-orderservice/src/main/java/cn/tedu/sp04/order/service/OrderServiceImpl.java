package cn.tedu.sp04.order.service;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.service.OrderService;
import cn.tedu.sp04.order.feignClient.ItemFeignService;
import cn.tedu.sp04.order.feignClient.UserFeignService;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserFeignService userService;
    @Autowired
    private ItemFeignService itemService;

    @Override
    public Order getOrder(String orderId) {
        log.info("获取订单，orderId=" + orderId);

        // TODO: 远程调用商品，获取商品列表
        //OrderController->OrderServoceImpl->
        //通过itemFeignService远程调用(执行失败是调用降级方法itemFeignServiceFB)->
        //itemController->itemServiceImpl
        JsonResult<List<Item>> items=itemService.getItems(orderId);

        // TODO：远程调用用户，获取用户数据
        JsonResult<User> user=userService.getUser(7);

        Order order = new Order();
        order.setId(orderId);
        //order.setItems(商品列表);
        order.setItems(items.getData());
        //order.setUser(用户);
        order.setUser(user.getData());

        return order;
    }

    @Override
    public void addOrder(Order order) {
        //log.info("保存订单：" + order);

        // TODO: 远程调用商品，减少商品库存
        itemService.decreaseNumber(order.getItems());
        // TODO: 远程调用用户，增加用户积分
        userService.addScore(7, 100);

        log.info("保存订单：" + order);
    }
}
