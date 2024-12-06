package com.example.bookstore.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bookstore.common.domain.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chaoluo
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}