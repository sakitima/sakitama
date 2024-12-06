package com.example.bookstore.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bookstore.common.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chaoluo
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}