package com.example.bookstore.controller;

import com.example.bookstore.common.request.OrderReqDto;
import com.example.bookstore.common.response.ResponseResult;
import com.example.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author chaoluo
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public ResponseResult addOrder(@RequestBody @Valid OrderReqDto orderReqDto){
        return orderService.addOrder(orderReqDto);
    }

    @PostMapping("/cancelOrder")
    public ResponseResult cancelOrder(@RequestBody OrderReqDto orderReqDto){
        return orderService.cancelOrder(orderReqDto);
    }

    @PostMapping("/queryOrder")
    public ResponseResult queryOrder(@RequestBody OrderReqDto orderReqDto){
        return orderService.queryOrder(orderReqDto);
    }
}
