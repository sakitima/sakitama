package com.example.bookstore.service;

import com.example.bookstore.common.request.OrderReqDto;
import com.example.bookstore.common.response.ResponseResult;

/**
 * @author chaoluo
 */
public interface OrderService {

    ResponseResult addOrder(OrderReqDto orderReqDto);

    ResponseResult cancelOrder(OrderReqDto orderReqDto);

    ResponseResult queryOrder(OrderReqDto orderReqDto);
}
