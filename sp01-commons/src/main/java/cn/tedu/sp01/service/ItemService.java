package cn.tedu.sp01.service;

import cn.tedu.sp01.pojo.Item;

import java.util.List;

public interface ItemService {
    //获取订单的商品列表
    List<Item> getItems(String orderId);

    //减少商品库存
    void decreaseNumber(List<Item> items);
}
