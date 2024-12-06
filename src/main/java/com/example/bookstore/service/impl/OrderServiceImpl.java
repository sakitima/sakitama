package com.example.bookstore.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bookstore.common.mapper.InventoryMapper;
import com.example.bookstore.common.mapper.OrderMapper;
import com.example.bookstore.common.enums.ErrorCodeEnum;
import com.example.bookstore.common.exception.BusinessException;
import com.example.bookstore.common.domain.Inventory;
import com.example.bookstore.common.domain.Order;
import com.example.bookstore.common.request.InventoryReqDto;
import com.example.bookstore.common.request.OrderReqDto;
import com.example.bookstore.common.response.ResponseResult;
import com.example.bookstore.common.util.PageUtil;
import com.example.bookstore.common.util.RedisUtil;
import com.example.bookstore.service.InventoryService;
import com.example.bookstore.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author chaoluo
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private InventoryService inventoryService;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private InventoryMapper inventoryMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseResult addOrder(OrderReqDto orderReqDto) {
        log.info("addOrder requestParam: {}", JSON.toJSONString(orderReqDto));
        InventoryReqDto inventoryReqDto = BeanUtil.copyProperties(orderReqDto, InventoryReqDto.class);
        //扣减库存
        Boolean aBoolean = inventoryService.updateInventory(inventoryReqDto);
        if (aBoolean){
             //生成订单
            Order order = BeanUtil.copyProperties(orderReqDto, Order.class);
            order.setOrderNo("OD" + orderReqDto.getUserId()+orderReqDto.getBookNo()+System.currentTimeMillis());
            orderMapper.insert(order);
            //TODO 可通过通过mq生成订单，进行解耦，后续可通过延时队列设置过期时间进行模拟支付超时，订单过期这种实际业务场景
        }
        return ResponseResult.ok("下单成功");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseResult cancelOrder(OrderReqDto orderReqDto) {
        log.info("cancelOrder requestParam: {}", JSON.toJSONString(orderReqDto));
        if(orderReqDto== null || orderReqDto.getId() ==null){
            throw new BusinessException(ErrorCodeEnum.NOEXIST.getCode(),"订单id不能为空");
        }
        Order order = orderMapper.selectById(orderReqDto.getId());
        if (order ==null){
            throw new BusinessException(ErrorCodeEnum.NOEXIST.getCode(),"订单不存在");
        }
        order.setStatus(2);
        orderMapper.updateById(order);
        Inventory inventory = inventoryMapper.selectOne(new QueryWrapper<Inventory>()
                .lambda()
                .eq(Inventory::getBookNo, orderReqDto.getBookNo()));
        if (inventory !=null){
            inventory.setSaleNum(inventory.getSaleNum() - orderReqDto.getPurchase());
            inventory.setResidue(inventory.getResidue() + orderReqDto.getPurchase());
            redisUtil.set("book:inventory:" + inventory.getBookNo(),JSON.toJSONString(inventory),7200L);
        }else {
            redisUtil.set("book:inventory:" + inventory.getBookNo(),JSON.toJSONString(inventory),10L);
        }
        return ResponseResult.ok("订单取消成功");
    }

    @Override
    public ResponseResult queryOrder(OrderReqDto orderReqDto) {
        log.info("queryOrder requestParam: {}", JSON.toJSONString(orderReqDto));
        Page<Order> orderPage = new Page<>();
        if (orderReqDto == null ){
            return ResponseResult.ok(orderPage);
        }
        Page<Order> page = PageUtil.getPage(orderReqDto.getPageNum(), orderReqDto.getPageSize());
        orderPage = orderMapper.selectPage(page, new QueryWrapper<Order>()
                .lambda()
                .eq(Order::getUserId, orderReqDto.getUserId()));
        return ResponseResult.ok(orderPage);
    }
}
